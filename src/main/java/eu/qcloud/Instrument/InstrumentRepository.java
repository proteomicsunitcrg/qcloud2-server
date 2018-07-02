package eu.qcloud.Instrument;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    public Instrument findByName(String name);
    
    public List<Instrument> findByCategoryId(Long categoryId);
    
    public List<Instrument> findByCategoryApiKey(UUID categoryApiKey);
    
    public List<Instrument> findByEnabled(Boolean enabled);
    
    public List<Instrument> findByCategoryIdAndEnabled(Long categoryId,Boolean enabled);
    
    @Query("select c from Instrument c where cv_id= ?1")
    public Optional<Instrument> getByCVId(String cvId);

	public List<Instrument> findByCategoryApiKeyAndEnabled(UUID categoryApiKey, Boolean b);
}
