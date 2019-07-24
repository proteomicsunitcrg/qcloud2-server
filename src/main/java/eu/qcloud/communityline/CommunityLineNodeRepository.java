/**
 * Repository for comm line
 * @author Marc Serret <marc.serret@crg.eu>
 */
package eu.qcloud.communityline;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityLineNodeRepository extends CrudRepository<CommunityLineNode, Long> {

	public List<CommunityLineNode> findAll();

    public List<CommunityLineNode> findAllByNodeId(Long id);
    
    public CommunityLineNode findAllByNodeIdAndCommunityLineId(Long nodeId, Long commId);

    // @Override
    public CommunityLineNode findById(long id);

}
