package eu.qcloud.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.view.DefaultViewRepository.DefaultViewDisplay;
import eu.qcloud.view.DefaultViewRepository.DefaultViewName;
import eu.qcloud.view.UserViewRepository.ViewDisplay;

@Service
public class ViewService {
	
	@Autowired
	private ViewRepository viewRepository;
	@Autowired
	private UserViewRepository userViewRepository;
	@Autowired
	private DefaultViewRepository defaultViewRepository;
	
	public List<View> getAllViews() {
		List<View> views = new ArrayList<>();
		viewRepository.findAll().forEach(views::add);
		return views;
	}
	
	public List<UserView> getAllUserViews() {
		List<UserView> userViews = new ArrayList<>();
		userViewRepository.findAll().forEach(userViews::add);
		return userViews;
	}
	
	public List<ViewDisplay> getIds(Long viewId) {
		return userViewRepository.findByViewId(viewId);
	}
	
	public List<DefaultViewDisplay> getDefaults(Long viewId) {
		return this.defaultViewRepository.findByViewId(viewId);
	}
	
	public DefaultViewName getDefaultViewByCV(Long cvId) {
		return defaultViewRepository.findFirstByChartCvId(cvId);
	}
	
}
