package eu.qcloud.chart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import eu.qcloud.chart.chartParams.ChartParams;
import eu.qcloud.chart.chartParams.ChartParamsRepository.FullParams;
import eu.qcloud.exceptions.InvalidActionException;
/**
 * Controller for charts.
 * It will handle also the chart params. 
 * @author dmancera
 *
 */
@RestController
public class ChartController {
	
	@Autowired
	private ChartService chartService;
	
	
	/**
	 * Add a new chart into the database
	 * @param chart the chart to add
	 * @return the saved chart
	 */
	@RequestMapping(value="/api/chart", method = RequestMethod.POST)
	public Chart addNewChart(@RequestBody Chart chart) {
		return chartService.addNewChart(chart);
	}
	
	/**
	 * Update a chart
	 * @param chart
	 * @return the updated chart
	 */
	@RequestMapping(value="/api/chart", method = RequestMethod.PUT)
	public Chart updateChart(@RequestBody Chart chart) {
		return chartService.addNewChart(chart);
	}
	/**
	 * Retrieves all the charts
	 * @return a List with the charts found
	 */
	@RequestMapping(value="/api/chart", method = RequestMethod.GET)
	public List<Chart> allCharts() {
		List<Chart> charts =chartService.getAllCharts();
		return charts;
	}
	/**
	 * Return a list of charts by controlled vocabulary id
	 * @param cvId the cv id
	 * @return a list with the results
	 */
	@RequestMapping(value="/api/chart/cv/{cvId}", method = RequestMethod.GET)
	public List<Chart> getChartByCVId(@PathVariable Long cvId) {
		return chartService.getChartsByCVId(cvId);
	}
	
	/**
	 * Update the chart params of a chart by chart id
	 * @param chartParams a list with the params to add to the chart
	 * @param chartId the id of the chart whose params would be updated
	 * @return a list with the new chart params
	 */
	@RequestMapping(value="/api/chart/{chartId}", method = RequestMethod.PUT)
	public List<ChartParams> updateChartParamsByChart(@RequestBody List<ChartParams> chartParams,@PathVariable Long chartId) {
		// Delete previous chartparams
		List<ChartParams> previous = chartService.getChartParamsByChart(chartId);
		chartService.deleteChartParams(previous);
		// Add new ones
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
	
	/**
	 * Add a list of chart params to a chart
	 * @param chartParams the list of chart params to add
	 * @param chartId the chart id to add the params
	 * @return a list with the chart params added
	 */
	@RequestMapping(value="/api/chart/{chartId}", method = RequestMethod.POST)
	public List<ChartParams> addParamsToChart(@RequestBody List<ChartParams> chartParams,@PathVariable Long chartId) {
		
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
	
	
	/**
	 * Get a list of chart params by chart id
	 * @param chartId
	 * @return Return a list of fullParams, this is a spring jpa projection.
	 * Check the manual for more information on that topic or go to the 
	 * repository itself.
	 */
	@RequestMapping(value="/api/chart/params/{chartId}")
	public List<FullParams> getChartParamsByChartId(@PathVariable Long chartId) {
		return chartService.getChartParamsByChartId(chartId);
	}
	
	/**
	 * Get a chart by its Id
	 * @param chartId
	 * @return a chart
	 */
	@RequestMapping(value="/api/chart/{chartId}", method = RequestMethod.GET)
	public Optional<Chart> getChartByChartId(@PathVariable Long chartId) {
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
