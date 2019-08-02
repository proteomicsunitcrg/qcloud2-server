package eu.qcloud.troubleshooting;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TroubleshootingRepository<T extends Troubleshooting> extends CrudRepository<T, Long> {

}
