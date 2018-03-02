package eu.qcloud.sampleComposition;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SampleCompositionRepository extends CrudRepository<SampleComposition, SampleCompositionId> {
	
	public List<SampleComposition> findByPeptideId(Long peptideId);
}
