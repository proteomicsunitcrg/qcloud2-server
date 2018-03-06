package eu.qcloud.param;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.dataSource.DataSource;
import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.security.model.User;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class ParamController {
	
	@Autowired
	private ParamService paramService;
	
	@RequestMapping(value="/api/param", method = RequestMethod.GET)
	public List<Param> getAllParams() {
		return paramService.getAllParams();
	}
	@RequestMapping(value="/api/param", method = RequestMethod.POST)
	public Param addNewParam(@RequestBody Param param) {
		return paramService.addNewParam(param);
	}
	
	@RequestMapping(value="/api/param",method= RequestMethod.PUT)
	public Param updateParam(@RequestBody Param param) {
		return paramService.updateParam(param);
	}

}
