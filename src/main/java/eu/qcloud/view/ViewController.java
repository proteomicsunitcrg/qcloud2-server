package eu.qcloud.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.view.DefaultViewRepository.DefaultViewDisplay;
import eu.qcloud.view.DefaultViewRepository.DefaultViewName;
import eu.qcloud.view.UserViewRepository.ViewDisplay;

@RestController
public class ViewController {
	
	@Autowired
	private ViewService viewService;
	
	@RequestMapping(value="/api/views", method=RequestMethod.GET)
	public List<View> getAllViews() {
		return viewService.getAllViews(); 
	}
	@RequestMapping(value="/api/views/user", method=RequestMethod.GET)
	public List<UserView> getAllUserViews() {
		return viewService.getAllUserViews();
	}
	public List<ViewDisplay> getIds(@PathVariable Long viewId) {
		return viewService.getIds(viewId);
	}
	
	
	@RequestMapping(value="/api/views/default/view/{viewId}", method=RequestMethod.GET)
	public List<DefaultViewDisplay> getDefaultIds(@PathVariable Long viewId) {
		return viewService.getDefaults(viewId);
	}
	@RequestMapping(value="/api/views/default/{cvId}", method=RequestMethod.GET)
	public DefaultViewName getDefaultViewByCv(@PathVariable Long cvId) {
		return viewService.getDefaultViewByCV(cvId);
	}

}
