package eu.qcloud.utils;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.labsystem.GuideSet;
import eu.qcloud.threshold.ThresholdType;

@Service
public class ThresholdUtils {
	
	@Autowired
	private FileRepository fileRepository;
	
	/**
	 * Use this function when a labsystem has not
	 * defined a guide set yet.
	 * It will return a guideset with an interval of
	 * two weeks since the current day
	 * @return GuideSet a valid guideset
	 */
	public GuideSet getTwoWeeksGuideSet(Long labSystemId) {
		// get the last date
		File f = fileRepository.findTop1ByLabSystemIdOrderByCreationDateDesc(labSystemId);
		if(f==null) {
			return null;
		}
		int noOfDays = -14; //i.e two weeks
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(f.getCreationDate());
		calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
		Date startDate = calendar.getTime();
		
		
		return new GuideSet(startDate,f.getCreationDate());
		
	}
	
	public Float processValueWithThresholdProcessor(float value, ThresholdType thresholdType) {
		switch(thresholdType) {
		case HARDLIMIT:
			return value;
		case SIGMA:
			return (value>0)? log2(value): value;
			
		default:
			return null;
		}
	}
	
	private static final float log2(float f){
	    return (float) (Math.log(f)/Math.log(2.0));
	}
}
