package eu.qcloud.contextSource.peptide.isotopologue;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

// @RestController
public class IsotopologueController {

	// @Autowired
	private IsotopologueService isotopologueService;
	
	// @RequestMapping(value="/api/contextsource/isotopologue",method= RequestMethod.POST)
	public void add(@RequestBody Isotopologue i) {
		isotopologueService.add(i);
	}
	
	// @RequestMapping(value="/api/contextsource/isotopologue/main/{peptideId}",method= RequestMethod.GET)
	public List<Isotopologue> findAllIsotopologueByMainPeptide(@PathVariable Long peptideId) {
		return isotopologueService.findByMainPeptide(peptideId);		
	}
}
