package eu.qcloud.troubleshooting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

@Service
public class TroubleshootingService {

	@Autowired
	private TroubleshootingRepo troubleshootingRepository;

	public Troubleshooting addTroubleshootingItem(Troubleshooting troubleshooting) {
		Optional<Troubleshooting> ts = troubleshootingRepository.findByQccv(troubleshooting.getQccv());
		troubleshooting.setApiKey(UUID.randomUUID());
		if (ts.isPresent()) {
			throw new DataIntegrityViolationException("An item with that QCCV already exists");
		}
		return troubleshootingRepository.save(troubleshooting);
	}

	public List<Troubleshooting> getAllTopParents() {
		return troubleshootingRepository.findAllByParentIsNull();
	}

	public Troubleshooting getByApiKey(UUID apiKey) {
		Optional<Troubleshooting> tr = troubleshootingRepository.findByApiKey(apiKey);
		if (!tr.isPresent()) {
			throw new DataRetrievalFailureException("Chart not found");
		} else {
			return tr.get();
		}
	}

	public Troubleshooting unLink(UUID apiKey) {
		Troubleshooting tr = getByApiKey(apiKey);
		tr.setParent(null);
		return troubleshootingRepository.save(tr);
	}

	public List<Troubleshooting> getByParentNullChildsNullAndType(TroubleshootingType type) {
		List<Troubleshooting> trs = troubleshootingRepository.findAllByParentIsNullAndChildsIsNullAndType(type);
		return trs;
	}

	public Troubleshooting linkChild(UUID parentApiKey, Troubleshooting child) {
		Troubleshooting parent = getByApiKey(parentApiKey);
		child.setParent(parent);
		return troubleshootingRepository.save(child);
	}

	public List<Troubleshooting> getTroubleshootingParentByType(TroubleshootingType type) {
		return troubleshootingRepository.findAllByParentIsNullAndType(type);
	}

	public Troubleshooting updateTroubleshooting(Troubleshooting troubleshooting) {
		return troubleshootingRepository.save(troubleshooting);
	}

}
