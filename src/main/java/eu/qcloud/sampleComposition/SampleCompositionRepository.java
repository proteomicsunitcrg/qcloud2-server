package eu.qcloud.sampleComposition;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.sampleType.SampleType;

public interface SampleCompositionRepository extends CrudRepository<SampleComposition, SampleCompositionId> {

	@Modifying
	@Query(value = "delete from sample_composition where peptide_id = ?1 and sample_type_id = ?2", nativeQuery = true)
	public void deleteSampleCompositionByPeptideIdAndSampleTypeId(Long peptideId, Long sampleTypeId);

	public List<SampleComposition> findByPeptideId(Long peptideId);

	public List<SampleComposition> findByPeptideSequence(String peptideSequence);

	public List<PeptidesFromSample> findBySampleTypeName(String name);

	public List<PeptidesFromSample> findBySampleType(SampleType st);


	@Query("select sc from SampleComposition sc where sc.sampleType.id=?1 and sc.peptide.id = ?2")
	public SampleComposition getSampleCompositionBySampleTypeIdAndPeptideId(Long sampleTypeId, Long peptideId);

	public List<SampleComposition> findBySampleTypeQualityControlControlledVocabulary(String qqcv);

	public List<PeptidesFromSample> findBySampleTypeQualityControlControlledVocabularyAndPeptideAbbreviated(String qqcv,
			String abbreviated);

	interface PeptidesFromSample {
		Peptide getPeptide();
	}
}
