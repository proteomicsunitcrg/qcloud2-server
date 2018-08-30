package eu.qcloud.guideset.manual;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualGuideSetRepository extends CrudRepository<ManualGuideSet, Long> {

}
