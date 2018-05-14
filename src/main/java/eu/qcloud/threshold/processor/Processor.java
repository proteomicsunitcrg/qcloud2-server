package eu.qcloud.threshold.processor;

import eu.qcloud.threshold.params.ThresholdParams;

public interface Processor {
	
	public void process(ThresholdParams thresholdParam);
	
	public boolean isGuideSetRequired();
}
