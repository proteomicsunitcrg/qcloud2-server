package eu.qcloud.communitypartner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityPartnerService {

    @Autowired
    CommunityPartnerRepository partnerRepository;

    public List<CommunityPartner> getAll() {
        return partnerRepository.findAll();
    }

	public CommunityPartner createNew(CommunityPartner communityPartner) {
		return partnerRepository.save(communityPartner);
	}
}