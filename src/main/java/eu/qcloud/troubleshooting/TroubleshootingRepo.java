package eu.qcloud.troubleshooting;

import java.util.Optional;

import javax.transaction.Transactional;

@Transactional
public interface TroubleshootingRepo  extends TroubleshootingRepository<Troubleshooting>{

	Optional<Troubleshooting> findByQccv(String qccv);

}
