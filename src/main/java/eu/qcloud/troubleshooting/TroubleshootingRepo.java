package eu.qcloud.troubleshooting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Transactional
public interface TroubleshootingRepo extends TroubleshootingRepository<Troubleshooting> {

	Optional<Troubleshooting> findByQccv(String qccv);

	Optional<Troubleshooting> findByApiKey(UUID apiKey);

	List<Troubleshooting> findAllByParentIsNull();

	List<Troubleshooting> findAllByParentIsNullAndType(TroubleshootingType type);

	List<Troubleshooting> findAllByParentIsNullAndChildsIsNullAndType(TroubleshootingType type);

}
