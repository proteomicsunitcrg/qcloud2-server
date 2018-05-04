package eu.qcloud.threshold;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class ThresholdController {

	@Autowired
	private ThresholdService thresholdService;
	
	@RequestMapping(value="/api/threshold", method = RequestMethod.GET)
	public List<Threshold> getAll() {
		return thresholdService.getAll();
		
	}
}
