package eu.qcloud.guideset;

import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuideSetController {

	@Autowired
	private GuideSetService guideSetService;

	@Value("${qcloud.threshold.min-points-manual}")
	private int minFilesForManualGuideSet;

	@RequestMapping(value = "/api/guideset/minmanual", method = RequestMethod.GET)
	public int getMinFilesForManualGuideSet() {
		return minFilesForManualGuideSet;
	}

	@RequestMapping(value = "/api/guideset/checkfiles/{labSystemApiKey}/{startDate}/{endDate}/{sampleTypeQccv}", method = RequestMethod.GET)
	public long getFilesInThreshold(@PathVariable UUID labSystemApiKey, @PathVariable String startDate,
			@PathVariable String endDate, @PathVariable String sampleTypeQccv) {
		System.out.println(startDate);
		Date start = Date.valueOf(startDate);
		Date end = Date.valueOf(endDate);
		return guideSetService.getFilesInThreshold(labSystemApiKey, start, end, sampleTypeQccv);
	}

}
