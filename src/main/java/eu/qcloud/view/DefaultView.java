package eu.qcloud.view;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.qcloud.chart.Chart;

@Entity
@Table(name = "default_view")
public class DefaultView {

	@ManyToOne
	@JoinColumn(name = "chartId", insertable = false, updatable = false)
	private Chart chart;

	@ManyToOne
	@JoinColumn(name = "viewId", insertable = false, updatable = false)
	private View view;

	private int col;

	private int row;
	
	@EmbeddedId
	private DefaultViewId defaultViewId;

	public DefaultView(Chart chart, View view, int col, int row) {
		this.chart = chart;
		this.view = view;
		this.col = col;
		this.row = row;
	}
	
	public DefaultView() {}

	public Chart getChart() {
		return chart;
	}

	public void setChart(Chart chart) {
		this.chart = chart;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public DefaultViewId getDefaultViewId() {
		return defaultViewId;
	}

	public void setDefaultViewId(DefaultViewId defaultViewId) {
		this.defaultViewId = defaultViewId;
	}
	
	
	
	
}
