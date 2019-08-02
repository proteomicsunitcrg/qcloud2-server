package eu.qcloud.contextSource;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContextSourceRepository extends CrudRepository<ContextSource, Long> {

	Optional<ContextSource> findByApiKey(UUID contextSourceApiKey);

}
