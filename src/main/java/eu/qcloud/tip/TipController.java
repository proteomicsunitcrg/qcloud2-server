package eu.qcloud.tip;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.TwitterException;

@RestController
@RequestMapping("/api/tip")
public class TipController {

    @Autowired
    TipService tipService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Tip saveTip(@RequestBody Tip tip) throws TwitterException {
        return tipService.saveTip(tip);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Tip> getAllTips() {
        return tipService.getAllTips();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteTip(@PathVariable Long id) {
        return tipService.deleteTip(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/active")
    public List<Tip> getActive() {
        return tipService.getActive();
    }
}
