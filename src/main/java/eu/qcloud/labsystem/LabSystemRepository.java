package eu.qcloud.labsystem;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LabSystemRepository extends CrudRepository<LabSystem, Long> {
	
	@Query(value="select distinct(labsystem.name),labsystem.id from labsystem, labsystem_data_sources, data_source where labsystem_data_sources.lab_system_id = labsystem.id and labsystem_data_sources.data_sources_id = data_source.id and data_source.node_id =?1", nativeQuery=true)
	List<LabSystem> findAllByNode(Long nodeId);
	
}
