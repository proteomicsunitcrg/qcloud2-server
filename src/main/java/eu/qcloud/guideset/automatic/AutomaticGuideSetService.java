package eu.qcloud.guideset.automatic;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomaticGuideSetService {

	@Autowired
	private AutomaticGuideSetRepository automaticGuideSetRepository;

	public AutomaticGuideSet saveAutomaticGuideSet(AutomaticGuideSet automaticGuideSet) {
		automaticGuideSet.setApiKey(UUID.randomUUID());
		automaticGuideSet.setIsActive(true);
		List<AutomaticGuideSet> activeGuideSet = automaticGuideSetRepository.findByIsActiveTrue();
		if (activeGuideSet.size() == 0) {
			return automaticGuideSetRepository.save(automaticGuideSet);
		} else {
			for (AutomaticGuideSet ag : activeGuideSet) {
				ag.setIsActive(false);
				automaticGuideSetRepository.save(ag);
			}

			return automaticGuideSetRepository.save(automaticGuideSet);
		}

	}
}
