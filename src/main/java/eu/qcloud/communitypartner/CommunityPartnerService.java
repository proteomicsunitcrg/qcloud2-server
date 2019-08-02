package eu.qcloud.communitypartner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.communityline.CommunityLine;
import eu.qcloud.communityline.CommunityLineRepository;

@Service
public class CommunityPartnerService {

    @Autowired
    CommunityPartnerRepository partnerRepository;

    @Autowired
    CommunityLineRepository communityLineRepository;

    public List<CommunityPartner> getAll() {
        return partnerRepository.findAll();
    }

    public CommunityPartner createNew(CommunityPartner communityPartner) {
        return partnerRepository.save(communityPartner);
    }

    public CommunityPartner findById(Long id) {
        return partnerRepository.findById(id).get();
    }

    public boolean delete(Long id) {
        List<CommunityLine> communityLines = communityLineRepository.findAllByCommunityPartnerId(id);
        for (CommunityLine line : communityLines) {
            line.setCommunityPartner(null);
            communityLineRepository.save(line);
        }
        try {
            partnerRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}