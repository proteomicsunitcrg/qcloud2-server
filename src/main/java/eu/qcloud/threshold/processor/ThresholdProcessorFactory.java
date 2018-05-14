package eu.qcloud.threshold.processor;

import eu.qcloud.threshold.ThresholdType;

public class ThresholdProcessorFactory {
	
	public static ThresholdProcessor getProcessor(ThresholdType thresholdType) {
		switch(thresholdType) {
		case SIGMA:
			return new AreaProcessor();
		case HARDLIMIT:
			return new NoProcessor();			
		default:
			System.out.println("erro");
			break;
		}
		return null;
	}
	
}
