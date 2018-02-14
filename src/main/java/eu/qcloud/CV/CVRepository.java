package eu.qcloud.CV;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVRepository extends JpaRepository<CV, Long> {
    public CV findByName(String name);
    public List<CV> findByCategoryId(Long categoryId);
    
    
    
}
