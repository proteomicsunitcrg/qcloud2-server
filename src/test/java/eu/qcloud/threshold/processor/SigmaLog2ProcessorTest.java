package eu.qcloud.threshold.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.contextSource.ContextSourceRepository;
import eu.qcloud.data.Data;
import eu.qcloud.data.DataRepository;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.ThresholdRepo;
import eu.qcloud.threshold.params.ThresholdParams;
import eu.qcloud.utils.ThresholdUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unittest")
public class SigmaLog2ProcessorTest {

	@Autowired
	private ThresholdUtils thresholdUtils;

	@Autowired
	private ThresholdRepo thresholdRepository;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private ContextSourceRepository contextSourceRepository;

	@Autowired
	private DataRepository dataRepository;

	@Test
	public void processorTest() {
		Optional<Threshold> threshold = thresholdRepository
				.findByApiKey(UUID.fromString("9d5e9d79-6e9d-4846-9d24-9d2a9d1b219d"));
		Optional<File> file = fileRepository.findById(54L);
		Optional<ContextSource> cs = contextSourceRepository.findById(1L);

		GuideSet gs = thresholdUtils.generateGuideSetFromWithFile(file.get(), threshold.get().getParam(), cs.get());

		ThresholdProcessor sigmaLog2Processor = new SigmaLog2Processor();
		sigmaLog2Processor.setGuideSet(gs);
		ThresholdParams p = new ThresholdParams(0f, 0f, cs.get(), threshold.get());
		List<Data> dataForProcessor = dataRepository.findParamData(p.getContextSource().getId(),
				threshold.get().getParam().getId(), gs.getStartDate(), gs.getEndDate(),
				threshold.get().getLabSystem().getId(), threshold.get().getSampleType().getId());

		sigmaLog2Processor.setGuideSetData(dataForProcessor);
		sigmaLog2Processor.process(p);
		
		assertThat(p.getInitialValue())
			.isCloseTo(29.7394f, offset(0.0001f));
		assertThat(p.getStepValue())
			.isCloseTo(0.27040f, offset(0.0001f));
	}

}
