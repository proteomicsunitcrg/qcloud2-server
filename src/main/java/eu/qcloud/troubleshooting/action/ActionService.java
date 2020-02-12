package eu.qcloud.troubleshooting.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.troubleshooting.action.Action;
import eu.qcloud.troubleshooting.action.ActionRepository;
import eu.qcloud.troubleshooting.cause.CauseRepository;
import eu.qcloud.troubleshooting.problem.Problem;
import eu.qcloud.troubleshooting.problem.ProblemRepository;
import eu.qcloud.troubleshooting.test.TestRepository;

@Service
public class ActionService {

	@Autowired
	private ActionRepository actionRepository;

	public List<Action> getAllActions() {
		List<Action> actions = new ArrayList<>();
		actionRepository.findAll().forEach(actions::add);
		return actions;
	}

	public Action saveAction(Action action) {
		action.setApiKey(UUID.randomUUID());
		return actionRepository.save(action);
	}

	public Action getByApiKey(UUID apiKey) {
		return actionRepository.findOneByApiKey(apiKey);
	}

	public Action updateAction(Action actionNew, UUID actionApiKey) {
		Action actionFromDB = getByApiKey(actionApiKey);
		actionFromDB.setName(actionNew.getName());
		actionFromDB.setDescription(actionNew.getDescription());
		actionFromDB.setQccv(actionNew.getQccv());
		return actionRepository.save(actionFromDB);
	}


	// public Troubleshooting addTroubleshootingItem(Troubleshooting troubleshooting,
	// 		TroubleshootingType troubleshootingType) {
	// 	Optional<Troubleshooting> ts = troubleshootingRepository.findByQccv(troubleshooting.getQccv());
	// 	if (ts.isPresent()) {
	// 		throw new DataIntegrityViolationException("An item with that QCCV already exists");
	// 	}

	// 	return troubleshootingRepository
	// 			.save(TroubleshootingUtils.createTroubleshootingByType(troubleshooting, troubleshootingType));
	// }

	// public List<Troubleshooting> getAllTroubleshotingByType(TroubleshootingType troubleshootingType) {
	// 	switch (troubleshootingType) {
	// 	case PROBLEM:
	// 		return getAllProblems();
	// 	case ACTION:
	// 		return getAllActions();
	// 	case CAUSE:
	// 		return getAllCauses();
	// 	case TEST:
	// 		return getAllTests();
	// 	default:
	// 		throw new InvalidActionException("Invalid troubleshooting type");
	// 	}
	// }

	// public Troubleshooting enableDisable(UUID apiKey) {
	// 	Optional <Troubleshooting> trouble = troubleshootingRepository.findByApiKey(apiKey);
	// 	if(!trouble.isPresent()) {
	// 		throw new DataIntegrityViolationException("Item not found");
	// 	}
	// 	trouble.get().setActive(!trouble.get().isActive());
	// 	return troubleshootingRepository.save(trouble.get());
	// }

}
