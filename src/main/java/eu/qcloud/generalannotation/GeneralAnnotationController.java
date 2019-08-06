
package eu.qcloud.generalannotation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * GeneralAnnotationController Main controller for general annotations
 * operations
 * 
 * @author Marc Serret <marc.serret@crg.eu>
 */
@RestController
public class GeneralAnnotationController {

    @Autowired
    GeneralAnnotationService generalAnnotationService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/api/general-annotation", method = RequestMethod.GET)
    public List<GeneralAnnotation> sendManualEmail() {
        return generalAnnotationService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/api/general-annotation/save", method = RequestMethod.POST)
    public GeneralAnnotation saveGeneralAnnotation(@RequestBody GeneralAnnotation annotation) {
        return generalAnnotationService.save(annotation);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/api/general-annotation/toggle/{apiKey}", method = RequestMethod.GET)
    public GeneralAnnotation toggleActive(@PathVariable UUID apiKey) {
        return generalAnnotationService.toggleActive(apiKey);
    }

    @RequestMapping(value = "/api/general-annotation/dates/{dateStart}/{dateEnd}", method = RequestMethod.GET)
    public List<GeneralAnnotation> requestMethodName(@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) Date dateStart,
            @PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) Date dateEnd) {
                return generalAnnotationService.getBetweenDates(dateStart, dateEnd);
    }

}
