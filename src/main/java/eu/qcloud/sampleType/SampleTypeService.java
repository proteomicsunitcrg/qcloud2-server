package eu.qcloud.sampleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.sampleType.SampleTypeRepository.SampleTypeOnlyName;
import eu.qcloud.sampleType.SampleTypeRepository.WithPeptide;

@Service
@Transactional
public class SampleTypeService {
	@Autowired
	private SampleTypeRepository sampleTypeRepository;
	
	public SampleType addSampleType(SampleType s) {
		return sampleTypeRepository.save(s);		
	}
	
	public List<SampleTypeOnlyName> getAllSampleType() {
		List<SampleTypeOnlyName> samples = new ArrayList<>();
		
		sampleTypeRepository.findAllSampleTypes().forEach(samples::add);
		
		return samples;
	}
	/*
	public SampleType addPeptideToSampleType(SampleType s, List<Peptide> peptides) {		
		s.setPeptides(peptides);
		return sampleTypeRepository.save(s);
	}
	*/
	
	
	public Optional<SampleType> getSampleTypeById(Long id) {
		return sampleTypeRepository.findById(id);
		
	}
	
	public SampleTypeOnlyName getSampleById(Long id) {
		return sampleTypeRepository.findOnlyNameById(id);
	}

	public List<WithPeptide> getAllSampleTypeWithPeptide() {
		return sampleTypeRepository.findAllSampleType();
	}
	
}
