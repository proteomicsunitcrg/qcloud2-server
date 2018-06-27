package eu.qcloud.chart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.chart.ChartRepository.NoView;
import eu.qcloud.chart.chartParams.ChartParams;
import eu.qcloud.chart.chartParams.ChartParamsRepository.FullParams;
import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeService;
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
	
	@Autowired
	private SampleTypeService sampleTypeService;
	
	
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
		return chartService.updateChart(chart);
	}
	/**
	 * Retrieves all the charts
	 * @return a List with the charts found
	 */
	@RequestMapping(value="/api/chart", method = RequestMethod.GET)
	public List<NoView> allCharts() {
		//List<Chart> charts =chartService.getAllCharts();
		List<NoView> charts =chartService.getAllChartsWithoutView();
		return charts;
	}
	/**
	 * Return a list of charts by controlled vocabulary id
	 * @param cvId the cv id
	 * @return a list with the results
	 */
	@RequestMapping(value="/api/chart/cv/{cvId}", method = RequestMethod.GET)
	public List<NoView> getChartByCVId(@PathVariable String cvId) {
		return chartService.getChartsByCVIdWithoutView(cvId);
		//return chartService.getChartsByCVId(cvId);
	}
	/**
	 * Get a list of charts of the main sample type of a sample type category
	 * @param cvId
	 * @param sampleTypeCategoryId
	 * @return
	 */
	@RequestMapping(value="/api/chart/cv/{cvId}/category/{sampleTypeCategoryApiKey}", method = RequestMethod.GET)
	public List<NoView> getChartsByCVIdAndSampleTypeCategoryId(@PathVariable Long cvId,@PathVariable UUID sampleTypeCategoryApiKey) {
		// get the main sample type of the given category
		SampleType sampleType = sampleTypeService.getMainSampleTypeBySampleTypeCategoryApiKey(sampleTypeCategoryApiKey);
		return chartService.getChartsByCVIdAndSampleTypeCategoryId(cvId, sampleType.getId());
	}
	
	/**
	 * Update the chart params of a chart by chart id
	 * @param chartParams a list with the params to add to the chart
	 * @param chartId the id of the chart whose params would be updated
	 * @return a list with the new chart params
	 */
	@RequestMapping(value="/api/chart/{chartApiKey}", method = RequestMethod.PUT)
	public List<ChartParams> updateChartParamsByChart(@RequestBody List<ChartParams> chartParams,@PathVariable UUID chartApiKey) {
		// Delete previous chartparams
		List<ChartParams> previous = chartService.getChartParamsByChartApiKey(chartApiKey);
		chartService.deleteChartParams(previous);
		// Add new ones
		List<ChartParams> chartParamsList = new ArrayList<>();		
		for(ChartParams chartParam: chartParams) {
			chartParamsList.add(chartService.addParamToChart(chartParam));
			
		}
		if(chartParamsList.size()!=chartParams.size()) {
			// delete previous
			chartService.deleteChartParams(chartParamsList);
			chartService.deleteChartByApiKey(chartApiKey);
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
	@RequestMapping(value="/api/chart/{chartApiKey}", method = RequestMethod.POST)
	public List<ChartParams> addParamsToChart(@RequestBody List<ChartParams> chartParams,@PathVariable UUID chartApiKey) {
		// get the chart id
		Optional<Chart> chart = chartService.getChartByApiKey(chartApiKey);
		List<ChartParams> chartParamsList = new ArrayList<>();
		for(ChartParams chartParam: chartParams) {
			chartParam.setChart(chart.get());
			chartParamsList.add(chartService.addParamToChart(chartParam));
			
		}
		if(chartParamsList.size()!=chartParams.size()) {
			// delete previous
			chartService.deleteChartParams(chartParamsList);
			chartService.deleteChartByApiKey(chartApiKey);
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
	@RequestMapping(value="/api/chart/params/{chartApiKey}", method = RequestMethod.GET)
	public List<FullParams> getChartParamsByChartId(@PathVariable UUID chartApiKey) {
		return chartService.getFullChartParamsByChartApiKey(chartApiKey);
	}
	
	/**
	 * Get a chart by its Id
	 * @param chartId
	 * @return a chart
	 */
	@RequestMapping(value="/api/chart/{chartApiKey}", method = RequestMethod.GET)
	public Optional<Chart> getChartByChartId(@PathVariable UUID chartApiKey) {
		return chartService.getChartByApiKey(chartApiKey);
	}
	
	// @RequestMapping(value="/api/chart/generate", method = RequestMethod.GET)
	public void generate() {
		chartService.generate();
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
	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNotFound(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}
	
}
