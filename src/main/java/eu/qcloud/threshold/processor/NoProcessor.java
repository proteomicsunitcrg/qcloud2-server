package eu.qcloud.threshold.processor;

import eu.qcloud.threshold.params.ThresholdParams;

public class NoProcessor extends ThresholdProcessor{

	@Override
	public void process(ThresholdParams thresholdParam) {
		thresholdParam.setInitialValue(thresholdParam.getInitialValue());
		thresholdParam.setStepValue(thresholdParam.getStepValue());
	}

	@Override
	public boolean isGuideSetRequired() {
		return false;
	}

}
