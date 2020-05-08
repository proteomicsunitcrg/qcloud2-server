package eu.qcloud.sampleComposition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.contextSource.peptide.PeptideRepository;
import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.sampleComposition.SampleCompositionRepository.PeptidesFromSample;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;

/**
 * Service for sampleComposition
 *
 * @author dmancera
 *
 */
@Service
@Transactional
public class SampleCompositionService {
	@Autowired
	private SampleCompositionRepository sampleCompositionRepository;

	@Autowired
	private PeptideRepository peptideRepository;

	@Autowired
	private SampleTypeRepository sampleTypeRepository;

	/**
	 * Before add the sample composition an id(composite) must be generated.
	 *
	 * @param sampleComposition
	 * @return
	 */
	public SampleComposition addSampleComposition(SampleComposition sampleComposition) {

		Peptide p = peptideRepository.findBySequence(sampleComposition.getPeptide().getSequence());
		Optional<SampleType> st = sampleTypeRepository.findByQualityControlControlledVocabulary(
				sampleComposition.getSampleType().getQualityControlControlledVocabulary());
		if (!st.isPresent() || p == null) {
			throw new InvalidActionException("Peptide or sample type not found");
		}
		SampleCompositionId scId = new SampleCompositionId();
		scId.setPeptideId(p.getId());
		scId.setSampleTypeId(st.get().getId());
		sampleComposition.setSampleType(st.get());
		sampleComposition.setPeptide(p);
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

	public List<SampleComposition> getAllSampleCompositionByPeptideSequence(String peptideSequence) {
		return sampleCompositionRepository.findByPeptideSequence(peptideSequence);
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

	public void deleteFromSampleComposition(String peptideSequence, String sampleTypeQCCV) {
		Peptide p = peptideRepository.findBySequence(peptideSequence);
		Optional<SampleType> st = sampleTypeRepository.findByQualityControlControlledVocabulary(sampleTypeQCCV);
		if (!st.isPresent()) {
			throw new InvalidActionException("Sample type not found");
		}
		sampleCompositionRepository.deleteSampleCompositionByPeptideIdAndSampleTypeId(p.getId(), st.get().getId());

	}

}
