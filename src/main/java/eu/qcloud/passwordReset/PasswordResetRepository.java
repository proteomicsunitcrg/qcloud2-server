package eu.qcloud.passwordReset;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends CrudRepository<PasswordReset, Long> {
	
	Optional<PasswordReset> findOptionalByToken(UUID token);
	
	Optional<PasswordReset> findOptionalByUserId(Long userId);

}
