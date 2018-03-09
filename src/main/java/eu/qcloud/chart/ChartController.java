package eu.qcloud.chart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.chart.ChartRepository.ChartDescription;
import eu.qcloud.chart.chartParams.ChartParams;
import eu.qcloud.chart.chartParams.ChartParamsId;
import eu.qcloud.chart.chartParams.ChartParamsRepository.FullParams;
import eu.qcloud.exceptions.InvalidActionException;

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
	
	@RequestMapping(value="/api/chart/{chartId}", method = RequestMethod.POST)
	public List<ChartParams> addParamToChart(@RequestBody List<ChartParams> chartParams,@PathVariable Long chartId) {
		
		List<ChartParams> chartParamsList = new ArrayList<>();		
		for(ChartParams chartParam: chartParams) {
			chartParamsList.add(chartService.addParamToChart(chartParam));
			
		}
		if(chartParamsList.size()!=chartParams.size()) {
			// delete previous
			chartService.deleteChartParams(chartParamsList);
			chartService.deleteChartById(chartId);
			throw new DataIntegrityViolationException("There were a problem inserting the chart. Try again later");
		}
		return chartParams;
		
		
	}

	@RequestMapping(value="/api/chart/params/{chartId}")
	public List<FullParams> getChartParamsByChartId(@PathVariable Long chartId) {
		return chartService.getChartParamsByChartId(chartId);
	}
	
	@RequestMapping(value="/api/chart/{chartId}", method = RequestMethod.GET)
	public List<ChartDescription> getChartByChartId(@PathVariable Long chartId) {
		return chartService.getChartById(chartId);
	}
	/*
	 * Exception handlers
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

	@ExceptionHandler(PersistenceException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
	}
	@ExceptionHandler(InvalidActionException.class)
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException{
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
	
}
