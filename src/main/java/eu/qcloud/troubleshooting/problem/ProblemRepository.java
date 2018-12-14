package eu.qcloud.troubleshooting.problem;

import java.util.Optional;

import eu.qcloud.troubleshooting.TroubleshootingRepository;

public interface ProblemRepository extends TroubleshootingRepository<Problem> {
	
	Optional<Problem> findByQccv(String qccv);

}
