
package eu.qcloud.communityline;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.node.Node;

/**
 * CommunityLine Main controller
 *
 * @author Marc Serret <marc.serret@crg.eu>
 */
@RestController
public class CommunityLineController {

	@Autowired
	CommunityLineService communityLineService;

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/community/getAllLines", method = RequestMethod.GET)
	public List<CommunityLine> getAll() {
		return communityLineService.getAllCommunityLines();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/community/save", method = RequestMethod.POST)
	public CommunityLine saveCommunityLine(@RequestBody CommunityLine communityLine) {
		return communityLineService.saveCommunityLine(communityLine);
	}

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "api/community/getByNode", method = RequestMethod.GET)
	public List<CommunityLineNode> getByNodeIdFromSecurityContext() {
		return communityLineService.getByNodeIdFromSecurityContext();
	}

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "api/community/updateActive", method = RequestMethod.POST)
	public CommunityLineNode updateActive(@RequestBody CommunityLineNode communityLineNodeRelation) {
		return communityLineService.updateActive(communityLineNodeRelation);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/community/getNodesInCommunityLineRelation/{id}", method = RequestMethod.GET)
	public List<Node> getNodesInCommunityLineRelation(@PathVariable long id) {
		return communityLineService.getNodesInCommunityLineRelation(id);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/community/makeDeleteRelation", method = RequestMethod.GET)
	public boolean makeDeleteRelation(@RequestParam List<UUID> UUIDsNodes, @RequestParam UUID UUIDLine) {
		return communityLineService.makeDeleteRelation(UUIDsNodes, UUIDLine);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/community/deleteAllRelations/{id}", method = RequestMethod.GET)
	public boolean deleteAllRelations(@PathVariable Long id) {
		return communityLineService.deleteAllRelations(id);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/community/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable Long id) {
		return communityLineService.delete(id);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/community/{id}", method = RequestMethod.GET)
	public CommunityLine getById(@PathVariable Long id) {
		return communityLineService.getById(id);
	}
}
