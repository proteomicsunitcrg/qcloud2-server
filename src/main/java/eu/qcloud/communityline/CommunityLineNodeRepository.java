/**
 * Repository for comm line
 * @author Marc Serret <marc.serret@crg.eu>
 */
package eu.qcloud.communityline;

import java.util.List;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityLineNodeRepository extends CrudRepository<CommunityLineNode, Long> {

    public List<CommunityLineNode> findAll();

    public List<CommunityLineNode> findAllByNodeId(Long id);

    public List<CommunityLineNode> findAllByCommunityLineId(Long id);

    public Optional<CommunityLineNode> findAllByNodeIdAndCommunityLineId(Long nodeId, Long commId);

    public CommunityLineNode findById(long id);

}
