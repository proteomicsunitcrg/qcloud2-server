package eu.qcloud.threshold.labsystemstatus;

import java.util.UUID;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.param.Param;
import eu.qcloud.threshold.InstrumentStatus;

public class LabSystemStatus {

	private Param param;
	private ContextSource contextSource;
	private InstrumentStatus status;
	// private Long thresholdId;
	private UUID thresholdApiKey;
	private String sampleTypeQccv;

	public LabSystemStatus() {
	}

	public LabSystemStatus(Param param, ContextSource contextSource, InstrumentStatus status, UUID thresholdApiKey, String sampleTypeQccv) {
		this.param = param;
		this.contextSource = contextSource;
		this.status = status;
		this.thresholdApiKey = thresholdApiKey;
		this.sampleTypeQccv = sampleTypeQccv;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public ContextSource getContextSource() {
		return contextSource;
	}

	public void setContextSource(ContextSource contextSource) {
		this.contextSource = contextSource;
	}

	public InstrumentStatus getStatus() {
		return status;
	}

	public void setStatus(InstrumentStatus status) {
		this.status = status;
	}

	public UUID getThresholdApiKey() {
		return thresholdApiKey;
	}

	public void setThresholdApiKey(UUID thresholdApiKey) {
		this.thresholdApiKey = thresholdApiKey;
	}

	public String getSampleTypeQccv() {
		return sampleTypeQccv;
	}

	public void setSampleTypeQccv(String sampleTypeQccv) {
		this.sampleTypeQccv = sampleTypeQccv;
	}
	

}
