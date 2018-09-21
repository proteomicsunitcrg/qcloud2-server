package eu.qcloud.guideset;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.param.Param;

public class GuideSetContextSourceStatus {
	
	private Param param;
	
	private ContextSource contextSource;
	
	private ContextSourceStatus status;
	
	private Long count;
	
	public GuideSetContextSourceStatus(Param param, ContextSource contextSource, ContextSourceStatus status, Long count) {
		this.param = param;
		this.contextSource = contextSource;
		this.status = status;
		this.count = count;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public ContextSource getContextSource() {
		return contextSource;
	}

	public void setContextSource(ContextSource contextSource) {
		this.contextSource = contextSource;
	}

	public ContextSourceStatus getStatus() {
		return status;
	}

	public void setStatus(ContextSourceStatus status) {
		this.status = status;
	}
	
}
