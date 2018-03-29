package eu.qcloud.view;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.qcloud.chart.Chart;

@Entity
@Table(name = "view_display")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ViewDisplay {
	@ManyToOne
	@JoinColumn(name="chartId",insertable=false, updatable= false, nullable = false)
	private Chart chart;
	
	@ManyToOne
	@JoinColumn(name = "viewId", insertable = false, updatable = false)
	private View view;
	
	@EmbeddedId
	private ViewDisplayId viewDisplayId;
	
	private int col;
	
	private int row;
	
	public ViewDisplay() {}
	
	public ViewDisplay(Chart chart, View view, int col, int row) {
		super();
		this.chart = chart;
		this.view = view;
		this.col = col;
		this.row = row;
	}

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

	public ViewDisplayId getViewDisplayId() {
		return viewDisplayId;
	}

	public void setViewDisplayId(ViewDisplayId viewDisplayId) {
		this.viewDisplayId = viewDisplayId;
	}
	
	
}
