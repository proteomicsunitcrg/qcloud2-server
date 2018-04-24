package eu.qcloud.system;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SystemRepository extends CrudRepository<System, Long> {
	
	@Query(value="select distinct(system.name),system.id from system, system_data_sources, data_source where system_data_sources.system_id = system.id and system_data_sources.data_sources_id = data_source.id and data_source.node_id =?1", nativeQuery=true)
	List<System> findAllByNode(Long nodeId);
	
}
