package eu.qcloud.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.qcloud.chart.Chart;

@Entity
@Table(name = "view_display")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ViewDisplay {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "view_display_seq")
    @SequenceGenerator(name = "view_display_seq", sequenceName = "view_display_seq", allocationSize = 1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="chartId",insertable=true, updatable= true, nullable = true)
	private Chart chart;
	
	@ManyToOne
	@JoinColumn(name = "viewId", insertable = true, updatable = true, nullable=false)
	private View view;
	
	private int col;
	
	private int row;
	
	public ViewDisplay() {}
	
	public ViewDisplay(Long id, Chart chart, View view, int col, int row) {
		super();
		this.id = id;
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
	
	
}
