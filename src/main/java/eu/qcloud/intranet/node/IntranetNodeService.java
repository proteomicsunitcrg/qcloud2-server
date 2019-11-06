package eu.qcloud.intranet.node;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.exceptions.NotFoundException;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.node.Node;
import eu.qcloud.node.NodeRepository;

@Service
public class IntranetNodeService {

    @Autowired
    NodeRepository nodeRepo;

    @Autowired
    LabSystemRepository lsRepo;

    public List<Node> getAll() {
        return nodeRepo.findAll();
    }

    public Node getByApiKey(UUID apiKey) {
        Optional <Node> node = nodeRepo.findByApiKeyOptional(apiKey);
        if(node.isPresent()) {
            return node.get();
        } else {
            throw new NotFoundException("Node not found");
        }
    }

	public List<LabSystem> getLsByNodeApiKey(UUID apiKey) {
        Node node = nodeRepo.findByApiKey(apiKey);
		return lsRepo.findAllByNode(node.getId());
	}
}