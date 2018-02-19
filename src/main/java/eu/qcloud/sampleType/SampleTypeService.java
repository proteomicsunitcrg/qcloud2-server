package eu.qcloud.sampleType;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.sampleType.SampleTypeRepository.OnlyName;

@Service
@Transactional
public class SampleTypeService {
	@Autowired
	private SampleTypeRepository sampleTypeRepository;
	
	public SampleType addSampleType(SampleType s) {
		return sampleTypeRepository.save(s);		
	}
	
	public List<SampleType> getAllSampleType() {
		List<SampleType> samples = new ArrayList<>();
		sampleTypeRepository.findAll().forEach(samples::add);
		return samples;
	}
	
	public SampleType addPeptideToSampleType(SampleType s, List<Peptide> peptides) {		
		s.setPeptides(peptides);
		return sampleTypeRepository.save(s);
	}
	
	
	public SampleType getSampleTypeById(Long id) {
		return sampleTypeRepository.findOne(id);
		
	}
	
	public OnlyName getSampleById(Long id) {
		return sampleTypeRepository.findById(id);
	}
	
}
