package eu.qcloud.CV;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CVRepository extends JpaRepository<CV, Long> {
    public CV findByName(String name);
    public List<CV> findByCategoryId(Long categoryId);
    public List<CV> findByEnabled(Boolean enabled);
    
    public List<CV> findByCategoryIdAndEnabled(Long categoryId,Boolean enabled);
    
    @Query("select c from CV c where cv_id= ?1")
    public CV getByCVId(String cvId);
    
    
    
}
