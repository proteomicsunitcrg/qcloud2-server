package eu.qcloud.param;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ParamRepository extends CrudRepository<Param, Long> {
	
	public Param findByQccv(String qCCV);
	
	public List<Param> findByIsFor(String isFor);

}
