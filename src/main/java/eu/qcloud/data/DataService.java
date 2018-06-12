package eu.qcloud.data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.chart.chartParams.ChartParamsRepository;
import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.contextSource.peptide.PeptideRepository;
import eu.qcloud.data.DataRepository.MiniData;
import eu.qcloud.data.insertmodel.PeptideList;
import eu.qcloud.data.processor.processorfactory.ProcessorFactory;
import eu.qcloud.data.processor.processors.Processor;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.labsystem.GuideSet;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.param.Param;
import eu.qcloud.param.ParamRepository;

/**
 * Service for the data
 * 
 * @author dmancera
 *
 */
@Service
public class DataService {

	@Autowired
	private DataRepository dataRepository;

	@Autowired
	private ChartParamsRepository chartParamRepository;

	@Autowired
	private LabSystemRepository labSystemRepository;

	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ParamRepository paramRepository;
	
	@Autowired
	private PeptideRepository peptideRepository;

	public List<Data> getAllData() {
		List<Data> data = new ArrayList<>();
		dataRepository.findAll().forEach(data::add);
		return data;
	}

	public Data addData(Data data) {
		return dataRepository.save(data);
	}

	public MiniData getMiniData(Long fileId) {
		return dataRepository.findByDataIdFileId(fileId);
	}

	/**
	 * Recover data from the server by parameters. Note the usage of a class named
	 * DataForPlot and its extensions. If there is a need for further data
	 * processing it will do by reflections. Take a look at the processor package.
	 * 
	 * @author Daniel Mancera <daniel.mancera@crg.eu>
	 * 
	 * @param start
	 * @param end
	 * @param chartId
	 * @param dataSourceId
	 * @param sampleTypeId
	 * @return
	 */
	public List<DataForPlot> getPlotData(Date start, Date end, Long chartId, Long labSystemId, Long sampleTypeId) {
		List<DataForPlot> dataForPlot = new ArrayList<>();
		ArrayList<Data> dataFromDb = (ArrayList<Data>) dataRepository.findPlotData(chartId, start, end, labSystemId,
				sampleTypeId);

		for (Data data : dataFromDb) {
			dataForPlot.add(new DataForPlot(data.getFile().getFilename(), data.getFile().getCreationDate(),
					data.getContextSource().getAbbreviated(), data.getValue()));
		}
		// Get the param
		Param param = chartParamRepository.findTopByChartId(chartId).getParam();
		Processor processor = ProcessorFactory.getProcessor(param.getProcessor());

		// Optional<DataSource> dataSource =
		// dataSourceRepository.findById(dataSourceId);
		Optional<LabSystem> labSystem = labSystemRepository.findById(labSystemId);
		processor.setData(dataForPlot);
		/**
		 * If data from a guide set is required then call the db for the data and set it
		 * in the processor
		 */
		if (processor.isGuideSetRequired()) {
			// get the guide set of the instrument
			GuideSet gs = labSystem.get().getGuideSet();
			if (gs == null) {
				throw new DataRetrievalFailureException("A guide set is required for this plot.");
			}
			processor.setGuideSet(gs);
			ArrayList<Data> dataToProcess = (ArrayList<Data>) dataRepository.findPlotData(chartId, gs.getStartDate(),
					gs.getEndDate(), labSystemId, sampleTypeId);
			if (dataToProcess.size() == 0) {
				throw new DataRetrievalFailureException(
						"Your selected guide has no results. Please, choose another date range.");
			}
			processor.setGuideSetData(dataToProcess);
			return processor.processData();
		} else {
			return processor.processData();
		}
	}

	public List<MiniData> getDataBetweenDates(Date start, Date end) {
		return dataRepository.findByFileCreationDateBetween(start, end);
	}

	public List<MiniData> getDataBetweenDatesByDataSourceId(Date start, Date end, Long dataSourceId) {
		return dataRepository.findByFileCreationDateBetweenAndFileLabSystemId(start, end, dataSourceId);
	}

	public void insertPeptides(String qCCV, String checksum, List<PeptideList> peptides) {
		List<Data> data = new ArrayList<>();
		
		// check if file exists
		File f = fileRepository.findByChecksum(checksum);
		if (f == null) {
			throw new DataRetrievalFailureException("File not found");
		}

		// check if param exists
		Param p = paramRepository.findByQCCV(qCCV);
		if(p==null) {
			throw new DataRetrievalFailureException("Parameter not found");
		}
		// check if peptides exists
		for(PeptideList pl : peptides) {
			Peptide peptide = peptideRepository.findBySequence(pl.getSequence());
			if(peptide == null) {
				throw new DataRetrievalFailureException("Peptide not found");	
			}
			Data d = new Data(p,peptide,f);
			d.setValue(pl.getValue());
			d.setDataId(new DataId(p.getId(),peptide.getId(),f.getId()));
			data.add(d);
		}
		dataRepository.saveAll(data);
		

	}

}
