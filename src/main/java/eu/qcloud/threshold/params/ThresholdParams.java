package eu.qcloud.threshold.params;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.threshold.Threshold;

@Entity
@Table(name = "threshold_params")
public class ThresholdParams {

	@EmbeddedId
	private ThresholdParamsId thresholdParamsId;

	@Column(name = "step_value")
	private Float stepValue;

	@Column(name = "initial_value", nullable = true)
	private Float initialValue;

	@ManyToOne
	@JoinColumn(name = "contextSourceId", insertable = false, updatable = false)
	private ContextSource contextSource;

	@Column(name = "is_enabled", columnDefinition = "bit default 1")
	private boolean isEnabled;

	public boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@ManyToOne()
	@JoinColumn(name = "thresholdId", insertable = false, updatable = false)
	private Threshold threshold;

	public ThresholdParams() {
	}

	public ThresholdParams(float stepValue, Float initialValue, ContextSource contextSource, Threshold threshold) {
		super();
		this.stepValue = stepValue;
		this.initialValue = initialValue;
		this.contextSource = contextSource;
		this.threshold = threshold;
	}

	public ThresholdParamsId getThresholdParamsId() {
		return thresholdParamsId;
	}

	public void setThresholdParamsId(ThresholdParamsId thresholdParamsId) {
		this.thresholdParamsId = thresholdParamsId;
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

	public Threshold getThreshold() {
		return threshold;
	}

	public void setThreshold(Threshold threshold) {
		this.threshold = threshold;
	}

	public Float getStepValue() {
		return stepValue;
	}

	public void setStepValue(Float stepValue) {
		this.stepValue = stepValue;
	}

	@Override
	public String toString() {
		return "ThresholdParams [contextSourceNAME=" + contextSource.getName() + ", initialValue=" + initialValue
				+ ", isEnabled=" + isEnabled + ", stepValue=" + stepValue + ", thresholdID=" + threshold.getId()
				+ ", thresholdParamsId=" + thresholdParamsId + "]";
	}

}
