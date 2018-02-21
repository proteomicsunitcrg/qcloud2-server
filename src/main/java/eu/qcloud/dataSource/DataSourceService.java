package eu.qcloud.dataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService {
	@Autowired
	private DataSourceRepository dataSourceRepository;
	
	public List<DataSource> getAllDataSource() {
		List<DataSource> dataSources = new ArrayList<>();
		dataSourceRepository.findAll().forEach(dataSources::add);
		return dataSources;
	}
	
	public List<DataSource> getAllDataSourceByNodeId(Long nodeId) {
		
		List<DataSource> dataSources = new ArrayList<>();
		dataSourceRepository.findByNodeId(nodeId).forEach(dataSources::add);
		
		return dataSources;
	}
	
	public List<DataSource> addNewDataSource(DataSource dataSource) {		
		dataSourceRepository.save(dataSource);
		return dataSourceRepository.findByNode(dataSource.getNode());
	}
	
	public boolean checkIfNodeHasDataSource(Long nodeId, Long dataSourceId) {
		DataSource check = dataSourceRepository.findByNodeIdAndId(nodeId, dataSourceId);
		return check!=null;
		
	}

	public List<DataSource> getAllDataSourceByNodeIdAndCategoryId(Long nodeId, Long categoryId) {
		return dataSourceRepository.findByNodeIdAndCvCategoryId(nodeId, categoryId);
	}
	
	public DataSource findById(Long id) {
		return dataSourceRepository.findOne(id);
		
	}

	public void deleteDataSource(DataSource dataSource) {
		// Delete
		dataSourceRepository.delete(dataSource);
	}

	public DataSource findByApiKey(UUID apiKey) {
		return dataSourceRepository.findByApiKey(apiKey);
	}

	public DataSource updateDataSource(DataSource ds) {
		return dataSourceRepository.save(ds);

	}
			
}
