package eu.qcloud.threshold;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThresholdRepository extends CrudRepository<Threshold, Long> {

}
