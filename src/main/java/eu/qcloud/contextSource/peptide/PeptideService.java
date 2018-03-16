package eu.qcloud.contextSource.peptide;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PeptideService {
	@Autowired
	private PeptideRepository peptideRepository;
	
	public Peptide addPeptide(Peptide p) {
		return peptideRepository.save(p);		
	}
	
	public List<Peptide> getAllPeptides() {
		return peptideRepository.findAllPeptides();
	}


	
	
	public Optional<Peptide> getPeptideById(Long peptideId) {
		return peptideRepository.findById(peptideId);
	}

	public List<Peptide> getPeptidesByIds(List<Peptide> peptides) {
		List<Peptide> foundPeptides = new ArrayList<>();		
		for(Peptide pep: peptides) {			
			foundPeptides.add(peptideRepository.findById(pep.getId()).get());
		}
		for(Peptide p: foundPeptides) {
			System.out.println(p.getSequence());
		}
		
		return foundPeptides;
		
	}

	public Peptide findPeptideBySequence(String contextSourceSequence) {
		return peptideRepository.findBySequence(contextSourceSequence);		
	}

	public Optional<Peptide> findPeptideById(Long peptideId) {
		return peptideRepository.findById(peptideId);
	}
	
	public Peptide findePeptideByName(String name) {
		return peptideRepository.findByName(name);
	}
	
}
