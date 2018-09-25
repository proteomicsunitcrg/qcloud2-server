package eu.qcloud.guideset;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.guideset.automatic.AutomaticGuideSet;
import eu.qcloud.guideset.automatic.AutomaticGuideSetService;
import eu.qcloud.threshold.Threshold;

@RestController
public class GuideSetController {

	@Autowired
	private GuideSetService guideSetService;
	
	@Autowired
	private AutomaticGuideSetService automaticGuideSetService;

	@Value("${qcloud.threshold.min-points-manual}")
	private int minFilesForManualGuideSet;
	
	@Value("${qcloud.threshold.min-valid-context-source-points}")
	private int minValidPointsManualThreshold;

	@RequestMapping(value = "/api/guideset/minmanual", method = RequestMethod.GET)
	public int getMinFilesForManualGuideSet(HttpServletResponse response) {
		response.addIntHeader("minpoints", minValidPointsManualThreshold);
		return minFilesForManualGuideSet;
	}
	
	@RequestMapping(value = "/api/guideset/checkguideset/{labSystemApiKey}/{sampleTypeQccv}/{contextSourceApiKey}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MANAGER')")
	public GuideSetContextSourceStatus checkCurrentGuideSet(@PathVariable UUID labSystemApiKey,
			@PathVariable String sampleTypeQccv,
			@PathVariable UUID contextSourceApiKey) {
		return guideSetService.checkCurrentGuideSet(labSystemApiKey, sampleTypeQccv, contextSourceApiKey);
	}

	@RequestMapping(value = "/api/guideset/checkfiles/{labSystemApiKey}/{startDate}/{endDate}/{sampleTypeQccv}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MANAGER')")
	public List<GuideSetContextSourceStatus> getFilesInThreshold(@PathVariable UUID labSystemApiKey, @PathVariable String startDate,
			@PathVariable String endDate, @PathVariable String sampleTypeQccv) {
		Date start = Date.valueOf(startDate);
		Date end = Date.valueOf(endDate);
		return guideSetService.evaluateGuideSet(labSystemApiKey, start, end, sampleTypeQccv);
	}
	
	@RequestMapping(value = "/api/guideset/reset/{labSystemApiKey}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('MANAGER')")
	public void resetLabSystemGuideSet(@PathVariable UUID labSystemApiKey,@RequestBody Threshold threshold) {
		guideSetService.resetLabSystemGuideSetBySampleType(labSystemApiKey, threshold);
	}
	
	@RequestMapping(value="/api/guideset/automatic", method=RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public AutomaticGuideSet save(@RequestBody AutomaticGuideSet automaticGuideSet) {
		return automaticGuideSetService.saveAutomaticGuideSet(automaticGuideSet);
	}
	
	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleConflict(HttpServletResponse response, Exception e) throws IOException {
	    response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}
	

}
