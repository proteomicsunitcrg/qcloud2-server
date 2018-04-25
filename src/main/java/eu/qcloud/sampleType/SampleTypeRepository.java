package eu.qcloud.sampleType;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.contextSource.peptide.PeptideRepository.OnlyPeptide;
import eu.qcloud.sampleTypeCategory.SampleTypeCategory;
@Repository
public interface SampleTypeRepository extends CrudRepository<SampleType, Long> {
    @Query("select name from SampleType")
	public List<String> findAllMini();
    
    public SampleTypeOnlyName findOnlyNameById(Long id);
    
    @Query("select s from SampleType s")
    public List<WithPeptide> findAllSampleType();
    
    interface WithPeptide {
    	String getName();
    	List<OnlyPeptide> getPeptides();
    }
    
    public List<SampleType> findBySampleTypeCategoryId(Long id);
    
    
    public interface SampleTypeOnlyName {
    	Long getId();
    	String getName();
    	SampleTypeCategory getSampleTypeCategory();
    }
    
    @Query("select s from SampleType s")
	public Iterable<SampleTypeOnlyName> findAllSampleTypes();
    
}
