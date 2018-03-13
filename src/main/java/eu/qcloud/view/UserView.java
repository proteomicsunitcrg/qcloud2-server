package eu.qcloud.view;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.qcloud.chart.Chart;
import eu.qcloud.dataSource.DataSource;
@Entity
@Table(name="user_view")
public class UserView {
	
	@ManyToOne
	@JoinColumn(name="chartId",insertable=false, updatable= false)
	private Chart chart;
	
	@ManyToOne
	@JoinColumn(name="dataSourceId",insertable=false, updatable= false)
	private DataSource dataSource;
	
	@ManyToOne
	@JoinColumn(name="viewId",insertable=false, updatable= false)
	private View view;
	
	private int col;
	
	private int row;
	
	@EmbeddedId
	private UserViewId userViewId;

	public UserView(Chart chart, DataSource dataSource, View view, int col, int row) {
		super();
		this.chart = chart;
		this.dataSource = dataSource;
		this.view = view;
		this.col = col;
		this.row = row;
	}
	
	public UserView() {}

	public Chart getChart() {
		return chart;
	}

	public void setChart(Chart chart) {
		this.chart = chart;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
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

	public UserViewId getUserViewId() {
		return userViewId;
	}

	public void setUserViewId(UserViewId userViewId) {
		this.userViewId = userViewId;
	}


	
	
	
	
	
}
