package eu.qcloud.logo;

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

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.exceptions.NotFoundException;

@RestController
@RequestMapping(value = "/api/logo")
public class LogoController {
    
    @Autowired
    LogoService logoService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Logo addLogo(@RequestBody @NotNull @NotEmpty final Logo toAdd) {
        return logoService.insertLogo(toAdd);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Logo> addLogo() {
        return logoService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/enableDisable", method = RequestMethod.PATCH)
    public Logo enableDisable(@RequestBody @NotNull @NotEmpty final Logo logo) {
        try {
            return logoService.enableDisable(logo);
        } catch (final InvalidActionException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{logoApiKey}", method = RequestMethod.DELETE)
    public boolean deleteLogo(@PathVariable @NotNull @NotEmpty final UUID logoApiKey) {
        try {
            logoService.delete(logoApiKey);
            return true;
        } catch (final InvalidActionException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/enabled", method = RequestMethod.GET)
    public Logo getEnabledLogo() {
        try {
            return logoService.getEnabledLogo();
            
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{apiKey}", method = RequestMethod.GET)
    public Logo getByApiKey(@PathVariable UUID apiKey) {
        try {
            return logoService.getByApiKey(apiKey);
            
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Logo updateLogo(@RequestBody @NotNull @NotEmpty final Logo logo) {
        try {
            return logoService.updateLogo(logo);
            
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}