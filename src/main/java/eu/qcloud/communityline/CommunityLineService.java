package eu.qcloud.communityline;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import eu.qcloud.node.Node;
import eu.qcloud.node.NodeRepository;
import eu.qcloud.param.ParamRepository;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;
import eu.qcloud.traceColor.TraceColor;
import eu.qcloud.traceColor.TraceColorRepository;
import eu.qcloud.websocket.WebSocketService;

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

	public List<CommunityLine> getAllCommunityLines() {
		List<CommunityLine> allLines = new ArrayList<>();
		commLineRepository.findAll().forEach(allLines::add);
		System.out.println(allLines.get(0).toString());
		return allLines;
	}

	public CommunityLine saveCommunityLine(CommunityLine communityLine) {
		communityLine.setParam(paramRepository.findByQccv(communityLine.getParam().getqCCV()));
		communityLine.setSampleType(sampleTypeRepository.findByQualityControlControlledVocabulary(
				communityLine.getSampleType().getQualityControlControlledVocabulary()).get());
		communityLine.setApiKey(UUID.randomUUID());
		Optional <TraceColor> trace =traceColorRepository.findById(16l);
		if (trace.isPresent()) {
			communityLine.setTraceColor(trace.get());
		} else {	//TODO : Make a trace selector and remove this
			TraceColor newTrace = new TraceColor();
			newTrace.setApiKey(UUID.randomUUID());
			newTrace.setMainColor("rgba(1, 1, 1, 1.0)");
			communityLine.setTraceColor(traceColorRepository.save(newTrace));
		}
		return commLineRepository.save(communityLine);
	}

	public List <Node> getNodesInCommunityLineRelation(Long id) {
		List <CommunityLineNode> communityLineNodes = communityLineNodeRepository.findAllByCommunityLineId(id);
		List <Node> nodes = new ArrayList<Node>();
		for (CommunityLineNode communityLineNode: communityLineNodes) {
			nodes.add(communityLineNode.getNode());
		}
		return nodes;
	}

	public List<CommunityLineNode> getByNodeId() {
		User u = getManagerFromSecurityContext();
        List <CommunityLineNode> lines = communityLineNodeRepository.findAllByNodeId(u.getNode().getId());
		return lines;
	}

	public CommunityLineNode updateActive(CommunityLineNode communityLineNode) {
        // Because client dont have the full entity
        CommunityLineNode test = communityLineNodeRepository.findById(communityLineNode.getId());
        test.setActive(!test.isActive());
        communityLineNodeRepository.save(test);
        return test;
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

	public boolean makeDeleteRelation(UUID nodeKey, UUID lineKey) {
		CommunityLine communityLine = commLineRepository.findByApiKey(lineKey);
		Node node = nodeRepository.findByApiKey(nodeKey);
		Optional<CommunityLineNode> commuOptional = communityLineNodeRepository.findAllByNodeIdAndCommunityLineId(node.getId(), communityLine.getId());
		if (commuOptional.isPresent()) {
			System.out.println("existe");
			commuOptional.get().setActive(!commuOptional.get().isActive());
			communityLineNodeRepository.save(commuOptional.get());
		} else {
			CommunityLineNode ffff = new CommunityLineNode();
			ffff.setActive(false);
			ffff.setCommunityLine(communityLine);
			ffff.setNode(node);
			communityLineNodeRepository.save(ffff);
			System.out.println("no existte");
		}

		return false;
	}

	public boolean delete(Long id) {
		List<CommunityLineNode> communityLineNodes = communityLineNodeRepository.findAllByCommunityLineId(id);
		for(CommunityLineNode line : communityLineNodes) {
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


}
