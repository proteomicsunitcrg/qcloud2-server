package eu.qcloud.dataSource;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.node.Node;

@Repository
@Transactional
public interface DataSourceRepository extends CrudRepository<DataSource, Long> {

	List<DataSource> findByNodeId(Long nodeId);

	DataSource findByIdAndNodeId(Long id, Long nodeId);

	List<DataSource> findByNode(Node node);

	List<DataSource> findByNodeIdAndCvCategoryId(Long nodeId, Long categoryId);

	DataSource findByApiKey(UUID apiKey);

	interface OnlyDataSource {
		Long getId();

		String getName();

	}

	List<DataSource> findByNodeIdAndCvCategoryApiKey(Long nodeId, UUID categoryApiKey);

}
