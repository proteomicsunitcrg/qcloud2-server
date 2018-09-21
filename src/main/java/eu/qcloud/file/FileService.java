package eu.qcloud.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.file.FileRepository.OnlyChecksum;
import eu.qcloud.file.FileRepository.OnlySmalls;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.guideset.automatic.AutomaticGuideSetRepository;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemService;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeService;
/**
 * File service
 * @author dmancera
 *
 */
@Service
public class FileService {
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private SampleTypeService sampleTypeService;
	
	@Autowired
	private LabSystemService labSystemService;
	
	@Autowired
	private AutomaticGuideSetRepository guideSetRepository;
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	public File addNewFile(File file) {
		
		GuideSet guideSet = guideSetRepository.findByIsActiveTrue().get(0);
		
		file.setGuideSet(guideSet);
		return fileRepository.save(file);
	}
	
	public List<File> getAllFiles() {
		List<File> files = new ArrayList<>();
		fileRepository.findAll().forEach(files::add);
		return files;
	}
	
	public OnlySmalls getFileById(Long idFile) {
		return fileRepository.findFileById(idFile);
	}
	
	public File addSpecial(File file, Long labSystemId, Long sampleTypeId) {
		SampleType s = new SampleType();
		s.setId(sampleTypeId);
		LabSystem ls = new LabSystem(); 
		ls.setId(labSystemId);
		file.setLabSystem(ls);;
		file.setSampleType(s);
		return fileRepository.save(file);
	}

	public File getFileByChecksum(String checksum) {
		return fileRepository.findByChecksum(checksum);
		
	}

	public List<OnlySmalls> getLastFilesByDataSourceIdAndSampleTypeId(Long dataSourceId, Long sampleTypeId) {
		List<OnlySmalls> initials = new ArrayList<>();
		fileRepository.findTop10ByLabSystemIdAndSampleTypeIdOrderByCreationDateDesc(dataSourceId, sampleTypeId)
			.forEach(initials:: add);
		List<OnlySmalls> initialAndFinalDate = new ArrayList<>();
		initialAndFinalDate.add(initials.get(initials.size()-1));
		initialAndFinalDate.add(initials.get(0));
		return initialAndFinalDate;
	}

	public OnlyChecksum getFileByFilename(String filename) {
		return fileRepository.getFileByFilename(filename);
		
	}

	public File addFile(String sampleTypeQCCV, File file) {
		// get the sample type
		SampleType t = sampleTypeService.getSampleTypeByQCCV(sampleTypeQCCV);
		file.setSampleType(t);
		return fileRepository.save(file);
	}

	public File addFromWorkflow(File file, String sampleTypeQCCV, UUID labSystemApiKey) {
		// Find if file already exists
		if(getFileByChecksum(file.getChecksum())!= null) {
			logger.error("ERROR: File NOT inserted with duplicated checksum: " + file.getChecksum());
			throw new DataIntegrityViolationException("A file with that checksum already exists!");
		}
		
		GuideSet guideSet = guideSetRepository.findByIsActiveTrue().get(0);
		
		file.setGuideSet(guideSet);
		
		SampleType st = sampleTypeService.getSampleTypeByQCCV(sampleTypeQCCV);
		Optional<LabSystem> ls = labSystemService.findSystemByApiKey(labSystemApiKey);
		if(ls.isPresent()) {
			file.setSampleType(st);
			file.setLabSystem(ls.get());
			GuideSet mgs = ls.get().getGuideActiveSetBySampleType(st.getId());
			if(mgs!=null) {
				file.setGuideSet(mgs);
			}
			logger.info("File inserted with checksum: " + file.getChecksum() + " for labsystem: " + file.getLabSystem().getName());
			return fileRepository.save(file);
		} else {
			throw new DataRetrievalFailureException("Lab system not found.");
		}
	}
	
	public void deleteFile(String checksum) {
		File f = fileRepository.findByChecksum(checksum);
		if(f== null) {
			throw new DataRetrievalFailureException("File not found.");
		}
		fileRepository.delete(f);
		
	}
	
	public File getLastFileBySampleTypeQCCVAndLabSystemApikey(String sampleTypeQCCV, UUID labSystemApikey) {
		return fileRepository.findTop1BySampleTypeQualityControlControlledVocabularyAndLabSystemApiKeyOrderByCreationDateDesc(sampleTypeQCCV,labSystemApikey);
	}

	public List<SampleType> findSampleTypesByLabSystemApiKey(UUID labSystemApiKey) {
		return fileRepository.findDistinctSampleTypeByLabSystemApiKey(labSystemApiKey);
	}


}
