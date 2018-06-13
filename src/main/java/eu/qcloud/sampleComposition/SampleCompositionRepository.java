package eu.qcloud.sampleComposition;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import eu.qcloud.contextSource.peptide.Peptide;

public interface SampleCompositionRepository extends CrudRepository<SampleComposition, SampleCompositionId> {
	
	public List<SampleComposition> findByPeptideId(Long peptideId);
	
	public List<PeptidesFromSample> findBySampleTypeName(String name);
	
	@Query("select sc from SampleComposition sc where sc.sampleType.id=?1 and sc.peptide.id = ?2")
	public SampleComposition getSampleCompositionBySampleTypeIdAndPeptideId(Long sampleTypeId, Long peptideId);
	
	
	interface PeptidesFromSample {
		Peptide getPeptide();
	}
}
