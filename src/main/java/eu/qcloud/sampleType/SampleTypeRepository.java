package eu.qcloud.sampleType;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SampleTypeRepository extends CrudRepository<SampleType, Long> {
    @Query("select name from SampleType")
	public List<String> findAllMini();
    
    public interface OnlyName {
    	String getName();
    	Long getId();
    }
    
    public OnlyName findById(Long id);
}
