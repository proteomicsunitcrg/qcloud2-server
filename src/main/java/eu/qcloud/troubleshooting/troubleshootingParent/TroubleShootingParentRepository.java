package eu.qcloud.troubleshooting.troubleshootingParent;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import eu.qcloud.troubleshooting.TroubleshootingRepository;

public interface TroubleShootingParentRepository extends CrudRepository<TroubleshootingParent, Long> {

	TroubleshootingParent findOneByApiKey(UUID apiKey);

	// Optional<Action> findByQccv(String qccv);

}
