package eu.qcloud.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ViewDisplayId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 342321L;

	@Column(nullable = false, updatable = false)
	private Long chartId;
	@Column(nullable = false, updatable = false)
	private Long viewId;

	public ViewDisplayId() {
	}

	public Long getChartId() {
		return chartId;
	}

	public void setChartId(Long chartId) {
		this.chartId = chartId;
	}

	public Long getViewId() {
		return viewId;
	}

	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chartId == null) ? 0 : chartId.hashCode());
		result = prime * result + ((viewId == null) ? 0 : viewId.hashCode());
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
		ViewDisplayId other = (ViewDisplayId) obj;
		if (chartId == null) {
			if (other.chartId != null)
				return false;
		} else if (!chartId.equals(other.chartId))
			return false;
		if (viewId == null) {
			if (other.viewId != null)
				return false;
		} else if (!viewId.equals(other.viewId))
			return false;
		return true;
	}

}
