package eu.qcloud.link;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import eu.qcloud.exceptions.NotFoundException;

@RestController
@RequestMapping(value = "/api/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Link> getLinks() {
        return linkService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Link updateLink(@RequestBody @NotNull @NotEmpty final Link link) {
        try {
            return linkService.updateLink(link);
            
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{apiKey}", method = RequestMethod.GET)
    public Link getByApiKey(@PathVariable @NotNull @NotEmpty final UUID apiKey) {
        try {
            return linkService.getByApiKey(apiKey);
            
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
}