package eu.qcloud.threshold.params;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import eu.qcloud.contextSource.ContextSource;

public interface ThresholdParamsRepository extends CrudRepository<ThresholdParams, ThresholdParamsId> {

	@Query("select tp from ThresholdParams tp")
	List<paramsNoThreshold> getAll();

	ThresholdParams findByThresholdIdAndContextSourceId(Long thresholdId, Long contextSourceId);

	interface paramsNoThreshold {
		Float getStepValue();

		Float getInitialValue();

		ContextSource getContextSource();

		Boolean getIsEnabled();

	}
}
