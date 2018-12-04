package eu.qcloud.troubleshooting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.troubleshooting.action.ActionRepository;
import eu.qcloud.troubleshooting.cause.CauseRepository;
import eu.qcloud.troubleshooting.problem.ProblemRepository;
import eu.qcloud.troubleshooting.test.TestRepository;

@Service
public class TroubleshootingService {

	@Autowired
	private ProblemRepository problemRepository;
	
	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private CauseRepository reasonRepository;

	@Autowired
	private TroubleshootingRepo troubleshootingRepository;

	private List<Troubleshooting> getAllProblems() {
		List<Troubleshooting> problems = new ArrayList<>();
		problemRepository.findAll().forEach(problems::add);
		return problems;
	}
	private List<Troubleshooting> getAllActions() { 
		List<Troubleshooting> actions = new ArrayList<>();
		actionRepository.findAll().forEach(actions::add);
		return actions;
	}
	
	private List<Troubleshooting> getAllTests() { 
		List<Troubleshooting> tests = new ArrayList<>();
		testRepository.findAll().forEach(tests::add);
		return tests;
	}
	
	private List<Troubleshooting> getAllCauses() { 
		List<Troubleshooting> reasons = new ArrayList<>();
		reasonRepository.findAll().forEach(reasons::add);
		return reasons;
	}
	
	public Troubleshooting addTroubleshootingItem(Troubleshooting troubleshooting,
			TroubleshootingType troubleshootingType) {
		Optional<Troubleshooting> ts = troubleshootingRepository.findByQccv(troubleshooting.getQccv());
		if(ts.isPresent()) {
			throw new DataIntegrityViolationException("An item with that QCCV already exists");
		}
		
		return troubleshootingRepository
				.save(TroubleshootingUtils.createTroubleshootingByType(troubleshooting, troubleshootingType));
	}

	public List<Troubleshooting> getAllTroubleshotingByType(TroubleshootingType troubleshootingType) {
		switch (troubleshootingType) {
		case PROBLEM:
			return getAllProblems();
		case ACTION:
			return getAllActions();
		case CAUSE:
			return getAllCauses();
		case TEST:
			return getAllTests();
		default:
			throw new InvalidActionException("Invalid troubleshooting type");
		}
	}

}
