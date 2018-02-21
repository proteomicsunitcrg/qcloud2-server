package eu.qcloud.dataSource;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.node.Node;

@Repository
public interface DataSourceRepository extends CrudRepository<DataSource, Long> {
		
	List<DataSource> findByNodeId(Long nodeId);
	
	DataSource findByNodeIdAndId(Long nodeId, Long id);

	List<DataSource> findByNode(Node node);
	
	List<DataSource> findByNodeIdAndCvCategoryId(Long nodeId, Long categoryId);

	DataSource findByApiKey(UUID apiKey);
	

}
