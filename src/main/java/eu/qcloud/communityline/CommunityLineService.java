package eu.qcloud.communityline;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import eu.qcloud.node.Node;
import eu.qcloud.node.NodeRepository;
import eu.qcloud.param.ParamRepository;
import eu.qcloud.sampleType.SampleTypeRepository;
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;
import eu.qcloud.traceColor.TraceColorRepository;

/**
 * Main service for community line
 * 
 * @author Marc Serret <marc.serret@crg.eu>
 */

@Service
public class CommunityLineService {

	@Autowired
	CommunityLineRepository commLineRepository;

	@Autowired
	ParamRepository paramRepository;

	@Autowired
	SampleTypeRepository sampleTypeRepository;

	@Autowired
	private UserService userService;

	@Autowired
	CommunityLineNodeRepository communityLineNodeRepository;

	@Autowired
	NodeRepository nodeRepository;

	@Autowired
	TraceColorRepository traceColorRepository;

	/**
	 * Retrieve all the existing communityLines
	 * 
	 * @return List<CommunityLine> all the communityLines in the database
	 */
	public List<CommunityLine> getAllCommunityLines() {
		List<CommunityLine> allLines = new ArrayList<>();
		commLineRepository.findAll().forEach(allLines::add);
		return allLines;
	}

	/**
	 * Save the line in the database. Retrieves some data first because in the FE is
	 * incomplete Sets a default black TraceColor color
	 * 
	 * @param communityLine to be saved to the database
	 * @return CommunityLine saved
	 */
	public CommunityLine saveCommunityLine(CommunityLine communityLine) {
		communityLine.setParam(paramRepository.findByQccv(communityLine.getParam().getqCCV()));
		communityLine.setSampleType(sampleTypeRepository.findByQualityControlControlledVocabulary(
				communityLine.getSampleType().getQualityControlControlledVocabulary()).get());
		communityLine.setApiKey(UUID.randomUUID());
		communityLine.setTraceColor(traceColorRepository.findByApiKey(communityLine.getTraceColor().getApiKey()).get());
		return commLineRepository.save(communityLine);
	}

	/**
	 * Retrives all the nodes associated with a communityLine
	 * 
	 * @param id of the communityLine
	 * @return a list wwith the linked nodes
	 */
	public List<Node> getNodesInCommunityLineRelation(Long id) {
		List<CommunityLineNode> communityLineNodes = communityLineNodeRepository.findAllByCommunityLineId(id);
		List<Node> nodes = new ArrayList<Node>();
		for (CommunityLineNode communityLineNode : communityLineNodes) {
			nodes.add(communityLineNode.getNode());
		}
		return nodes;
	}

	/**
	 * Retrive the node lines based on the logged user
	 * 
	 * @return the node lines
	 */
	public List<CommunityLineNode> getByNodeIdFromSecurityContext() {
		User u = getManagerFromSecurityContext();
		List<CommunityLineNode> lines = communityLineNodeRepository.findAllByNodeId(u.getNode().getId());
		return lines;
	}

	/**
	 * Retrieves the itself because the FE don't have all the data and sets the
	 * isActive attribute to the opposite state
	 * 
	 * @param communityLineNode CommunityLineNode to update
	 * @return the updated relation
	 */
	public CommunityLineNode updateActive(CommunityLineNode communityLineNode) {
		// Because client dont have the full entity
		CommunityLineNode toUpdate = communityLineNodeRepository.findById(communityLineNode.getId());
		toUpdate.setActive(!toUpdate.isActive());
		communityLineNodeRepository.save(toUpdate);
		return toUpdate;
	}

	/**
	 * Get the current user from the security context
	 * 
	 * @return the logged user
	 */
	private User getManagerFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User manager = userService.getUserByUsername(authentication.getName());
		return manager;
	}

	/**
	 * First get all the data needed with the provided UUIDs and checks if some node
	 * in the UUIDNodes is not in the relation and adds it if not exists Then do the
	 * reverse, if some node is in the relation but no in the array then the
	 * relation is deleted TODO: Improve this method
	 * 
	 * @param UUIDsNodes UUID list with the current node that the line has to be
	 *                   linked
	 * @param UUIDLine   UUID of the line that need to be linked with the nodes
	 * @return true if all correct or false otherwise
	 * 
	 */
	public boolean makeDeleteRelation(List<UUID> UUIDsNodes, UUID UUIDLine) {
		try {
			List<Node> nodesRelationIncoming = new ArrayList<Node>();
			for (UUID nodeUUID : UUIDsNodes) {
				nodesRelationIncoming.add(nodeRepository.findByApiKey(nodeUUID));
			}
			CommunityLine communityLine = commLineRepository.findByApiKey(UUIDLine);
			List<CommunityLineNode> communityLineNodes = communityLineNodeRepository
					.findAllByCommunityLineId(communityLine.getId());
			for (Node node : nodesRelationIncoming) {
				if (!isThisNodeInTheRelation(communityLineNodes, node)) {
					communityLineNodeRepository.save(new CommunityLineNode(communityLine, node, true));
				}
			}
			List<Node> nodesInRelations = new ArrayList<Node>();
			for (CommunityLineNode communityLineNode : communityLineNodes) {
				nodesInRelations.add(communityLineNode.getNode());
			}
			List<Node> nodeOnlyInRelation = new ArrayList<Node>();
			nodeOnlyInRelation.addAll(nodesInRelations);
			nodeOnlyInRelation.removeAll(nodesRelationIncoming);
			for (Node node : nodeOnlyInRelation) {
				Optional<CommunityLineNode> toDelete = communityLineNodeRepository
						.findAllByNodeIdAndCommunityLineId(node.getId(), communityLine.getId());
				if (toDelete.isPresent()) {
					toDelete.get().setActive(false);
					toDelete.get().setCommunityLine(null);
					toDelete.get().setNode(null);
					communityLineNodeRepository.save(toDelete.get());
					communityLineNodeRepository.delete(toDelete.get());
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * If the node is in the relation returns true otherwise false
	 * 
	 * @param communityLineNodes List with the relations
	 * @param node               The node that is going to be checked
	 * @return true if the node is already in the relations or false otherwise
	 * 
	 */
	private boolean isThisNodeInTheRelation(List<CommunityLineNode> communityLineNodes, Node node) {
		for (CommunityLineNode relation : communityLineNodes) {
			if (relation.getNode().equals(node)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Deletes acommunityLine with the given id
	 * 
	 * @param id CommunityLine id of the line to be deleted
	 * @return true if all ok or false otherwise
	 */
	public boolean delete(Long id) {
		List<CommunityLineNode> communityLineNodes = communityLineNodeRepository.findAllByCommunityLineId(id);
		for (CommunityLineNode line : communityLineNodes) {
			line.setNode(null); // before the delete we need to break the relation due to constraints
			communityLineNodeRepository.save(line);
			communityLineNodeRepository.delete(line);
		}
		try {
			commLineRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Deletes al relations of the given communiityLine id
	 * 
	 * @param id CommunityLineId that his relations are going to be deleted
	 * @return true if all ok or false otherwise
	 */
	public boolean deleteAllRelations(Long id) {
		try {
			List<CommunityLineNode> communityLineNodes = communityLineNodeRepository.findAllByCommunityLineId(id);
			communityLineNodes.forEach(communityLineNode -> {
				communityLineNode.setActive(false);
				communityLineNode.setCommunityLine(null);
				communityLineNode.setNode(null);
				communityLineNodeRepository.save(communityLineNode);
				communityLineNodeRepository.delete(communityLineNode);
			});
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public CommunityLine getById(Long id) {
		Optional<CommunityLine> cOptional = commLineRepository.findById(id);
		if (cOptional.isPresent()) {
			return cOptional.get();
		} else {
			throw new DataRetrievalFailureException("Community line not found");
		}
	}

}
