package eu.qcloud.file;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.dataSource.DataSource;
import eu.qcloud.file.FileRepository.OnlySmalls;
import eu.qcloud.sampleType.SampleType;

@Service
public class FileService {
	
	@Autowired
	private FileRepository fileRepository;
	
	
	public File addNewFile(File file) {
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
	
	public File addSpecial(File file, Long dataSourceId, Long sampleTypeId) {
		SampleType s = new SampleType();
		s.setId(sampleTypeId);
		DataSource d = new DataSource();
		d.setId(dataSourceId);
		file.setDataSource(d);
		file.setSampleType(s);
		return fileRepository.save(file);
	}

	public File getFileByChecksum(String checksum) {
		return fileRepository.findByChecksum(checksum);
		
	}

	public List<OnlySmalls> getLastFilesByDataSourceIdAndSampleTypeId(Long dataSourceId, Long sampleTypeId) {
		List<OnlySmalls> initials = new ArrayList<>();
		fileRepository.findTop10ByDataSourceIdAndSampleTypeIdOrderByCreationDateDesc(dataSourceId, sampleTypeId)
			.forEach(initials:: add);
		List<OnlySmalls> initialAndFinalDate = new ArrayList<>();
		initialAndFinalDate.add(initials.get(initials.size()-1));
		initialAndFinalDate.add(initials.get(0));
		return initialAndFinalDate;
	}
}
