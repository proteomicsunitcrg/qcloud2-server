package eu.qcloud.view;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.Instrument.Instrument;
import eu.qcloud.security.model.User;

@Repository
public interface ViewRepository extends CrudRepository<View, Long> {

	// List<View> findByCvId(Long cvId);

	@Query("SELECT v from View v where v.cv.qccv=?1 and v.isDefault=true")
	List<View> findByCvCVId(String cvId);

	List<View> findByIsDefaultTrue();

	View findByCvIdAndSampleTypeCategoryId(Long cvId, Long sampleTypeCategoryId);

	View findByCvIdAndSampleTypeCategoryApiKey(Long cvId, UUID sampleTypeCategoryApiKey);

	Optional <View> findByApiKeyAndIsDefaultAndUser(UUID apiKey, boolean isDefault, User u);

	Optional <List<View>> findByIsDefaultAndUserAndIsShared(boolean isDefault, User u, boolean isShared);


	interface ViewWithoutDisplay {
		Long getId();

		String getName();

		User getUser();

		Instrument getCv();

		UUID getApiKey();
	}

	interface OnlyViewApiKey {
		String getName();

		UUID getApiKey();
	}

	interface UserViewWithoutUser {
		String getName();

		UUID getApiKey();

		boolean isIsShared();
	}

	@Query("SELECT v from View v where v.apiKey = ?1")
	UserViewWithoutUser getByApiKey(UUID viewApiKey);

	Optional<View> findOptionalByApiKey(UUID apiKey);

	List<UserViewWithoutUser> findByUserIdAndIsDefaultFalse(Long userId);

}
