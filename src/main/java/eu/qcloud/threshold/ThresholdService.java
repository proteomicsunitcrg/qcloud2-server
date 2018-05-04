package eu.qcloud.threshold;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThresholdService {
	
	@Autowired
	private ThresholdRepository thresholdRepository;
	
	public List<Threshold> getAll() {
		List<Threshold> thresholds = new ArrayList<>();
		thresholdRepository.findAll()
			.forEach(thresholds::add);
		return thresholds;
		
	}

}
