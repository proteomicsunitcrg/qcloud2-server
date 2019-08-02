/**
 * Repository for message
 * @author Marc Serret <marc.serret@crg.eu>
 */
package eu.qcloud.message;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

	public Message findFirstByOrderByIdDesc();

}
