package eu.qcloud.CV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CVService {
	@Autowired
	private CVRepository cvRepository;
	
	public CV addCV() {
		return null;
	}
	
	
}
