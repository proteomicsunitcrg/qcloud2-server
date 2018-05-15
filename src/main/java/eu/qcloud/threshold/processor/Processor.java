package eu.qcloud.threshold.processor;

import eu.qcloud.threshold.params.ThresholdParams;

public interface Processor {
	
	/**
	 * This function receives a ThresholdParams and
	 * modifies it. It will not store or save inside the processor
	 * any information.
	 * 
	 * @param thresholdParam
	 */
	public void process(ThresholdParams thresholdParam);
	
	public boolean isGuideSetRequired();
}
