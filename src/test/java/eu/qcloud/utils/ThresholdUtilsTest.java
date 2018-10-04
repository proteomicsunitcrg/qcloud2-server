package eu.qcloud.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.param.Param;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unittest")
public class ThresholdUtilsTest {

	@Autowired
	private ThresholdUtils thresholdUtils;

	@Autowired
	private FileRepository fileRepository;

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void generateGuideSetFromBeforeFile_testNoFiles() {

		File file = fileRepository.findByChecksum("b1351ac0546cdb1c1cb696aa1808d103");

		Param rtParam = new Param();
		rtParam.setId(9L);
		rtParam.setIsZeroNoData(true);
		Peptide tcc = new Peptide();
		tcc.setId(8L);
		thresholdUtils.generateGuideSetFromBeforeFile(file, rtParam, tcc);
	}
	@Test
	public void generateGuideSetFromBeforeFile_testFiles() {
		File file = fileRepository.findByChecksum("b1351ac0546cdb1c1cb696aa1808d103");
		Param rtParam = new Param();
		rtParam.setId(9L);
		rtParam.setIsZeroNoData(true);
		Peptide tcc = new Peptide();
		tcc.setId(6L);
		GuideSet gs = thresholdUtils.generateGuideSetFromBeforeFile(file, rtParam, tcc);
		
		String startTestDate = "2018-08-10 07:00:00";
		String endTestDate = "2018-08-11 16:50:00";
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter.parse(startTestDate);
			endDate = formatter.parse(endTestDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(gs.getStartDate().getTime())
			.isEqualTo(startDate.getTime());
		assertThat(gs.getEndDate().getTime())
		.isEqualTo(endDate.getTime());
	}
	
	@Test
	public void generateGuideSetFromFromFile_testFiles() {
		File file = fileRepository.findByChecksum("b1351ac0546cdb1c1cb696aa1808d103");
		Param rtParam = new Param();
		rtParam.setId(9L);
		rtParam.setIsZeroNoData(true);
		Peptide tcc = new Peptide();
		tcc.setId(6L);
		GuideSet gs = thresholdUtils.generateGuideSetFromWithFile(file, rtParam, tcc);
		
		String startTestDate = "2018-08-10 09:54:00";
		String endTestDate = "2018-08-11 19:45:00";
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter.parse(startTestDate);
			endDate = formatter.parse(endTestDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(gs.getStartDate().getTime())
			.isEqualTo(startDate.getTime());
		assertThat(gs.getEndDate().getTime())
		.isEqualTo(endDate.getTime());
	}
}
