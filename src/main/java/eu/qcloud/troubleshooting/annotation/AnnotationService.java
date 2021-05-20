package eu.qcloud.troubleshooting.annotation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;
// import eu.qcloud.troubleshooting.action.ActionRepository;
import eu.qcloud.troubleshooting.annotation.AnnotationRepository.AnnotationForPlot;
import eu.qcloud.websocket.WebSocketService;

@Service
public class AnnotationService {

    @Autowired
    private AnnotationRepository annotationRepository;

    @Autowired
    private LabSystemRepository labSystemRepository;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private UserService userService;

    @Autowired
    private LabSystemRepository lsRepo;

    private final Log logger = LogFactory.getLog(this.getClass());

    public void addAnnotation(Annotation annotation, User user) {
        attachLabSystemFromDb(annotation);
        annotation.setUser(user);
        annotation.setAnnotationDate(new Date());
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
        if (annotation.getLabSystem().getMainDataSource().getNode().getId() != user.getNode().getId()) {
            logger.warn("User " + user.getEmail() + " tried to modify other node annotation");
            throw new DataRetrievalFailureException("Annotation not found");
        }
    }

    public AnnotationForPlot getAnnotationByLabSystemApiKeyAndDate(UUID labSystemApiKey, Date date) {
        Optional <AnnotationForPlot> anno = annotationRepository.findByLabSystemApiKeyAndDate(labSystemApiKey, date);
        if (anno.isPresent()) {
            return anno.get();
        } else {
            return null;
        }
    }

    public void deleteAnnotationByAnnotationApiKey(UUID annotationApiKey, User user) {
        Optional<Annotation> annotation = annotationRepository.findByApiKey(annotationApiKey);
        if (!annotation.isPresent()) {
            throw new DataRetrievalFailureException("Annotation not found");
        }
        annotationRepository.delete(annotation.get());
        webSocketService.sendDeleteAnnotationToNodeUsers(user.getNode(), annotation.get().getApiKey());
    }

    public AnnotationForPlot updateAnnotation(UUID annotationApiKey, Annotation annotation, User user) {
        // check for annotation
        Optional<Annotation> annotationFromDb = annotationRepository.findByApiKey(annotationApiKey);
        if (!annotationFromDb.isPresent()) {
            throw new DataRetrievalFailureException("Annotation not found");
        }
        checkIfAnnotationBelongsToUser(annotationFromDb.get(), user);

        annotationFromDb.get().setUser(user);
        annotationFromDb.get().setAnnotationDate(new Date());
        annotationFromDb.get().setDescription(annotation.getDescription());
        annotationFromDb.get().setTroubleshootings(annotation.getTroubleshootings());

        Annotation savedAnnotation = annotationRepository.save(annotationFromDb.get());

        webSocketService.sendUpdateAnnotationToNodeUsers(user.getNode(), savedAnnotation);

        return annotationRepository.findAnnotationForPlotById(savedAnnotation.getId());
    }

    public Page<Annotation> getPage(Pageable page, String lsApiKey, Date startDate, Date endDate, String troubleshootingName) {
        User u = getManagerFromSecurityContext();
        List <LabSystem> ls = lsRepo.findAllByNode(u.getNode().getId());
        if (!lsApiKey.equals("null")) {
            return annotationRepository.findDistinctByLabSystemApiKeyAndDateBetweenAndTroubleshootingsNameContainsIgnoreCaseOrderByIdDesc(UUID.fromString(lsApiKey), page, startDate, endDate, troubleshootingName);
        }
        return annotationRepository.findDistinctByLabSystemInAndDateBetweenAndTroubleshootingsNameContainsIgnoreCaseOrderByIdDesc(ls, page, startDate, endDate, troubleshootingName);
    }

    private User getManagerFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByUsername(authentication.getName());
        return manager;
    }


}
