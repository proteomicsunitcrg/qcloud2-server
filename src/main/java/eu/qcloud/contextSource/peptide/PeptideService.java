package eu.qcloud.contextSource.peptide;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.contextSource.peptide.PeptideRepository.PeptideWithSampleType;

@Service
@Transactional
public class PeptideService {
	@Autowired
	private PeptideRepository peptideRepository;
	
	public Peptide addPeptide(Peptide p) {
		return peptideRepository.save(p);		
	}
	
	public List<Peptide> getAllPeptides() {
		List<Peptide> peptides = new ArrayList<>();
		peptideRepository.findAll().forEach(peptides::add);
		return peptides;
	}
	
	public List<PeptideWithSampleType> getOnlyPeptides() {
		return peptideRepository.findAllPeptides();
		
	}
	
	
	public Peptide getPeptideById(Long peptideId) {
		return peptideRepository.findOne(peptideId);
	}

	public List<Peptide> getPeptidesByIds(List<Peptide> peptides) {
		List<Peptide> foundPeptides = new ArrayList<>();		
		for(Peptide pep: peptides) {			
			foundPeptides.add(peptideRepository.findOne(pep.getId()));
		}
		for(Peptide p: foundPeptides) {
			System.out.println(p.getSequence());
		}
		
		return foundPeptides;
		
	}

	public Peptide findPeptideBySequence(String quantificationSourceSequence) {
		return peptideRepository.findBySequence(quantificationSourceSequence);		
	}
	
}
