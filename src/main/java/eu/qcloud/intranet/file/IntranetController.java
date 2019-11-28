package eu.qcloud.intranet.file;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import eu.qcloud.data.Data;
import eu.qcloud.file.File;
import eu.qcloud.node.Node;
import eu.qcloud.security.model.User;

@RestController
public class IntranetController {

    @Autowired
    IntranetService intranetService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "api/intranet/file/countAll", method = RequestMethod.GET)
    public Long countAllFiles() {
        return intranetService.countAllFiles();
    }

    // @PreAuthorize("hasRole('ADMIN')")
    // @RequestMapping(value = "api/intranet/file/{name}/{checksum}/{labsystemName}/{sampleTypeId}/{exact}", method = RequestMethod.GET)
    // // public List<File> getAllFIles(@PathVariable String name, @PathVariable String checksum, @PathVariable String labsystemName, @PathVariable String sampleTypeId, @PathVariable boolean exact) {
    //     // return intranetService.getAllFiles(name, checksum, labsystemName, sampleTypeId, exact);
    // }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "api/intranet/file/{checksum}", method = RequestMethod.DELETE)
    public boolean deleteFile(@PathVariable String checksum) {
        intranetService.deleteFile(checksum);
        return true;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "api/intranet/file/getPage", method = RequestMethod.GET)
    public Page<File> loadPage(Pageable page, @RequestParam String name, @RequestParam String checksum,@RequestParam String labsystemName, @RequestParam String sampleTypeId, @RequestParam String node, @RequestParam String email, @RequestParam boolean exact) {
        return intranetService.getAllFiles(name, checksum, labsystemName, sampleTypeId, page, node, email, exact);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "api/intranet/file/getNode", method = RequestMethod.GET)
    public Node getNodeByDataSourceApiKey(@RequestParam UUID apiKey) {
        return intranetService.getNodeByDataSourceApiKey(apiKey);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "api/intranet/file/getNodeAndFileInfo", method = RequestMethod.GET)
    public NodeAndFileStatus getNodeByDataSourceApiKey(@RequestParam UUID dataSourceApiKey, @RequestParam String fileChecksum) {
        return intranetService.getNodeAndFileStatus(dataSourceApiKey, fileChecksum);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "api/intranet/file/data", method = RequestMethod.GET)
    public List <Data> getData(@RequestParam String checksum) {
        return intranetService.getDataBychecksum(checksum);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "api/intranet/file/user", method = RequestMethod.GET)
    public List <User> getUsers(@RequestParam String email) {
        return intranetService.getUsers(email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "api/intranet/file/fullData", method = RequestMethod.GET)
    public List<File> getJSON(@RequestParam String name, @RequestParam String checksum,@RequestParam String labsystemName, @RequestParam String sampleTypeId, @RequestParam String node, @RequestParam String email, @RequestParam boolean exact) {
        return intranetService.getJSON(name, checksum, labsystemName, sampleTypeId, node, email, exact);
    }

}