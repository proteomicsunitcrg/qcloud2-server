package eu.qcloud.param;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ParamRepository extends CrudRepository<Param, Long> {
	
	public Param findByQccv(String qCCV);
	
	public Optional<Param> findOptionalByQccv(String qCCV);
	
	public List<Param> findByIsFor(String isFor);

}
