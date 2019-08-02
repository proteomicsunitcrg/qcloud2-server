package eu.qcloud.troubleshooting.annotation;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.labsystem.LabSystemRepository.LabSystemNameAndApiKey;
import eu.qcloud.security.repository.UserRepository.UserForAnnotation;
import eu.qcloud.troubleshooting.action.Action;
import eu.qcloud.troubleshooting.problem.Problem;

@Repository
public interface AnnotationRepository extends CrudRepository<Annotation, Long> {

	@Query("select c from Annotation c")
	List<AnnotationForPlot> getAll();

	List<AnnotationForPlot> findByLabSystemIdAndDateBetween(Long labSystemId, Date startDate, Date endDate);

	interface AnnotationForPlot {
		Date getDate();

		List<Problem> getProblems();

		List<Action> getActions();

		LabSystemNameAndApiKey getLabSystem();

		UUID getApiKey();

		String getDescription();

		UserForAnnotation getUser();

		Date getAnnotationDate();
	}

	AnnotationForPlot findByLabSystemApiKeyAndDate(UUID labSystemApiKey, Date date);

	Optional<Annotation> findByApiKey(UUID annotationApiKey);

	@Query("select a from Annotation a where id = ?1")
	AnnotationForPlot findAnnotationForPlotById(Long id);

}
