package eu.qcloud.chart.chartParams;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class ChartParamsId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 14323L;
	
	@Column(nullable = false, updatable = false)
	private Long chartId;
	@Column(nullable = false, updatable = false)
	private Long contextSourceId;
	
	public ChartParamsId() {}

	public Long getChartId() {
		return chartId;
	}

	public void setChartId(Long chartId) {
		this.chartId = chartId;
	}

	public Long getContextSourceId() {
		return contextSourceId;
	}

	public void setContextSourceId(Long contextSourceId) {
		this.contextSourceId = contextSourceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chartId == null) ? 0 : chartId.hashCode());
		result = prime * result + ((contextSourceId == null) ? 0 : contextSourceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChartParamsId other = (ChartParamsId) obj;
		if (chartId == null) {
			if (other.chartId != null)
				return false;
		} else if (!chartId.equals(other.chartId))
			return false;
		if (contextSourceId == null) {
			if (other.contextSourceId != null)
				return false;
		} else if (!contextSourceId.equals(other.contextSourceId))
			return false;
		return true;
	}

}
