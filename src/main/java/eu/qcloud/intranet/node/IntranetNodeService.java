package eu.qcloud.intranet.node;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.exceptions.NotFoundException;
import eu.qcloud.file.FileRepository;
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

    @Autowired
    FileRepository fileRepo;


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

	public LSStats getLSStats(UUID apiKey) {
        Calendar cal = Calendar.getInstance();  //Get current date/month i.e 27 Feb, 2012
        cal.add(Calendar.MONTH, -1);   //Go to date, 6 months ago 27 July, 2011
        // cal.set(Calendar.DAY_OF_MONTH, 1); //set date, to make it 1 July, 2011
        Date date = cal.getTime();
        LSStats stats = new LSStats();
        stats.setFilesLastMonths(fileRepo.countByLabSystemApiKeyAndCreationDateAfter(apiKey, date));
        stats.setTotalFiles(fileRepo.countByLabSystemApiKey(apiKey));
        return stats;
	}
}