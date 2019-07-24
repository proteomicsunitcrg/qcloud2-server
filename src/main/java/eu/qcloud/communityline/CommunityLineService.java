package eu.qcloud.communityline;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import eu.qcloud.param.ParamRepository;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;
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
		return commLineRepository.save(communityLine);
	}

	public List<CommunityLineNode> getByNodeId() {
		User u = getManagerFromSecurityContext();
        List <CommunityLineNode> lines = communityLineNodeRepository.findAllByNodeId(u.getNode().getId());
        List<CommunityLine> linesAlone = new ArrayList<CommunityLine>();
        for (int i = 0 ; i < lines.size(); i++) {
            linesAlone.add(lines.get(i).getCommunityLine());
        }
		System.out.println(lines.size());
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


}
