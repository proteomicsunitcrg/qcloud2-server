package eu.qcloud.labsystem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.file.FileRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.utils.ThresholdUtils;
/**
 * Service for system
 * @author dmancera
 *
 */
@Service
public class LabSystemService {
	
	@Autowired
	private LabSystemRepository systemRepository;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ThresholdUtils thresholdUtils;
	
	public LabSystem saveSystem(LabSystem system) {
		return systemRepository.save(system);
	}

	public List<LabSystem> findAllByNode(Long nodeId) {
		List<LabSystem> labSystems = systemRepository.findAllByNode(nodeId);
		for(LabSystem ls : labSystems) {
			// Getting its active guide sets
			List<SampleType> sampleTypes = fileRepository.findDistinctSampleTypeByLabSystemId(ls.getId());
			for(SampleType st: sampleTypes) {
				GuideSet gs = ls.getGuideSet(st.getId());
				if(gs==null) {
					gs = thresholdUtils.generateAutoGuideSet(st, ls);
					// ls.getGuideSets().add(gs);
				}
				gs.setTotalFiles(fileRepository.countByLabSystemIdAndSampleTypeIdAndCreationDateBetween(ls.getId(), st.getId(), gs.getStartDate(), gs.getEndDate()));
				gs.setLabSystemTotalFiles(fileRepository.countByLabSystemIdAndSampleTypeId(ls.getId(), st.getId()));
			}
		}
		return systemRepository.findAllByNode(nodeId);
	}

	public Optional<LabSystem> findSystemBySystemId(Long systemId) {
		return systemRepository.findById(systemId);
	}
	
	public Optional<LabSystem> findSystemByApiKey(UUID apikey) {
		return systemRepository.findByApiKey(apikey);		
	}
	
	public List<LabSystem> findLabSystemByDataSourceId(Long dataSourceId) {
		return systemRepository.findAllByDataSourcesId(dataSourceId);
	}

}
