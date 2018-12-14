package eu.qcloud.troubleshooting.action;

import java.util.Optional;

import eu.qcloud.troubleshooting.TroubleshootingRepository;

public interface ActionRepository extends TroubleshootingRepository<Action> {

	Optional<Action> findByQccv(String qccv);

}
