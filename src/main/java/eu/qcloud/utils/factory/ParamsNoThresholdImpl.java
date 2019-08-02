package eu.qcloud.utils.factory;

import eu.qcloud.contextSource.ContextSource;

/**
 * Please check ThresholdForPlotImpl description
 * 
 * @author dmancera
 *
 */
public class ParamsNoThresholdImpl {

	private Float stepValue;

	private Float initialValue;

	private ContextSource contextSource;

	private Boolean isEnabled;

	public Float getStepValue() {
		return stepValue;
	}

	public void setStepValue(Float stepValue) {
		this.stepValue = stepValue;
	}

	public Float getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(Float initialValue) {
		this.initialValue = initialValue;
	}

	public ContextSource getContextSource() {
		return contextSource;
	}

	public void setContextSource(ContextSource contextSource) {
		this.contextSource = contextSource;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public ParamsNoThresholdImpl(Float stepValue, Float initialValue, ContextSource contextSource, Boolean isEnabled) {
		this.stepValue = stepValue;
		this.initialValue = initialValue;
		this.contextSource = contextSource;
		this.isEnabled = isEnabled;
	}

}
