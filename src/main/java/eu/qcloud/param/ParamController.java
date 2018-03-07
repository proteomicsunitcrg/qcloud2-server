package eu.qcloud.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.contextSource.ContextSource;

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
	@RequestMapping(value="/api/param/types", method = RequestMethod.GET)
	public List<String> getPossibleContextSourceTypes() {
		Reflections reflections = new Reflections("eu.qcloud.contextSource");
    	Set<Class<? extends ContextSource>> subTypes = reflections.getSubTypesOf(ContextSource.class);
    	String classesName = subTypes.toString();
    	classesName = classesName.replaceAll("\\[", "");
    	classesName = classesName.replaceAll("\\]", "");
    	String[] classes = classesName.split(",");
    	List<String> types = new ArrayList<>();
    	for(int i = 0 ; i < classes.length; i++) {
    		String[] parts = classes[i].split(Pattern.quote("."));    		
    		types.add(parts[parts.length-1]);
    	}
    	return types;
    	
	}

}
