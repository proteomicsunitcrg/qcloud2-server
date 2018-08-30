package eu.qcloud.guideset.automatic;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomaticGuideSetRepository extends CrudRepository<AutomaticGuideSet, Long> {

	List<AutomaticGuideSet> findByIsActiveTrue();

}
