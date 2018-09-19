package eu.qcloud.userDefaultView;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.view.ViewRepository.OnlyViewApiKey;

@Repository
public interface UserDefaultViewRepository extends CrudRepository<UserDefaultView, Long> {
	
	@Query("select u from UserDefaultView u where u.id = ?1")
	SmallUserDefaultView findUserDefaultViewById(Long id);

	interface SmallUserDefaultView {
		ViewType getViewType();
		LabSystem getLabSystem();
		OnlyViewApiKey getView();
	}
}
