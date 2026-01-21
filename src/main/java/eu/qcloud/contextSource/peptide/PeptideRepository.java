package eu.qcloud.contextSource.peptide;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface for peptide
 *
 * @author dmancera
 *
 */
public interface PeptideRepository extends CrudRepository<Peptide, Long> {
	
	/**
	 * Direct database query for peptide by exact sequence match
	 * Used internally by findBySequence() - do not call directly
	 */
	@Query("select p from peptide p where p.sequence = ?1")
	Peptide findBySequenceExact(String sequence);
	
	/**
	 * Find peptide by sequence with flexible underscore handling.
	 * This method tries to match the sequence as-is first, then tries
	 * with/without trailing underscore to handle format inconsistencies
	 * between the database and incoming data.
	 * 
	 * @param sequence The peptide sequence to search for
	 * @return Peptide if found (with or without trailing underscore), null otherwise
	 */
	default Peptide findBySequence(String sequence) {
		// Try exact match first
		Peptide peptide = findBySequenceExact(sequence);
		
		// If not found and sequence doesn't end with underscore, try adding one
		if (peptide == null && !sequence.endsWith("_")) {
			peptide = findBySequenceExact(sequence + "_");
		} 
		// If not found and sequence ends with underscore, try without it
		else if (peptide == null && sequence.endsWith("_")) {
			peptide = findBySequenceExact(sequence.substring(0, sequence.length() - 1));
		}
		
		return peptide;
	}

	@Query("select p from peptide p order by p.abbreviated")
	List<Peptide> findAllPeptides();

	List<Peptide> findByAbbreviated(String abbreviated);

	public interface OnlyPeptide {
		Long getId();

		String getName();

		String getSequence();

		String getAbbreviated();

		Float getMz();

		Integer getCharge();
	}

	Peptide findByName(String name);

	@Query("select p from peptide p where p.sequence = ?1")
	OnlyPeptide findOnlyBySequence(String sequence);
}