package eu.qcloud.sampleComposition;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SampleCompositionService {
	@Autowired
	private SampleCompositionRepository sampleCompositionRepository;
	
	
	public SampleComposition addSampleComposition(SampleComposition sampleComposition) {
		return sampleCompositionRepository.save(sampleComposition);
	}
	
	public List<SampleComposition> getAllSampleComposition() {
		List<SampleComposition> samples = new ArrayList<>();
		sampleCompositionRepository.findAll().forEach(samples::add);
		return samples;
	}
}
