package eu.qcloud.category;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	Category findByName(String name);

	Category findTop1ByIsMainDataSourceTrue();

	Optional<Category> findOptionalByApiKey(UUID categoryApiKey);
}
