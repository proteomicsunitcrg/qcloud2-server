package eu.qcloud.view;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultViewRepository extends CrudRepository<DefaultView, Long> {

	// DefaultView findByCvId(Long cvId);

}
