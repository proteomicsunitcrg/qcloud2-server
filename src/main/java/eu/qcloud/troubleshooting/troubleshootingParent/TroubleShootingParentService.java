package eu.qcloud.troubleshooting.troubleshootingParent;

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
public class TroubleShootingParentService {


    @Autowired
	TroubleShootingParentRepository parentRepository;
	
	@Autowired
	ActionRepository actionRepo;
	
	@Autowired
	ProblemRepository problemRepo;

	public List<TroubleshootingParent> getAllParents() {
		List<TroubleshootingParent> parent = new ArrayList<>();
		parentRepository.findAll().forEach(parent::add);
		return parent;
	}

	public List<TroubleshootingParent> getAllParentsWithActions() {
		List<TroubleshootingParent> parents = new ArrayList<>();
		parentRepository.findAll().forEach(parent -> {
			if (parent.getAction().size()!=0) {
				parents.add(parent);
			}
		});
		return parents;
	}

	public List<TroubleshootingParent> getAllParentsWithProblems() {
		List<TroubleshootingParent> parents = new ArrayList<>();
		parentRepository.findAll().forEach(parent -> {
			if (parent.getProblem().size()!=0) {
				parents.add(parent);
			}
		});
		return parents;
	}

	public TroubleshootingParent getByApiKey(UUID apiKey) {
		return parentRepository.findOneByApiKey(apiKey);
	}

	public TroubleshootingParent unlinkAction(UUID actionApiKey, UUID parentApiKey) {
		TroubleshootingParent parent = getByApiKey(parentApiKey);
		List<Action> actions = new ArrayList<>();
		parent.getAction().stream().filter(action -> !action.getApiKey().equals(actionApiKey)).forEach(action-> actions.add(action));
		parent.setAction(actions);
		return parentRepository.save(parent);
	}
	
	public TroubleshootingParent unlinkProblem(UUID problemApiKey, UUID parentApiKey) {
		TroubleshootingParent parent = getByApiKey(parentApiKey);
		List<Problem> problems = new ArrayList<>();
		parent.getProblem().stream().filter(problem -> !problem.getApiKey().equals(problemApiKey)).forEach(problem-> problems.add(problem));
		System.out.println(problems.size());
		parent.setProblem(problems);
		return parentRepository.save(parent);
	}

	public TroubleshootingParent linkAction(UUID actionApiKey, UUID parentApiKey) {
		TroubleshootingParent parent = getByApiKey(parentApiKey);
		parent.getAction().add(actionRepo.findOneByApiKey(actionApiKey));
		return parentRepository.save(parent);
	}

	public TroubleshootingParent linkProblem(UUID problemApiKey, UUID parentApiKey) {
		TroubleshootingParent parent = getByApiKey(parentApiKey);
		parent.getProblem().add(problemRepo.findOneByApiKey(problemApiKey));
		return parentRepository.save(parent);
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
