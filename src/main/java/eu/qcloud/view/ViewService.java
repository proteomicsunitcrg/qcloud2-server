package eu.qcloud.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.view.ViewDisplayRepository.WithOutViewDisplay;

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
	@Deprecated
	public View getDefaultViewByCV(Long cvId) {
		//return viewRepository.findByCvId(cvId);
		return null;
	}
	
	public List<View> getDefaultViewsByCV(String cvId) {
		return viewRepository.findByCvCVId(cvId);
	}
	
	
	
	public List<WithOutViewDisplay> getDefaultViewDisplayByViewId(Long viewId) {
		return viewDisplayRepository.findByViewId(viewId);
	}
	
	public List<View> findAllDefaultViews() {
		List<View> defaultViews = new ArrayList<>();
		viewRepository.findByIsDefaultTrue().forEach(defaultViews::add);
		return defaultViews;
	}
	/**
	 * Delete the view_display rows by view id.
	 * 
	 * @return
	 */
	@Transactional
	public boolean deleteLayoutByViewId(Long viewId) {
		int actual = viewDisplayRepository.countByViewId(viewId);
		viewDisplayRepository.deleteByViewId(viewId);
		int afterDelete = viewDisplayRepository.countByViewId(viewId);
		
		return actual!=afterDelete;
		
	}

	public View getDefaultViewByCVIdAndSampleTypeCategoryId(Long cvId, Long sampleTypeCategoryId) {
		return viewRepository.findByCvIdAndSampleTypeCategoryId(cvId, sampleTypeCategoryId);
	}
	
	public View getDefaultViewByCVIdAndSampleTypeCategoryApiKey(Long cvId, UUID sampleTypeCategoryApiKey) {
		return viewRepository.findByCvIdAndSampleTypeCategoryApiKey(cvId, sampleTypeCategoryApiKey);
	}
	
	

}
