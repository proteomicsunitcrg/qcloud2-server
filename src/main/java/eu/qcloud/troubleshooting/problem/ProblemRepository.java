package eu.qcloud.troubleshooting.problem;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface ProblemRepository extends CrudRepository<Problem, Long> {

	Optional<Problem> findByQccv(String qccv);

	Problem findOneByApiKey(UUID actionApiKey);

	Problem[] findAllByQccv(String qccv);

}
