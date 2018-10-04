package eu.qcloud.data.processor.processors;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.data.Data;
import eu.qcloud.data.DataForPlot;
import eu.qcloud.data.DataRepository;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.param.Param;
import eu.qcloud.utils.ThresholdUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unittest")
public class RetentionTimeProcessorTest {
	
	@Autowired
	private ThresholdUtils thresholdUtils;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private DataRepository dataRepository;
	
	@Test
	public void retentionTimeProcess_test() {
		
		Processor processor = new RetentionTimeProcessor();
		
		File file = fileRepository.findByChecksum("0aeb6f7b3639a93575511902908a003b");
		
		Param param = new Param();
		param.setId(9L);
		param.setIsZeroNoData(true);
		
		Peptide peptide = new Peptide();
		peptide.setId(1L);
						
		GuideSet guideSet = thresholdUtils.generateGuideSetFromWithFile(file, param, peptide);
		
		ArrayList<Data> guideSetData = (ArrayList<Data>) dataRepository.findParamData(peptide.getId(), param.getId(),
				guideSet.getStartDate(), guideSet.getEndDate(), file.getLabSystem().getId(),
				file.getSampleType().getId());
		Float value = 577.378f;
		
		List<DataForPlot> dataForPlot = new ArrayList<>();
		dataForPlot.add(new DataForPlot(file.getFilename(), file.getCreationDate(),
				"LVN", value, null));
		processor.setData(dataForPlot);
		processor.setGuideSet(guideSet);
		processor.setGuideSetData(guideSetData);
		List<DataForPlot> processedValue = processor.processData();
		assertThat(processedValue.get(0).getValue())
			.isEqualTo(-3.3f);
		
		
	}

}
