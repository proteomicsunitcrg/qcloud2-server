package eu.qcloud.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {
	
	@Autowired
	private ViewService viewService;
	
	@RequestMapping(value="/api/views", method=RequestMethod.GET)
	public List<View> getAllViews() {
		return viewService.getAllViews(); 
	}
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/views/default", method=RequestMethod.POST)
	public View addDefaultView(@RequestBody View view) {
		return viewService.addView(view);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/views/default/layout", method=RequestMethod.POST)
	public List<ViewDisplay> addViewDisplayToView(@RequestBody List<DefaultView> layout) {
		
		System.out.println("hola");
		return viewService.addDefaultViewDisplay(layout);
		
	}
}
