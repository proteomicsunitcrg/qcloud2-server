/**
 * Main service for node
 * @author Daniel Mancera <daniel.mancera@crg.eu>
 */

package eu.qcloud.node;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class NodeService {
	
	@Autowired
	NodeRepository nodeRepository;
	
	/**
	 * Try to insert a new node in the database
	 * @author Daniel Mancera
	 * @param n the node to insert
	 * @return <Node> inserted node or <null> if error
	 */
	public Node createNode(Node n) {
		
		return nodeRepository.save(n);
	}
	
	/**
	 * Delete a node
	 * @param the node to delete
	 * @return true or false
	 */	
	public boolean deleteNode(Node n) {
		nodeRepository.delete(n);
		if(nodeRepository.findOneById(n.getId()) == null) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public Node getNodeByNodeUuid(UUID nodeUuid) {
		return nodeRepository.findOneByApiKey(nodeUuid);
	}
	
	public Node getNodeByNodeId(Long nodeId) {
		return nodeRepository.findOneById(nodeId);
	}

	public List<Node> getAllNodes() {
		List<Node> nodes = new ArrayList<>();
		nodeRepository.findAll().forEach(nodes::add);
		return nodes;		
	}
	

}
