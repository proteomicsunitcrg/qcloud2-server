/**
 * Repository for comm line
 * @author Marc Serret <marc.serret@crg.eu>
 */
package eu.qcloud.communityline;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityLineNodeRepository extends CrudRepository<CommunityLineNodeRelation, Long> {

	public List<CommunityLineNodeRelation> findAll();

	public List<CommunityLineNodeRelation> findAllByNodeId(Long id);

}
