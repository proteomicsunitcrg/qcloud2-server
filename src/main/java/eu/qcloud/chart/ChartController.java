package eu.qcloud.chart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.chart.ChartRepository.ChartDescription;

@RestController
public class ChartController {
	
	@Autowired
	private ChartService chartService;
	
	
	@RequestMapping(value="/api/chart", method = RequestMethod.POST)
	public Chart addNewChart(@RequestBody Chart chart) {
		return chartService.addNewChart(chart);
	}
	@RequestMapping(value="/api/chart", method = RequestMethod.GET)
	public List<Chart> allCharts() {
		return chartService.getAllCharts();
	}
	/*
	@RequestMapping(value="/chart/{chartId}", method = RequestMethod.POST)
	public ChartParams addParamToChart(@RequestBody ChartParams chartParams,@PathVariable Long chartId) {
		Chart c = new Chart();
		c.setId(chartId);
		chartParams.setChart(c);
		ChartParamsId chartParamsId = new ChartParamsId();
		chartParamsId.setChartId(chartId);
		chartParamsId.setParamId(chartParams.getParam().getId());
		chartParamsId.setQuantificationSourceId(chartParams.getQuantificationSource().getId());
		chartParams.setChartParamsId(chartParamsId);
		return chartService.addParamsToChart(chartParams);		
	}
	
	@RequestMapping(value="/chart/params/{chartId}")
	public List<FullParams> getChartParamsByChartId(@PathVariable Long chartId) {
		return chartService.getChartParamsByChartId(chartId);
	}
	*/
	
	@RequestMapping(value="/api/chart/{chartId}", method = RequestMethod.GET)
	public List<ChartDescription> getChartByChartId(@PathVariable Long chartId) {
		return chartService.getChartById(chartId);
	}
	
}
