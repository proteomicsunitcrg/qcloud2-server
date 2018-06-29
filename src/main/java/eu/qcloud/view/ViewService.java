package eu.qcloud.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import eu.qcloud.chart.Chart;
import eu.qcloud.chart.ChartRepository;
import eu.qcloud.sampleTypeCategory.SampleTypeCategory;
import eu.qcloud.sampleTypeCategory.SampleTypeCategoryRepository;
import eu.qcloud.view.ViewDisplayRepository.WithOutViewDisplay;

@Service
public class ViewService {

	@Autowired
	private ViewRepository viewRepository;

	@Autowired
	private ViewDisplayRepository viewDisplayRepository;
	
	@Autowired
	private SampleTypeCategoryRepository sampleTypeCategoryRepository;
	
	@Autowired
	private ChartRepository chartRepository;
	
	public List<View> getAllViews() {
		List<View> views = new ArrayList<>();
		viewRepository.findAll().forEach(views::add);
		return views;
	}

	public View addView(View view) {
		Optional<SampleTypeCategory> stc = sampleTypeCategoryRepository.findByApiKey(view.getSampleTypeCategory().getApiKey());
		if(!stc.isPresent()) {
			throw new DataIntegrityViolationException("Sample type category not exists");
		}
		view.setSampleTypeCategory(stc.get());		
		return viewRepository.save(view);
	}

	public List<ViewDisplay> addDefaultViewDisplay(List<DefaultView> layout) {
		List<ViewDisplay> saved = new ArrayList<>();
		for (ViewDisplay vd : layout) {
			Optional<Chart> chart = chartRepository.findByApiKey(vd.getChart().getApiKey());
			if(!chart.isPresent()) {
				throw new DataIntegrityViolationException("Chart does not exists");
			}
			vd.setChart(chart.get());
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
