package eu.qcloud.dataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.Instrument.Instrument;
import eu.qcloud.Instrument.InstrumentRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.guideset.GuideSetRepository;
/**
 * Data source service
 * @author dmancera
 *
 */
@Service
public class DataSourceService {
	@Autowired
	private DataSourceRepository dataSourceRepository;
	
	@Autowired
	private GuideSetRepository guideSetRepository;
	
	@Autowired
	private InstrumentRepository cvRepository;
	
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
	/**
	 * Checks if the current user has the requested
	 * data source
	 * @return true or false
	 */
	public boolean checkIfNodeHasDataSource(Long dataSourceId,Long nodeId) {
		DataSource check = dataSourceRepository.findByIdAndNodeId(dataSourceId,nodeId);
		return check!=null;
		
	}

	public List<DataSource> getAllDataSourceByNodeIdAndCategoryId(Long nodeId, Long categoryId) {
		return dataSourceRepository.findByNodeIdAndCvCategoryId(nodeId, categoryId);
	}
	
	public List<DataSource> getAllDataSourceByNodeIdAndCategoryApiKey(Long nodeId, UUID categoryApiKey) {
		return dataSourceRepository.findByNodeIdAndCvCategoryApiKey(nodeId, categoryApiKey);
	}
	
	public Optional<DataSource> findById(Long id) {
		return dataSourceRepository.findById(id);
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
	
	public GuideSet saveGuideSet(GuideSet gs) {
		return guideSetRepository.save(gs);
	}

	public DataSource addNewDataSourceAuto(DataSource dataSource) {
		Optional<Instrument> cv = cvRepository.getByCVId(dataSource.getCv().getCVId());
		if(!cv.isPresent()) {
			throw new DataRetrievalFailureException("CV not found");
		}
		dataSource.setCv(cv.get());
		return dataSourceRepository.save(dataSource);
	}
			
}
