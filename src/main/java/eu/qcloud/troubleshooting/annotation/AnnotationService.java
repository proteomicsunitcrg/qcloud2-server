package eu.qcloud.troubleshooting.annotation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.security.model.User;
import eu.qcloud.troubleshooting.action.Action;
import eu.qcloud.troubleshooting.action.ActionRepository;
import eu.qcloud.troubleshooting.annotation.AnnotationRepository.AnnotationForPlot;
import eu.qcloud.troubleshooting.problem.Problem;
import eu.qcloud.troubleshooting.problem.ProblemRepository;
import eu.qcloud.websocket.WebSocketService;

@Service
public class AnnotationService {

	@Autowired
	private AnnotationRepository annotationRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private ActionRepository actionRepository;

	@Autowired
	private LabSystemRepository labSystemRepository;
	
	@Autowired
	private WebSocketService webSocketService;

	private final Log logger = LogFactory.getLog(this.getClass());

	public void addAnnotation(Annotation annotation, User user) {
		attachLabSystemFromDb(annotation);
		annotation.setUser(user);
		annotation.setAnnotationDate(new Date());

		attachAnnotationProblemsFromDb(annotation);
		attachAnnotationActionsFromDb(annotation);
		annotation.setApiKey(UUID.randomUUID());

		annotationRepository.save(annotation);
		
		webSocketService.sendAnnotationToNodeUsers(user.getNode(), annotation);

	}

	private void attachLabSystemFromDb(Annotation annotation) {
		Optional<LabSystem> ls = labSystemRepository.findByApiKey(annotation.getLabSystem().getApiKey());
		if (!ls.isPresent()) {
			throw new DataRetrievalFailureException("Lab system not found");
		}
		annotation.setLabSystem(ls.get());
	}

	private void attachAnnotationProblemsFromDb(Annotation annotation) {
		List<Problem> problems = new ArrayList<>();
		annotation.getProblems().stream().forEach(p -> {
			Optional<Problem> problem = problemRepository.findByQccv(p.getQccv());
			if (problem.isPresent()) {
				problems.add(problem.get());
			}
		});
		annotation.setProblems(problems);
	}

	private void attachAnnotationActionsFromDb(Annotation annotation) {
		List<Action> actions = new ArrayList<>();
		annotation.getActions().stream().forEach(a -> {
			Optional<Action> action = actionRepository.findByQccv(a.getQccv());
			if (action.isPresent()) {
				actions.add(action.get());
			}
		});
		annotation.setActions(actions);
	}

	public List<AnnotationForPlot> getAnnotationsBetweenDates(UUID labSystemApiKey, Date startDate, Date endDate,
			User user) {
		// check for lab system
		Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		if (!labSystem.isPresent()) {
			throw new DataRetrievalFailureException("Lab system not found");
		}
		// check for user first
		checkIfLabSystemBelongsToUserNode(labSystem.get(), user);
		return annotationRepository.findByLabSystemIdAndDateBetween(labSystem.get().getId(), startDate, endDate);
	}

	/**
	 * It will throw an exception if an user is trying to access a other node lab
	 * system
	 * 
	 * @param labSystem
	 * @param user
	 */
	private void checkIfLabSystemBelongsToUserNode(LabSystem labSystem, User user) {
		if (labSystem.getMainDataSource().getNode().getId() != user.getNode().getId()) {
			logger.warn("User " + user.getEmail() + " tried to access other node lab system");
			throw new DataRetrievalFailureException("Lab system not found");
		}
	}
	
	private void checkIfAnnotationBelongsToUser(Annotation annotation, User user) {
		if(annotation.getLabSystem().getMainDataSource().getNode().getId() != user.getNode().getId()) {
			logger.warn("User " + user.getEmail() + " tried to modify other node annotation");
			throw new DataRetrievalFailureException("Annotation not found");
		}
	}

	public AnnotationForPlot getAnnotationByLabSystemApiKeyAndDate(UUID labSystemApiKey, Date date) {
		
		return annotationRepository.findByLabSystemApiKeyAndDate(labSystemApiKey, date);
	}

	public void deleteAnnotationByAnnotationApiKey(UUID annotationApiKey, User user) {
		Optional<Annotation> annotation = annotationRepository.findByApiKey(annotationApiKey);
		if(!annotation.isPresent()) {
			throw new DataRetrievalFailureException("Annotation not found");
		}
		annotationRepository.delete(annotation.get());
		webSocketService.sendDeleteAnnotationToNodeUsers(user.getNode(), annotation.get().getApiKey());
	}

	public AnnotationForPlot updateAnnotation(UUID annotationApiKey, Annotation annotation,
			User user) {
		// check for annotation
		Optional<Annotation> annotationFromDb = annotationRepository.findByApiKey(annotationApiKey);
		if(!annotationFromDb.isPresent()) {
			throw new DataRetrievalFailureException("Annotation not found");
		}
		checkIfAnnotationBelongsToUser(annotationFromDb.get(), user);
		
		annotationFromDb.get().setActions(getActionsFromDb(annotation.getActions()));
		// annotationFromDb.get().setCauses(annotation.getCauses());
		annotationFromDb.get().setProblems(getProblemsFromDb(annotation.getProblems()));
		// annotationFromDb.get().setReasons(annotation.getReasons());
		
		annotationFromDb.get().setUser(user);
		annotationFromDb.get().setAnnotationDate(new Date());
		annotationFromDb.get().setDescription(annotation.getDescription());
		
		Annotation savedAnnotation =  annotationRepository.save(annotationFromDb.get());
		
		webSocketService.sendUpdateAnnotationToNodeUsers(user.getNode(), savedAnnotation);
		
		return annotationRepository.findAnnotationForPlotById(savedAnnotation.getId());
	}
	
	private List<Action> getActionsFromDb(List<Action> actions) {
		List<Action> actionsFromDb = new ArrayList<>();
		actions.forEach(a -> {
			actionsFromDb.add(actionRepository.findByQccv(a.getQccv()).get());
		});
		return actionsFromDb;
	}
	
	private List<Problem> getProblemsFromDb(List<Problem> problems) {
		List<Problem> problemsFromDb = new ArrayList<>();
		problems.forEach(a -> {
			problemsFromDb.add(problemRepository.findByQccv(a.getQccv()).get());
		});
		return problemsFromDb;
	}
	
	

}
