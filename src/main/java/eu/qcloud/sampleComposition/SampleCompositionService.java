package eu.qcloud.sampleComposition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.sampleComposition.SampleCompositionRepository.PeptidesFromSample;
/**
 * Service for sampleComposition
 * @author dmancera
 *
 */
@Service
@Transactional
public class SampleCompositionService {
	@Autowired
	private SampleCompositionRepository sampleCompositionRepository;
	
	/**
	 * Before add the sample composition an id(composite) must be generated. 
	 * @param sampleComposition
	 * @return
	 */
	public SampleComposition addSampleComposition(SampleComposition sampleComposition) {
		SampleCompositionId scId = new SampleCompositionId();
		scId.setPeptideId(sampleComposition.getPeptide().getId());
		scId.setSampleTypeId(sampleComposition.getSampleType().getId());
		sampleComposition.setSampleCompositionId(scId);
		
		return sampleCompositionRepository.save(sampleComposition);
	}
	
	public List<SampleComposition> getAllSampleComposition() {
		List<SampleComposition> samples = new ArrayList<>();
		sampleCompositionRepository.findAll().forEach(samples::add);
		return samples;
	}

	public List<SampleComposition> getAllSampleCompositionByPeptideId(Long peptideId) {
		return sampleCompositionRepository.findByPeptideId(peptideId);
	}
	
	public Optional<SampleComposition> getSampleCompositionById(SampleCompositionId scId) {
		return sampleCompositionRepository.findById(scId);
	}

	public boolean deleteSampleComposition(SampleComposition sc) {
		sampleCompositionRepository.delete(sc);
		return true;
	}

	public List<PeptidesFromSample> findAllPeptidesBySampleTypeName(String sampleTypeName) {
		return sampleCompositionRepository.findBySampleTypeName(sampleTypeName);
	}
	
	public List<SampleComposition> getAllSampleCompositionBySampleTypeQQCV(String qqcv) {
		return sampleCompositionRepository.findBySampleTypeQualityControlControlledVocabulary(qqcv);
	}
	
	

}
