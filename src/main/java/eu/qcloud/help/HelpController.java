
package eu.qcloud.help;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * MessageController Main controller for message related operations
 * 
 * @author Marc Serret <marc.serret@crg.eu>
 */
@RestController
public class HelpController {

	@Autowired
	HelpService helpService;

	@RequestMapping(value = "/api/help/{filename}", method = RequestMethod.GET)
	public Resource getFileFromFileSystem(@PathVariable String filename, HttpServletResponse response) {
		return helpService.getClassPathFile(filename, response);
	}
}
