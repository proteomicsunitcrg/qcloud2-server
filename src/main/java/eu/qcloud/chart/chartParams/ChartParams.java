package eu.qcloud.chart.chartParams;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.qcloud.chart.Chart;
import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.param.Param;

@Entity
@Table(name="chart_params")
public class ChartParams {
	
	
	@ManyToOne
	@JoinColumn(name="chartId",insertable=false, updatable= false)
	private Chart chart;
	
	@ManyToOne
	@JoinColumn(name="paramId",insertable=false, updatable= false)
	private Param param;
	@ManyToOne
	@JoinColumn(name="contextSourceId",insertable=false, updatable= false)
	private ContextSource contextSource;
	
	@EmbeddedId
	private ChartParamsId chartParamsId;
		
	public ChartParams(Chart chart, Param param, ContextSource contextSource) {		
		this.chart = chart;
		this.param = param;
		this.contextSource = contextSource;
	}
	
	public ChartParams() {}
	
	public void setChartParamsId(ChartParamsId chartParamsId) {
		this.chartParamsId = chartParamsId;
	}
	public Chart getChart() {
		return chart;
	}
	public void setChart(Chart chart) {
		this.chart = chart;
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

	public ChartParamsId getChartParamsId() {
		return chartParamsId;
	}

	
	
	
	
}
