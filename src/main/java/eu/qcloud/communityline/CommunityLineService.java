package eu.qcloud.communityline;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.param.ParamRepository;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;
import eu.qcloud.websocket.WebSocketService;
/**
 * Main service for community line
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



	public List<CommunityLine> getAllCommunityLines() {
		List<CommunityLine> allLines = new ArrayList<>();
		commLineRepository.findAll().forEach(allLines::add);
		return allLines;		
	}

	public CommunityLine saveCommunityLine(CommunityLine communityLine) {
		communityLine.setParam(paramRepository.findByQccv(communityLine.getParam().getqCCV()));
		communityLine.setSampleType(sampleTypeRepository.findByQualityControlControlledVocabulary(communityLine.getSampleType().getQualityControlControlledVocabulary()).get());
		communityLine.setApiKey(UUID.randomUUID());
		return commLineRepository.save(communityLine);
	}

}
