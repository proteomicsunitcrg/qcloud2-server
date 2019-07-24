
package eu.qcloud.communityline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * EmailController Main controller for email related operations
 * 
 * @author Marc Serret <marc.serret@crg.eu>
 */
@RestController
public class CommunityLineController {

	// @Autowired
	// CommunityLineService communityLineService;

	// @PreAuthorize("hasRole('ADMIN')")
	// @RequestMapping(value = "/api/community/getAllLines", method = RequestMethod.GET)
	// public List<CommunityLineNodeRelation> getAll() {
	// 	return communityLineService.getAllCommunityLines();
	// }

	// @PreAuthorize("hasRole('ADMIN')")
	// @RequestMapping(value = "/api/community/save", method = RequestMethod.POST)
	// public CommunityLine saveCommunityLine(@RequestBody CommunityLine communityLine) {
	// 	return communityLineService.saveCommunityLine(communityLine);
	// }

	// @PreAuthorize("hasRole('MANAGER')")
	// @RequestMapping(value = "api/community/getByNode", method = RequestMethod.GET) 
	// public List<CommunityLineNodeRelation> getByNode() {
	// 	return communityLineService.getByNodeId();
	// }

	// @PreAuthorize("hasRole('MANAGER')")
	// @RequestMapping(value = "api/community/updateActive", method = RequestMethod.POST)
	// public CommunityLineNodeRelation updateActive (@RequestBody CommunityLineNodeRelation communityLineNodeRelation) {
	// 	return communityLineService.updateActive(communityLineNodeRelation);
	// }
	

}
