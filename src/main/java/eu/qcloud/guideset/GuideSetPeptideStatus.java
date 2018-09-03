package eu.qcloud.guideset;

import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.param.Param;

public class GuideSetPeptideStatus {
	
	private Param param;
	
	private Peptide peptide;
	
	private PeptideStatus status;
	
	private Long count;
	
	public GuideSetPeptideStatus(Param param, Peptide peptide, PeptideStatus status, Long count) {
		this.param = param;
		this.peptide = peptide;
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

	public Peptide getPeptide() {
		return peptide;
	}

	public void setPeptide(Peptide peptide) {
		this.peptide = peptide;
	}

	public PeptideStatus getStatus() {
		return status;
	}

	public void setStatus(PeptideStatus status) {
		this.status = status;
	}
	
}
