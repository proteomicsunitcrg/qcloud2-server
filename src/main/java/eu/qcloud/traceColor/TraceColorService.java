package eu.qcloud.traceColor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraceColorService {

	@Autowired
	private TraceColorRepository traceColorRepository;

	public List<TraceColor> getTraceColors() {
		return traceColorRepository.findAllTraceColor();
	}

	public void addNewTraceColor(TraceColor traceColor) {
		traceColor.setApiKey(UUID.randomUUID());
		traceColorRepository.save(traceColor);
	}

	public void updateTraceColor(UUID traceColorApiKey, TraceColor traceColor) {
		Optional<TraceColor> tc = traceColorRepository.findByApiKey(traceColorApiKey);
		tc.ifPresent(t -> {
			t.setMainColor(traceColor.getMainColor());
			traceColorRepository.save(t);
		});

	}

}
