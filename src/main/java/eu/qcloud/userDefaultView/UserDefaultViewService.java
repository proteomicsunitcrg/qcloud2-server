package eu.qcloud.userDefaultView;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.security.model.User;
import eu.qcloud.security.repository.UserRepository;
import eu.qcloud.userDefaultView.UserDefaultViewRepository.SmallUserDefaultView;
import eu.qcloud.view.View;
import eu.qcloud.view.ViewRepository;

@Service
public class UserDefaultViewService {

	@Autowired
	private UserDefaultViewRepository userDefaultViewRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ViewRepository viewRepository;

	@Autowired
	private LabSystemRepository labSystemRepository;

	public SmallUserDefaultView getUserDefaultView(User user) {
		if (user.getUserDefaultView() == null) {
			return null;
		}
		return userDefaultViewRepository.findUserDefaultViewById(user.getUserDefaultView().getId());
	}

	public UserDefaultView saveUserDefaultView(UserDefaultView userDefaultView, User user) {
		deletePreviousDefaultViews(user);
		UserDefaultView udv = null;
		switch (userDefaultView.getViewType()) {
		case DEFAULT:
			user.setUserDefaultView(null);
			userRepository.save(user);
			break;
		case INSTRUMENT:
			Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(userDefaultView.getView().getApiKey());
			if (!labSystem.isPresent()) {
				throw new DataRetrievalFailureException("Lab system not found");
			}
			userDefaultView.setView(null);
			userDefaultView.setLabSystem(labSystem.get());
			udv = userDefaultViewRepository.save(userDefaultView);
			user.setUserDefaultView(udv);
			userRepository.save(user);
			break;
		case USER:
			Optional<View> view = viewRepository.findOptionalByApiKey(userDefaultView.getView().getApiKey());
			if (!view.isPresent()) {
				throw new DataRetrievalFailureException("View not found");
			}
			userDefaultView.setView(view.get());
			udv = userDefaultViewRepository.save(userDefaultView);
			user.setUserDefaultView(udv);
			userRepository.save(user);
			break;
		default:
			break;
		}
		return null;
	}

	private void deletePreviousDefaultViews(User user) {
		user.setUserDefaultView(null);
		userRepository.save(user);
	}

}
