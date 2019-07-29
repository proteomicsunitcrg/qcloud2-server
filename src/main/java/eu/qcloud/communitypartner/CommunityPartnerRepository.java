package eu.qcloud.communitypartner;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPartnerRepository extends CrudRepository<CommunityPartner, Long> {

    public List<CommunityPartner> findAll();

}