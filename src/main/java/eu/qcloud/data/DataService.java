package eu.qcloud.data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.chart.chartParams.ChartParamsRepository;
import eu.qcloud.data.DataRepository.MiniData;
import eu.qcloud.data.processor.processors.Processor;
import eu.qcloud.dataSource.DataSource;
import eu.qcloud.dataSource.DataSourceRepository;
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
		Param param = chartParamRepository.findTopByChartParamsIdChartId(chartId).getParam();
		if(param.getProcessor()==null) {
			return dataForPlot;	
		}else {
			try {
				Optional<DataSource> guideSet = dataSourceRepository.findById(dataSourceId);
				
				Class<?> processor = Class.forName("eu.qcloud.data.processor.processors."+param.getProcessor());
				Processor processorInstance = (Processor) processor.newInstance();
				processorInstance.setData(dataForPlot);
				return processorInstance.processData();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return dataForPlot;
	}

	public List<MiniData> getDataBetweenDates(Date start, Date end) {
		return dataRepository.findByFileCreationDateBetween(start, end);
	}

	public List<MiniData> getDataBetweenDatesByDataSourceId(Date start, Date end, Long dataSourceId) {
		return dataRepository.findByFileCreationDateBetweenAndFileDataSourceId(start, end, dataSourceId);
	}

}
