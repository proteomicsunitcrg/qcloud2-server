package eu.qcloud.contextSource.peptide;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PeptideRepository extends CrudRepository<Peptide, Long> {
	Peptide findBySequence(String sequence);
	
	@Query("select p from peptide p order by p.abbreviated")
	List<Peptide> findAllPeptides();
	
	// public List<Peptide> findAllOrderByAbbreviatedSequence();
	
	public interface OnlyPeptide {
		Long getId();
		String getName();
		String getSequence();
		String getAbbreviated();
	}
	
	Peptide findByName(String name);
	
	@Query("select p from peptide p where p.sequence = ?1")
	OnlyPeptide findOnlyBySequence(String sequence);
}
