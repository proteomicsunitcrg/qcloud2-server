package eu.qcloud.contextSource.peptide;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import eu.qcloud.sampleType.SampleTypeRepository.SampleTypeOnlyName;

public interface PeptideRepository extends CrudRepository<Peptide, Long> {
	Peptide findBySequence(String sequence);
	
	@Query("select p from peptide p")
	List<PeptideWithSampleType> findAllPeptides();
	
	public interface OnlyPeptide {
		Long getId();
		String getName();
		String getSequence();
		String getAbbreviatedSequence();
	}
	
	public interface PeptideWithSampleType {
		Long getId();
		String getName();
		String getSequence();
		String getAbbreviatedSequence();
		List<SampleTypeOnlyName> getSampleTypes();
	}
}
