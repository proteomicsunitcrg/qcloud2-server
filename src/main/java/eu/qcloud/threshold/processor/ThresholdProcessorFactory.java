package eu.qcloud.threshold.processor;

import eu.qcloud.threshold.ThresholdType;

public class ThresholdProcessorFactory {
	
	public static ThresholdProcessor getProcessor(ThresholdType thresholdType) {
		switch(thresholdType) {
		case SIGMALOG2:
			return new SigmaLog2Processor();
		case HARDLIMIT:
			return new NoProcessor();
		case SIGMA:
			return new SigmaProcessor();
		default:
			System.out.println("erro");
			break;
		}
		return null;
	}
	
}
