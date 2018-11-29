package eu.qcloud.thresholdnonconformity;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.thresholdnonconformity.ThresholdNonConformityRepository.ThreholdNonConformityWithoutThresholdParams;

@RestController
public class ThresholdNonConformityController {

	@Autowired
	private ThresholdNonConformityService thresholdNonConformityService;
	
	@RequestMapping(value = "/api/thresholdnonconformity", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List<ThreholdNonConformityWithoutThresholdParams> getAllThresholdNonConformity() {
		return thresholdNonConformityService.findAll();
	}

	@RequestMapping(value = "/api/thresholdnonconformity/{labSystemApiKey}/{page}", method = RequestMethod.GET)
	public List<ThreholdNonConformityWithoutThresholdParams> getLabSystemNonConformities(
			@PathVariable UUID labSystemApiKey, @PathVariable int page, HttpServletResponse response) {
		response.addIntHeader("total", (int)thresholdNonConformityService.getTotalByLabSystem(labSystemApiKey));
		return thresholdNonConformityService.findLabSystemNonConformitiesByPage(labSystemApiKey, page);
	}
	
	@RequestMapping(value = "/api/thresholdnonconformity/{labSystemApiKey}/{sampleTypeQQCV}/{page}", method = RequestMethod.GET)
	public List<ThreholdNonConformityWithoutThresholdParams> getLabSystemNonConformitiesBySampleType(
			@PathVariable UUID labSystemApiKey,@PathVariable String sampleTypeQQCV, @PathVariable int page, HttpServletResponse response) {
		response.addIntHeader("total", (int)thresholdNonConformityService.getTotalByLabSystemAndSampleType(labSystemApiKey, sampleTypeQQCV));
		return thresholdNonConformityService.findLabSystemNonConformitiesBySampleTypeAndByPage(labSystemApiKey,sampleTypeQQCV, page);
	}

}
