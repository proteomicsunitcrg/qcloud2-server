/**
 * Repository for node
 * @author Daniel Mancera <daniel.mancera@crg.eu>
 */
package eu.qcloud.message;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
	
	public Message findFirstByOrderByIdDesc();
	// Node findByApiKey(UUID apiKey);
	
	// Node findOneByApiKey(UUID apikey);
	
	// @Query("SELECT n from Node n where apiKey=?1")
	// Node buscar(UUID apiKey);
	
	// Node findOneById(Long id);
	
	// List<OnlyNodeName> findByName(String name);
	
	// @Query("SELECT n FROM Node n ")
	// public List<OnlyNodeName> getNodes();
	
	// public interface OnlyNodeName {
	// 	String getName();
	// }
}
