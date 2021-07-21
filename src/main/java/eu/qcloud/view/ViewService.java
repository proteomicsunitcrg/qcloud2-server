package eu.qcloud.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import eu.qcloud.chart.Chart;
import eu.qcloud.chart.ChartRepository;
import eu.qcloud.file.FileRepository;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.sampleTypeCategory.SampleTypeCategory;
import eu.qcloud.sampleTypeCategory.SampleTypeCategoryRepository;
import eu.qcloud.sampleTypeCategory.SampleTypeComplexity;
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;
import eu.qcloud.view.UserViewRepository.UserDisplayWithOutViewDisplay;
import eu.qcloud.view.ViewDisplayRepository.WithOutViewDisplay;
import eu.qcloud.view.ViewRepository.UserViewWithoutUser;

@Service
public class ViewService {

	@Autowired
	private ViewRepository viewRepository;

	@Autowired
	private ViewDisplayRepository viewDisplayRepository;

	@Autowired
	private SampleTypeCategoryRepository sampleTypeCategoryRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ChartRepository chartRepository;

	@Autowired
	private LabSystemRepository labSystemRepository;

	@Autowired
	private UserViewRepository userViewRepository;

	@Autowired
	private FileRepository fileRepo;

	public List<View> getAllViews() {
		List<View> views = new ArrayList<>();
		viewRepository.findAll().forEach(views::add);
		return views;
	}

	public View addView(View view) {
		Optional<SampleTypeCategory> stc = sampleTypeCategoryRepository
				.findByApiKey(view.getSampleTypeCategory().getApiKey());
		if (!stc.isPresent()) {
			throw new DataIntegrityViolationException("Sample type category not exists");
		}
		view.setSampleTypeCategory(stc.get());
		view.setApiKey(UUID.randomUUID());
		return viewRepository.save(view);
	}

	public List<ViewDisplay> addDefaultViewDisplay(List<DefaultView> layout) {
		List<ViewDisplay> saved = new ArrayList<>();
		for (ViewDisplay vd : layout) {
			Optional<Chart> chart = chartRepository.findByApiKey(vd.getChart().getApiKey());
			Optional<View> view = viewRepository.findOptionalByApiKey(vd.getView().getApiKey());
			if (!chart.isPresent()) {
				throw new DataIntegrityViolationException("Chart does not exists");
			}
			if (!view.isPresent()) {
				throw new DataIntegrityViolationException("View does not exists");
			}
			vd.setView(view.get());
			vd.setChart(chart.get());
			saved.add(viewDisplayRepository.save(vd));
		}
		if (saved.size() != layout.size()) {
			return null;
		}
		return saved;
	}

	public List<UserView> addUserViewDisplay(List<UserView> layout) {
		List<UserView> saved = new ArrayList<>();
		for (UserView vd : layout) {
			Optional<Chart> chart = chartRepository.findByApiKey(vd.getChart().getApiKey());
			Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(vd.getLabSystem().getApiKey());
			Optional<View> view = viewRepository.findOptionalByApiKey(vd.getView().getApiKey());
			if (!chart.isPresent()) {
				throw new DataIntegrityViolationException("Chart does not exists");
			}
			if (!labSystem.isPresent()) {
				throw new DataIntegrityViolationException("Lab system does not exists");
			}
			if (!view.isPresent()) {
				throw new DataIntegrityViolationException("View does not exists");
			}
			view.get().setName(vd.getView().getName());
			vd.setChart(chart.get());
			vd.setLabSystem(labSystem.get());
			vd.setView(view.get());
			saved.add(userViewRepository.save(vd));
		}
		if (saved.size() != layout.size()) {
			return null;
		}
		return saved;
	}

	public List<View> getDefaultViewsByCV(String cvId) {
		return viewRepository.findByCvCVId(cvId);
	}

	// Returns the tabs but not the qc3 tab if the ls doesnt has any qc3 file
	public List<View> getDefaultViewsByCVAndLsApiKey(String cvId, UUID lsApiKey) {
		List<View> allViews = viewRepository.findByCvCVId(cvId);
		for (Iterator<View> i = allViews.iterator(); i.hasNext();) {
			View view = i.next();
			if (view.getSampleTypeCategory().getSampleTypeComplexity()
					.equals(SampleTypeComplexity.HIGHWITHISOTOPOLOGUES)) {
				if (fileRepo.countByLabSystemApiKeyAndSampleTypeId(lsApiKey, 5l) == 0) {
					i.remove();
				}
			}
		}
		return allViews;
	}

	/**
	 * @deprecated do not use ids
	 * @param viewId
	 * @return
	 */
	public List<WithOutViewDisplay> getDefaultViewDisplayByViewId(Long viewId) {
		return viewDisplayRepository.findByViewId(viewId);
	}

	public List<WithOutViewDisplay> getDefaultViewDisplayByViewApiKey(UUID viewApiKey) {
		return viewDisplayRepository.findByViewApiKey(viewApiKey);
	}

	public List<View> findAllDefaultViews() {
		List<View> defaultViews = new ArrayList<>();
		viewRepository.findByIsDefaultTrue().forEach(defaultViews::add);
		return defaultViews;
	}

	/**
	 * Delete the view_display rows by view id.
	 *
	 * @deprecated now use apikye
	 * @return
	 */
	@Transactional
	public boolean deleteLayoutByViewId(Long viewId) {
		int actual = viewDisplayRepository.countByViewId(viewId);
		viewDisplayRepository.deleteByViewId(viewId);
		int afterDelete = viewDisplayRepository.countByViewId(viewId);

		return actual != afterDelete;
	}

	@Transactional
	public boolean deleteLayoutByViewApiKey(UUID viewApiKey) {
		int actual = viewDisplayRepository.countByViewApiKey(viewApiKey);
		viewDisplayRepository.deleteByViewApiKey(viewApiKey);
		int afterDelete = viewDisplayRepository.countByViewApiKey(viewApiKey);

		return actual != afterDelete;
	}

	public View getDefaultViewByCVIdAndSampleTypeCategoryId(Long cvId, Long sampleTypeCategoryId) {
		return viewRepository.findByCvIdAndSampleTypeCategoryId(cvId, sampleTypeCategoryId);
	}

	public View getDefaultViewByCVIdAndSampleTypeCategoryApiKey(Long cvId, UUID sampleTypeCategoryApiKey) {
		return viewRepository.findByCvIdAndSampleTypeCategoryApiKey(cvId, sampleTypeCategoryApiKey);
	}

	public View addUserView(View view) {
		view.setUser(getUserFromSecurityContext());
		view.setApiKey(UUID.randomUUID());
		return viewRepository.save(view);
	}

	public List<UserViewWithoutUser> findAllUserViews() {
		List<UserViewWithoutUser> userViews = new ArrayList<>();
		viewRepository.findByUserIdAndIsDefaultFalse(getUserFromSecurityContext().getId()).forEach(userViews::add);
		return userViews;

	}

	public List<UserDisplayWithOutViewDisplay> getUserViewDisplayByViewApiKey(UUID viewApiKey) {
		return userViewRepository.findByViewApiKey(viewApiKey);
	}

	public UserViewWithoutUser findUserViewByApiKey(UUID viewApiKey) {
		return viewRepository.getByApiKey(viewApiKey);
	}

	/*
	 * Helper classes
	 */
	/**
	 * Get the current user from the security context
	 *
	 * @return the logged user
	 */
	private User getUserFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUsername(authentication.getName());
		return user;
	}

	public View updateView(View view) {
		Optional<View> v = viewRepository.findOptionalByApiKey(view.getApiKey());
		if (v.isPresent()) {
			v.get().setName(view.getName());
			return viewRepository.save(v.get());
		} else {
			throw new DataIntegrityViolationException("View does not exists");
		}

	}

	@Transactional
	public View deleteView(View view) {
		Optional<View> v = viewRepository.findOptionalByApiKey(view.getApiKey());
		if (v.isPresent()) {
			getDefaultViewDisplayByViewId(v.get().getId());
			viewDisplayRepository.deleteByViewApiKey(v.get().getApiKey());
			viewRepository.delete(v.get());
			return view;
		} else {
			throw new DataIntegrityViolationException("View not found.");
		}
	}

	public View updateShare(UUID viewApiKey) {
		User u = getUserFromSecurityContext();
		Optional <View> view = viewRepository.findByApiKeyAndIsDefaultAndUser(viewApiKey, false, u);
		if (view.isPresent()) {
			view.get().setShared(!view.get().isIsShared());
			viewRepository.save(view.get());
			return view.get();
		}
		throw new DataIntegrityViolationException("View not found.");
	}

	public List<View> getSharedViews() {
		User u = getUserFromSecurityContext();
		List <View> allNodeViews = new ArrayList<>();
		for (User nodeUser : u.getNode().getUsers()) {
			if (nodeUser.getAuthorities().size() > 1 && nodeUser.getEnabled() && !nodeUser.getApiKey().equals(u.getApiKey())) { // get the node managers enabled, but not ourselves
				Optional <List<View>> viewListOpt = viewRepository.findByIsDefaultAndUserAndIsShared(false, nodeUser, true); // get the user shared views
				if (viewListOpt.isPresent()) { // if the manager have shared views we insert them to the return list
					allNodeViews.addAll(viewListOpt.get());
				}
			}
		}
		return allNodeViews;
	}

}
