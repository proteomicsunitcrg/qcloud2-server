package eu.qcloud.contextSource.peptide.isotopologue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

// @Service
public class IsotopologueService {
	
	@Autowired
	private IsotopologueRepository isotopologueRepository;
	
	public void add(Isotopologue isotopologue) {
		isotopologueRepository.save(isotopologue);
	}

	public List<Isotopologue> findByMainPeptide(Long mainPeptideId) {
		return isotopologueRepository.findByMainPeptideId(mainPeptideId);
	}

}
