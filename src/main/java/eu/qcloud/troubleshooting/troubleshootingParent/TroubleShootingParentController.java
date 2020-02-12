package eu.qcloud.troubleshooting.troubleshootingParent;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.exceptions.InvalidActionException;

@RestController
@RequestMapping(value = "/api/troubleshooting/parent")
public class TroubleShootingParentController {

    @Autowired
    private TroubleShootingParentService troubleshootingParentService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public TroubleshootingParent save(@RequestBody TroubleshootingParent parent) {
        return troubleshootingParentService.save(parent);
    }

    @RequestMapping(value = "/{parentApiKey}", method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('ADMIN')")
    public TroubleshootingParent update(@RequestBody TroubleshootingParent parent, @PathVariable UUID parentApiKey) {
        return troubleshootingParentService.update(parent, parentApiKey);
    }

    @RequestMapping(value = "/{parentApiKey}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public boolean delete(@PathVariable UUID parentApiKey) {
        System.out.println(parentApiKey);
        return troubleshootingParentService.delete(parentApiKey);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public List<TroubleshootingParent> getAllParents() {
        return troubleshootingParentService.getAllParents();
    }

    @RequestMapping(value = "/getOnlyWithActions", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public List<TroubleshootingParent> getAllParentsWithActions() {
        return troubleshootingParentService.getAllParentsWithActions();
    }

    @RequestMapping(value = "/getOnlyWithProblems", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public List<TroubleshootingParent> getAllParentsWithProblems() {
        return troubleshootingParentService.getAllParentsWithProblems();
    }
    
    @RequestMapping(value = "/getByApiKey/{apiKey}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public TroubleshootingParent getByApiKey(@PathVariable UUID apiKey) {
        return troubleshootingParentService.getByApiKey(apiKey);
    }
    
    @RequestMapping(value = "/unlinkAction", method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('ADMIN')")
    public TroubleshootingParent unlinkAction(@RequestParam UUID actionApiKey, @RequestParam UUID parentApiKey) {
        System.out.println(actionApiKey);
        System.out.println(parentApiKey);
        return troubleshootingParentService.unlinkAction(actionApiKey, parentApiKey);
        // return troubleshootingParentService.getByApiKey(parentApiKey);
    }

    @RequestMapping(value = "/unlinkProblem", method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('ADMIN')")
    public TroubleshootingParent unlinkProblem(@RequestParam UUID problemApiKey, @RequestParam UUID parentApiKey) {
        System.out.println(problemApiKey);
        System.out.println(parentApiKey);
        return troubleshootingParentService.unlinkProblem(problemApiKey, parentApiKey);
        // return troubleshootingParentService.getByApiKey(parentApiKey);
    }

    @RequestMapping(value = "/linkAction", method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('ADMIN')")
    public TroubleshootingParent linkAction(@RequestParam UUID actionApiKey, @RequestParam UUID parentApiKey) {
        System.out.println(actionApiKey);
        System.out.println(parentApiKey);
        return troubleshootingParentService.linkAction(actionApiKey, parentApiKey);
        // return troubleshootingParentService.getByApiKey(parentApiKey);
    }

    @RequestMapping(value = "/linkProblem", method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('ADMIN')")
    public TroubleshootingParent linkProblem(@RequestParam UUID problemApiKey, @RequestParam UUID parentApiKey) {
        System.out.println(problemApiKey);
        System.out.println(parentApiKey);
        return troubleshootingParentService.linkProblem(problemApiKey, parentApiKey);
        // return troubleshootingParentService.getByApiKey(parentApiKey);
    }


    /*
     * Exception handlers
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(DataRetrievalFailureException.class)
    void handleNotFound(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(InvalidActionException.class)
    void handleBadAction(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
    }

}
