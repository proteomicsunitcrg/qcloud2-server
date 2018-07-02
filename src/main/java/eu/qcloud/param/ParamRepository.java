package eu.qcloud.param;

import org.springframework.data.repository.CrudRepository;

public interface ParamRepository extends CrudRepository<Param, Long> {
	
	public Param findByQccv(String qCCV);

}
