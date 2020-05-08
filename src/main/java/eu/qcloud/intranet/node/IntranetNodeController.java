package eu.qcloud.intranet.node;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.exceptions.NotFoundException;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.node.Node;

@RestController()
@RequestMapping("api/intranet/node/")
public class IntranetNodeController {

    @Autowired
    IntranetNodeService intrNodeService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public List<NodeAndStats> getAllNodes() {
        return intrNodeService.getAllStats();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Node getByApiKey(@RequestParam UUID apiKey) {
        return intrNodeService.getByApiKey(apiKey);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "getLS", method = RequestMethod.GET)
    public List<LabSystem> getByNodeApiKey(@RequestParam UUID apiKey) {
        return intrNodeService.getLsByNodeApiKey(apiKey);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "stats", method = RequestMethod.GET)
    public LSStats getStats(@RequestParam UUID apiKey) {
        return intrNodeService.getLSStats(apiKey);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "statsNode", method = RequestMethod.GET)
    public NodeStats getStatsNode(@RequestParam UUID apiKey) {
        return intrNodeService.getNodeStats(apiKey);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "generalStats", method = RequestMethod.GET)
    public GeneralStats getGeneralStats() {
        return intrNodeService.getGeneralStats();
    }

    @RequestMapping(value = "get3ByFile", method = RequestMethod.GET)
    public List<NodeAndStats> getTop3NodesByFile() {
        return intrNodeService.getTop3NodesByFile();
    }

    @ExceptionHandler(NotFoundException.class)
    void handleNotFound(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
}