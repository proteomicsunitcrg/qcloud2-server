package eu.qcloud.troubleshooting.annotation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.labsystem.LabSystemRepository.LabSystemNameAndApiKey;
import eu.qcloud.troubleshooting.action.Action;
import eu.qcloud.troubleshooting.problem.Problem;

@Repository
public interface AnnotationRepository extends CrudRepository<Annotation, Long> {
	
	@Query("select c from Annotation c")
	List<AnnotationForPlot> getAll();
	
	interface AnnotationForPlot {
		Date getDate();
		List<Problem> getProblems();
		List<Action> getActions();
		LabSystemNameAndApiKey getLabSystem();
		UUID getApiKey();
		String getDescription();
	}

}
