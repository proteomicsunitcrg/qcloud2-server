package eu.qcloud.sampleType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.contextSource.peptide.PeptideRepository.OnlyPeptide;
import eu.qcloud.sampleTypeCategory.SampleTypeCategory;
import eu.qcloud.sampleTypeCategory.SampleTypeComplexity;
@Repository
public interface SampleTypeRepository extends CrudRepository<SampleType, Long> {
    @Query("select name from SampleType")
	public List<String> findAllMini();
    
    public SampleTypeOnlyName findOnlyNameById(Long id);
    
    @Query("select s from SampleType s")
    public List<WithPeptide> findAllSampleType();
    
    public Optional<SampleType> findByQualityControlControlledVocabulary(String qCCV);
    
    interface WithPeptide {
    	String getName();
    	List<OnlyPeptide> getPeptides();
    }
    
    public List<SampleType> findBySampleTypeCategoryId(Long id);
    
    public List<SampleType> findBySampleTypeCategoryApiKey(UUID apiKey);
    
    public Optional<SampleType> findByQualityControlControlledVocabularyAndSampleTypeCategoryApiKey(String qCCV, UUID apiKey);
    
    public List<SampleTypeOnlyName> findBySampleTypeCategorySampleTypeComplexityNot(SampleTypeComplexity stc);
    
    public interface SampleTypeOnlyName {
    	// Long getId();
    	String getName();
    	SampleTypeCategory getSampleTypeCategory();
    	String getQualityControlControlledVocabulary();
    }
    
    @Query("select s from SampleType s")
	public Iterable<SampleTypeOnlyName> findAllSampleTypes();

	public SampleType findByIsMainSampleTypeTrueAndSampleTypeCategoryId(Long sampleTypeCategoryId);
	
	public SampleType findByIsMainSampleTypeTrueAndSampleTypeCategoryApiKey(UUID sampleTypeCategoryApiKey);
	
	public List<SampleTypeOnlyName> findBySampleTypeCategorySampleTypeComplexity(SampleTypeComplexity complexity);
	
	public List<SampleType> findByIsMainSampleTypeTrue();
    
}
