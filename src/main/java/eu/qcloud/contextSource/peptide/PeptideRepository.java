package eu.qcloud.contextSource.peptide;

import org.springframework.data.repository.CrudRepository;

public interface PeptideRepository extends CrudRepository<Peptide, Long> {
	Peptide findBySequence(String sequence);
}
