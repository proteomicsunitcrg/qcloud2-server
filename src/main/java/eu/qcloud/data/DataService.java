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
	
	public List<MiniData> getDataBetweenDates(Date start, Date end) {
		return dataRepository.findByFileCreationDateBetween(start, end);
	}

	public List<MiniData> getDataBetweenDatesByDataSourceId(Date start, Date end, Long dataSourceId) {
		return dataRepository.findByFileCreationDateBetweenAndFileDataSourceId(start, end, dataSourceId);
		
	}
}
