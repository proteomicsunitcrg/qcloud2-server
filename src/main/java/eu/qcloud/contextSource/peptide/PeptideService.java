package eu.qcloud.contextSource.peptide;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import eu.qcloud.contextSource.peptide.PeptideRepository.OnlyPeptide;
import eu.qcloud.traceColor.TraceColor;
import eu.qcloud.traceColor.TraceColorRepository;

/**
 * Service for peptides
 * 
 * @author dmancera
 *
 */
@Service
@Transactional
public class PeptideService {
	@Autowired
	private PeptideRepository peptideRepository;

	@Autowired
	private TraceColorRepository traceColorRepository;

	/**
	 * Add a new peptide into the database
	 * 
	 * @param p the peptide to ad
	 * @return the inserted peptide
	 */
	public Peptide addPeptide(Peptide p) {
		Optional<TraceColor> traceColor = traceColorRepository.findByApiKey(p.getTraceColor().getApiKey());
		if (!traceColor.isPresent()) {
			throw new DataIntegrityViolationException("Invalid color");
		}
		p.setTraceColor(traceColor.get());
		p.setApiKey(UUID.randomUUID());
		return peptideRepository.save(p);
	}

	public Peptide updatePeptide(Peptide peptide, String peptideSequence) {
		Peptide p = peptideRepository.findBySequence(peptideSequence);
		Optional<TraceColor> traceColor = traceColorRepository.findByApiKey(peptide.getTraceColor().getApiKey());

		if (!traceColor.isPresent()) {
			throw new DataIntegrityViolationException("Invalid color");
		}

		if (checkIfPeptideNameExists(peptide.getName()) && !peptide.getName().equals(p.getName())) {
			throw new DataIntegrityViolationException("This peptide already exists");
		} else {
			p.setAbbreviated(peptide.getAbbreviated());
			p.setMz(peptide.getMz());
			p.setCharge(peptide.getCharge());
			p.setName(peptide.getName());
			p.setSequence(peptideSequence);
			p.setShadeGrade(peptide.getShadeGrade());
			p.setTraceColor(traceColor.get());
			return peptideRepository.save(p);
		}
	}

	/**
	 * Get all peptides that are not isotopologues
	 * 
	 * @return
	 */
	public List<Peptide> getAllPeptides() {
		return peptideRepository.findAllPeptides();
	}

	/**
	 * Find a peptide by its id
	 * 
	 * @param peptideId the id
	 * @return a peptide
	 */
	public Optional<Peptide> getPeptideById(Long peptideId) {
		return peptideRepository.findById(peptideId);
	}

	public Peptide getPeptideBySequence(String peptideSequence) {
		return peptideRepository.findBySequence(peptideSequence);
	}

	/**
	 * Find peptides by its id
	 * 
	 * @param peptides a list of peptides with ids
	 * @return a list of peptides
	 */
	public List<Peptide> getPeptidesByIds(List<Peptide> peptides) {
		List<Peptide> foundPeptides = new ArrayList<>();
		for (Peptide pep : peptides) {
			foundPeptides.add(peptideRepository.findById(pep.getId()).get());
		}
		for (Peptide p : foundPeptides) {
			System.out.println(p.getSequence());
		}

		return foundPeptides;

	}

	/**
	 * Find a peptide by its sequence. Use this function to return a JSON
	 * 
	 * @param sequence the peptide sequence
	 * @return a peptide
	 */
	public OnlyPeptide findOnlyPeptideBySequence(String sequence) {
		return peptideRepository.findOnlyBySequence(sequence);
	}

	/**
	 * Find a peptide by its sequence
	 * 
	 * @param contextSourceSequence
	 * @return a full peptide object
	 */
	public Peptide findPeptideBySequence(String contextSourceSequence) {
		return peptideRepository.findBySequence(contextSourceSequence);
	}

	/**
	 * Find a peptide by name
	 * 
	 * @param name
	 * @return
	 */
	public Peptide findePeptideByName(String name) {
		return peptideRepository.findByName(name);
	}

	private boolean checkIfPeptideNameExists(String name) {
		if (peptideRepository.findByName(name) != null) {
			return true;
		} else {
			return false;
		}
	}

}
