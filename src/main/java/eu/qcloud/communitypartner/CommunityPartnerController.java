package eu.qcloud.communitypartner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityPartnerController {

    @Autowired
    CommunityPartnerService communityPartnerService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/api/communityPartner/getAll", method = RequestMethod.GET)
    public List<CommunityPartner> getAll() {
        return communityPartnerService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/api/communityPartner/create", method = RequestMethod.POST)
    public CommunityPartner createNew(@RequestBody CommunityPartner communityPartner) {
        return communityPartnerService.createNew(communityPartner);
    }
}