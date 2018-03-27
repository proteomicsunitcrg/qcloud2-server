package eu.qcloud.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
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
		view.setDefault(true);	
		return viewService.addView(view);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/views/default/layout", method=RequestMethod.POST)
	public List<ViewDisplay> addViewDisplayToView(@RequestBody List<DefaultView> layout) {
		return viewService.addDefaultViewDisplay(layout);
	}
	
	/**
	 * This method returns the view object by CV
	 * @param cvId
	 * @return
	 */
	@RequestMapping(value="/api/views/default/{cvId}", method=RequestMethod.GET)
	public View getViewByCVId(@PathVariable Long cvId) {
		View v =viewService.getDefaultViewByCV(cvId); 
		return v;
	}
	
	@RequestMapping(value="/api/views/default/view/{viewId}", method=RequestMethod.GET)
	public List<DefaultView> getDefaultViewDisplayByViewId(@PathVariable Long viewId) {
		return viewService.getDefaultViewDisplayByViewId(viewId);
	}
}
