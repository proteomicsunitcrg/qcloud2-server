package eu.qcloud.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewService {

	@Autowired
	private ViewRepository viewRepository;

	@Autowired
	private ViewDisplayRepository viewDisplayRepository;
	
	public List<View> getAllViews() {
		List<View> views = new ArrayList<>();
		viewRepository.findAll().forEach(views::add);
		return views;
	}

	public View addView(View view) {
		return viewRepository.save(view);
	}

	public List<ViewDisplay> addDefaultViewDisplay(List<DefaultView> layout) {
		List<ViewDisplay> saved = new ArrayList<>();
		for (ViewDisplay vd : layout) {
			saved.add(viewDisplayRepository.save(vd));
		}
		if(saved.size() != layout.size()) {
			return null;
		}
		return saved;
	}
	
	public View getDefaultViewByCV(Long cvId) {
		return viewRepository.findByCvId(cvId);
	}
	
	public List<DefaultView> getDefaultViewDisplayByViewId(Long viewId) {
		return viewDisplayRepository.findByViewId(viewId);
	}
	
	

}
