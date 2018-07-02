package eu.qcloud.param;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ParamService {

	@Autowired
	private ParamRepository paramRepository;
	
	public List<Param> getAllParams() {
		List<Param> params = new ArrayList<>();
		paramRepository.findAll().forEach(params::add);		
		return params;
	}

	public Param addNewParam(Param param) {
		return paramRepository.save(param);
	}

	public Param updateParam(Param param) {
		Param p = paramRepository.findByQccv(param.getqCCV());
		p.setName(param.getName());
		return paramRepository.save(p);
	}
	
	public Param findByQCCV(String qCCV) {
		return paramRepository.findByQccv(qCCV);
	}

}
