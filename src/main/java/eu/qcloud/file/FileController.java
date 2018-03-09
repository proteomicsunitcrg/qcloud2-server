package eu.qcloud.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.file.FileRepository.OnlySmalls;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value="/api/file", method = RequestMethod.POST)
	public File addFile(@RequestBody File file) {
		return fileService.addNewFile(file);
	}
	@RequestMapping(value="/api/file", method = RequestMethod.GET)
	public List<File> getAllFiles() {
		return fileService.getAllFiles();
	}
	@RequestMapping(value="/api/file/{fileId}", method = RequestMethod.GET)
	public OnlySmalls getFileById(@PathVariable Long fileId) {
		return fileService.getFileById(fileId);
	}
	@RequestMapping(value="/api/file/{sampleTypeId}/{dataSourceId}", method = RequestMethod.POST)
	public File addFileSpecial(@RequestBody File file,@PathVariable Long sampleTypeId,@PathVariable Long dataSourceId) {
		return fileService.addSpecial(file, dataSourceId, sampleTypeId);
				
	}
}
