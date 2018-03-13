package eu.qcloud.view;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends CrudRepository<View, Long> {
	

}
