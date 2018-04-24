package eu.qcloud.system;

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
public class SystemService {
	
	@Autowired
	private SystemRepository systemRepository;
	

	
	public System saveSystem(System system) {
		return systemRepository.save(system);
	}



	public List<System> findAllByNode(Long nodeId) {
		return systemRepository.findAllByNode(nodeId);

	}

	public Optional<System> findSystemBySystemId(Long systemId) {
		return systemRepository.findById(systemId);
		
	}

}
