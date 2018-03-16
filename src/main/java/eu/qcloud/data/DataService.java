package eu.qcloud.data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.data.DataRepository.MiniData;

@Service
public class DataService {
	
	@Autowired
	private DataRepository dataRepository;
	
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
	
	public List<DataForPlot> getPlotData(Date start, Date end, Long chartId, Long dataSourceId, Long sampleTypeId) {		
		List<DataForPlot> dataForPlot = new ArrayList<>();
		ArrayList<Data> dataFromDb = (ArrayList<Data>) dataRepository.findPlotData(chartId, start, end, dataSourceId, sampleTypeId); 
		for(Data data: dataFromDb) {
			dataForPlot.add(new DataForPlot(data.getFile().getFilename(),data.getFile().getCreationDate(),data.getContextSource().getName(),data.getValue()));
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
