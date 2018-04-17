package eu.qcloud.data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.chart.chartParams.ChartParamsRepository;
import eu.qcloud.data.DataRepository.MiniData;
import eu.qcloud.data.processor.processorfactory.ProcessorFactory;
import eu.qcloud.data.processor.processors.Processor;
import eu.qcloud.dataSource.DataSource;
import eu.qcloud.dataSource.DataSourceRepository;
import eu.qcloud.dataSource.GuideSet;
import eu.qcloud.param.Param;

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
	private DataSourceRepository dataSourceRepository;
	
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
	 * DataForPlot and its extensions. If there is a need for further data processing
	 * it will do by reflections. Take a look at the processor package.
	 * @author Daniel Mancera <daniel.mancera@crg.eu>
	 * 
	 * @param start
	 * @param end
	 * @param chartId
	 * @param dataSourceId
	 * @param sampleTypeId
	 * @return
	 */
	public List<DataForPlot> getPlotData(Date start, Date end, Long chartId, Long dataSourceId, Long sampleTypeId) {
		List<DataForPlot> dataForPlot = new ArrayList<>();
		ArrayList<Data> dataFromDb = (ArrayList<Data>) dataRepository.findPlotData(chartId, start, end, dataSourceId,
				sampleTypeId);

		for (Data data : dataFromDb) {
			dataForPlot.add(new DataForPlot(data.getFile().getFilename(), data.getFile().getCreationDate(),
					data.getContextSource().getName(), data.getValue()));
		}
		// Get the param
		Param param = chartParamRepository.findTopByChartId(chartId).getParam();
		Processor processor = ProcessorFactory.getProcessor(param.getProcessor());
		
		Optional<DataSource> dataSource = dataSourceRepository.findById(dataSourceId); 
		processor.setData(dataForPlot);
		/**
		 * If data from a guide set is required then call the db for the
		 * data and set it in the processor
		 */
		if(processor.isGuideSetRequired()) {
			// get the guide set of the instrument
			GuideSet gs = dataSource.get().getGuideSet();
			processor.setGuideSet(dataSource.get().getGuideSet());
			ArrayList<Data> dataToProcess = (ArrayList<Data>) dataRepository.findPlotData(chartId, gs.getStartDate(), gs.getEndDate(), dataSourceId,
					sampleTypeId);
			processor.setGuideSetData(dataToProcess);
			return processor.processData();
		}else {
			return processor.processData();
		}		
	}

	public List<MiniData> getDataBetweenDates(Date start, Date end) {
		return dataRepository.findByFileCreationDateBetween(start, end);
	}

	public List<MiniData> getDataBetweenDatesByDataSourceId(Date start, Date end, Long dataSourceId) {
		return dataRepository.findByFileCreationDateBetweenAndFileDataSourceId(start, end, dataSourceId);
	}

}
