package eu.qcloud.data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.chart.Chart;
import eu.qcloud.chart.ChartRepository;
import eu.qcloud.chart.chartParams.ChartParamsRepository;
import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.contextSource.instrumentSample.InstrumentSampleRepository;
import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.contextSource.peptide.PeptideRepository;
import eu.qcloud.data.DataRepository.MiniData;
import eu.qcloud.data.insertmodel.DataFromPipeline;
import eu.qcloud.data.insertmodel.DataValues;
import eu.qcloud.data.insertmodel.ParameterData;
import eu.qcloud.data.processor.processorfactory.ProcessorFactory;
import eu.qcloud.data.processor.processors.Processor;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.labsystem.GuideSet;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.param.Param;
import eu.qcloud.param.ParamRepository;
import eu.qcloud.sampleComposition.SampleComposition;
import eu.qcloud.sampleComposition.SampleCompositionRepository;
import eu.qcloud.sampleComposition.SampleCompositionRepository.PeptidesFromSample;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;

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
	
	@Autowired
	private SampleTypeRepository sampleTypeRepository;
	
	@Autowired
	private SampleCompositionRepository sampleCompositionRepository;
	
	@Autowired
	private InstrumentSampleRepository instrumentSampleRepository;
	
	@Autowired
	private ChartRepository chartRepository;

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
	public List<DataForPlot> getPlotData(Date start, Date end, UUID chartApiKey, UUID labSystemApiKey, String sampleTypeQCCV) {
		Optional<Chart> chart = chartRepository.findByApiKey(chartApiKey);
		Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		Optional<SampleType> sampleType = sampleTypeRepository.findByQualityControlControlledVocabulary(sampleTypeQCCV); 
		if(!chart.isPresent() || !labSystem.isPresent() || !sampleType.isPresent()) {
			throw new DataRetrievalFailureException("Wrong chart, system or sample type");
		}
				
		ArrayList<Data> dataFromDb = (ArrayList<Data>) dataRepository.findPlotData(chart.get().getId(), start, end, labSystem.get().getId(),
				sampleType.get().getId());

		// Check sample type in order to send the abbreviated name or anything else		
		
		List<DataForPlot> dataForPlot = prepareDataForPlot(dataFromDb,sampleType.get());
		
		// Get the param
		Param param = chartParamRepository.findTopByChartId(chart.get().getId()).getParam();
		Processor processor = ProcessorFactory.getProcessor(param.getProcessor());

		// Optional<DataSource> dataSource =
		// dataSourceRepository.findById(dataSourceId);
		// Optional<LabSystem> labSystem = labSystemRepository.findById(labSystemId);
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
			ArrayList<Data> dataToProcess = (ArrayList<Data>) dataRepository.findPlotData(chart.get().getId(), gs.getStartDate(),
					gs.getEndDate(), labSystem.get().getId(), sampleType.get().getId());
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
	
	/**
	 * Prepare the output data for the client.
	 * It will take into account if the sample category is HIGHWITHISOTOPOLOGUE for 
	 * susbsitute the abbreviated name with the concentration
	 * @param dataFromDb
	 * @param sampleType
	 * @return
	 */
	private List<DataForPlot> prepareDataForPlot(List<Data> dataFromDb, SampleType sampleType) {
		
		List<DataForPlot> dataForPlot = new ArrayList<>();
		switch(sampleType.getSampleTypeCategory().getSampleTypeComplexity()) {
		case HIGHWITHISOTOPOLOGUES:
			for (Data data : dataFromDb) {
				// Instead of getting the full name or the abbreviated one we need to get the concentration
				SampleComposition concentration = sampleCompositionRepository.getSampleCompositionBySampleTypeIdAndPeptideId(sampleType.getId(), data.getContextSource().getId());
				
				dataForPlot.add(new DataForPlot(data.getFile().getFilename(), data.getFile().getCreationDate(),
						concentration.getConcentration().toString(), data.getValue()));
				Collections.sort(dataForPlot);
			}
			break;
			default:
				for (Data data : dataFromDb) {
					dataForPlot.add(new DataForPlot(data.getFile().getFilename(), data.getFile().getCreationDate(),
							data.getContextSource().getAbbreviated(), data.getValue()));
				}
				break;
		}
		
		return dataForPlot;
	}
	
	/**
	 * Insert data from the pipeline into the database
	 * @param dataFromPipeline
	 */
	public void insertDataFromPipeline(DataFromPipeline dataFromPipeline) {
		// Check if file exists
		File file = fileRepository.findByChecksum(dataFromPipeline.getFile().getChecksum());
		if(file == null) {
			throw new DataRetrievalFailureException("File not found");
		}
		// Loop through the parameters
		for(ParameterData parameterData : dataFromPipeline.getData()) {
			// Loop through the parameters
			Param param = paramRepository.findByQCCV(parameterData.getParameter().getqCCV());
			if(param == null ) {
				continue;
			}
			// loop through values
			for(DataValues dataValue: parameterData.getValues()) {
				ContextSource cs = null;
				switch(param.getIsFor()) {
				case "Peptide":
					cs = peptideRepository.findBySequence(dataValue.getContextSource());
					if(cs==null) {
						continue;						
					}
					if(!peptideBelongsToSampleType(file.getSampleType(), dataValue.getContextSource())) {
						continue;
					}
						
					break;
				case "InstrumentSample":
					cs = instrumentSampleRepository.findByQualityControlControlledVocabulary(dataValue.getContextSource());
					if(cs==null) {
						continue;
					}
					break;
					default:
						System.out.println("i dont know");
						break;
				}
				Data d = new Data(param,cs,file);
				d.setValue(dataValue.getValue());
				d.setDataId(new DataId(param.getId(),cs.getId(),file.getId()));
				dataRepository.save(d);
			}
		}
		
	}
	
	/**
	 * Check if a peptide belongs to a sample type
	 * @param sampleType
	 * @param sequence
	 * @return true or false
	 */
	private boolean peptideBelongsToSampleType(SampleType sampleType, String sequence) {
		List<SampleComposition> sampleComposition = sampleCompositionRepository.findBySampleTypeQualityControlControlledVocabulary(sampleType.getQualityControlControlledVocabulary());
		for(SampleComposition sc : sampleComposition) {
			if(sc.getPeptide().getSequence().equals(sequence)) {
				return true;
			}
		}
		return false;
	}
	
	public List<DataForPlot> getIsotopologueData(String checksum, String abbreviatedSequence) {
		List<Data> dataFromDb = new ArrayList<>();

		// Getting file
		File file = fileRepository.findByChecksum(checksum);
		if(file==null) {
			throw new DataRetrievalFailureException("File not found");
		}
		
		// Getting param
		Param param = paramRepository.findByQCCV("QC:1001844");
		if(param== null) {
			throw new DataRetrievalFailureException("Database error, please contact administrator");
		}
		
		// get peptides by abbreviated sequence
		List<Peptide> peptides = new ArrayList<>();
		List<PeptidesFromSample> pps = sampleCompositionRepository.findBySampleTypeQualityControlControlledVocabularyAndPeptideAbbreviated(file.getSampleType().getQualityControlControlledVocabulary(), abbreviatedSequence);
		for(int i = 0 ; i < pps.size(); i++) {
			peptides.add(pps.get(i).getPeptide());
		}
		if(peptides.size()==0) {
			throw new DataRetrievalFailureException("No isotopologues found.");
		}
		for(Peptide peptide : peptides) {
			Data d = dataRepository.findByFileIdAndParamIdAndContextSourceId(file.getId(), param.getId(), peptide.getId());
			dataFromDb.add(d);
		}
		List<DataForPlot> dataForPlot = prepareDataForPlot(dataFromDb, file.getSampleType());
		
		
		/**
		 * The peptide area of isotopologues will only need the LOG2 processor.
		 * So, there is no need to check if the processor needs a guide set because
		 * we already know what processor will it be.
		 */
		Processor processor = ProcessorFactory.getProcessor(param.getProcessor());
		Collections.reverse(dataForPlot);
		processor.setData(dataForPlot);
		
		return processor.processData();
		
				
	}

}
