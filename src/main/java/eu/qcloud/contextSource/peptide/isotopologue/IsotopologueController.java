package eu.qcloud.contextSource.peptide.isotopologue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IsotopologueController {

	@Autowired
	private IsotopologueService isotopologueService;
	
	@RequestMapping(value="/api/contextsource/isotopologue",method= RequestMethod.POST)
	public void add(@RequestBody Isotopologue i) {
		isotopologueService.add(i);
	}
}
