/**
 * Repository for comm line
 * @author Marc Serret <marc.serret@crg.eu>
 */
package eu.qcloud.communityline;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CommunityLineRepository extends CrudRepository<CommunityLine, Long> {
	
	public List<CommunityLine> findAll();

}
