package eu.qcloud.troubleshooting.action;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import eu.qcloud.troubleshooting.TroubleshootingRepository;

public interface ActionRepository extends CrudRepository<Action, Long> {

	Optional<Action> findByQccv(String qccv);

	Action findOneByApiKey(UUID apiKey);


}
