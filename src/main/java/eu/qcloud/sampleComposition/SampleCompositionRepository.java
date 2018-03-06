package eu.qcloud.sampleComposition;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import eu.qcloud.contextSource.peptide.Peptide;

public interface SampleCompositionRepository extends CrudRepository<SampleComposition, SampleCompositionId> {
	
	public List<SampleComposition> findByPeptideId(Long peptideId);
	
	public List<PeptidesFromSample> findBySampleTypeName(String name);
	
	
	interface PeptidesFromSample {
		Peptide getPeptide();
		/*
		Long getPeptideId();
		String getPeptideName();
		String getPeptideAbbreviatedSequence();
		String getPeptideSequence();
		*/
		
	}
}
