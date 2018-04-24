package eu.qcloud.labsystem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Service for system
 * @author dmancera
 *
 */
@Service
public class LabSystemService {
	
	@Autowired
	private LabSystemRepository systemRepository;
	

	
	public LabSystem saveSystem(LabSystem system) {
		return systemRepository.save(system);
	}



	public List<LabSystem> findAllByNode(Long nodeId) {
		return systemRepository.findAllByNode(nodeId);

	}

	public Optional<LabSystem> findSystemBySystemId(Long systemId) {
		return systemRepository.findById(systemId);
		
	}

}
