/**
 * Repository for GeneralAnnotation
 * @author Marc Serret <marc.serret@crg.eu>
 */
package eu.qcloud.generalannotation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralAnnotationRepository extends CrudRepository<GeneralAnnotation, Long> {

    public List<GeneralAnnotation> findAll();

    public GeneralAnnotation findByApiKey(UUID apiKey);

    public List<GeneralAnnotation> findByDateBetween(Date startDate, Date endate);

}
