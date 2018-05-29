package eu.qcloud.threshold.labsystemstatus;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.param.Param;
import eu.qcloud.threshold.InstrumentStatus;

public class LabSystemStatus {
	
	private Param param;
	private ContextSource contextSource;
	private InstrumentStatus status;
	
	public LabSystemStatus() {}
	
	public LabSystemStatus(Param param, ContextSource contextSource, InstrumentStatus status) {
		this.param = param;
		this.contextSource = contextSource;
		this.status = status;
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
	
}
