package eu.qcloud.chart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.chart.ChartRepository.ChartDescription;

@Service
public class ChartService {
	
	@Autowired
	private ChartRepository chartRepository;
	/*
	@Autowired
	private ChartParamsRepository chartParamsRepository;
	*/
	public Chart addNewChart(Chart chart) {
		return chartRepository.save(chart);		
	}
	
	public List<Chart> getAllCharts() {
		List<Chart> charts = new ArrayList<>();
		chartRepository.findAll().forEach(charts::add);
		return charts;
	}
	/*
	
	public ChartParams addParamsToChart(ChartParams chartParams) {
		ChartParamsId id = new ChartParamsId();
		id.setChartId(chartParams.getChart().getId());
		id.setParamId(chartParams.getParam().getId());
		id.setQuantificationSourceId(chartParams.getQuantificationSource().getId());
		chartParams.setChartParamsId(id);
		chartParamsRepository.save(chartParams);
		return chartParams;
	}
	
	public List<FullParams> getChartParamsByChartId(Long chartId) {
		return chartParamsRepository.findByChartParamsIdChartId(chartId);		
	}
	*/
	public List<ChartDescription> getChartById(Long chartId) {
		return chartRepository.findById(chartId);
		
	}
	
}
