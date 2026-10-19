package eu.qcloud.file.tic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;

@Service
public class TicService {

	@Autowired
	private TicRepository ticRepository;

	@Autowired
	private FileRepository fileRepository;

	private final Log logger = LogFactory.getLog(this.getClass());

	public void addFileToTic(String checksum, Tic tic) {
		File file = fileRepository.findByChecksum(checksum);

		if (file == null) {
			throw new DataRetrievalFailureException("TIC: File not found");
		}

		tic.setFile(file);
		ticRepository.save(tic);
		logger.info("Saved TIC for file: " + checksum);

	}

	/**
	 * Returns the stored TIC for the file or null when the file (or its TIC)
	 * does not exist. Null keeps the error path out of the exception handler:
	 * the GET mapping declares produces=text/plain and a thrown
	 * DataRetrievalFailureException made the sendError(404) error dispatch
	 * come back as 406 instead of 404.
	 */
	public Tic findTicByChecksum(String checksum) {
		File file = fileRepository.findByChecksum(checksum);

		if (file == null) {
			return null;
		}

		return ticRepository.findByFile(file);
	}

}
