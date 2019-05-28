package eu.qcloud.help;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Main service for node
 * 
 * @author Daniel Mancera <daniel.mancera@crg.eu>
 */

@Service
public class HelpService {

	public Resource getClassPathFile(String filename, HttpServletResponse response) {
		return getResource(filename, response);
	}

	private Resource getResource(String filename, HttpServletResponse response) {
		response.setContentType("application/pdf; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setHeader("filename", filename);

		Resource resource = new ClassPathResource("help/" + filename);
		return resource;
	}

}
