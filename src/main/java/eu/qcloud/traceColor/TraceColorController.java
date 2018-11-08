package eu.qcloud.traceColor;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TraceColorController {
	
	@Autowired
	private TraceColorService traceColorService;
	
	@RequestMapping(value="/api/color", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public List<TraceColor> getTraceColors() {
		return traceColorService.getTraceColors();
	}
	
	@RequestMapping(value="/api/color", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public void addNewTraceColor(@RequestBody TraceColor traceColor) {
		traceColorService.addNewTraceColor(traceColor);
	}
	
	@RequestMapping(value="/api/color/{traceColorApiKey}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public void updateTraceColor(@RequestBody TraceColor traceColor,@PathVariable UUID traceColorApiKey) {
		traceColorService.updateTraceColor(traceColorApiKey, traceColor);
	}

}
