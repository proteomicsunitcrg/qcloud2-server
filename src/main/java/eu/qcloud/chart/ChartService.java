package eu.qcloud.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.chart.chartParams.ChartParams;
import eu.qcloud.chart.chartParams.ChartParamsId;
import eu.qcloud.chart.chartParams.ChartParamsRepository;
import eu.qcloud.chart.chartParams.ChartParamsRepository.FullParams;
/**
 * Service for charts and chart params
 * @author dmancera
 *
 */
@Service
public class ChartService {
	
	@Autowired
	private ChartRepository chartRepository;
	
	@Autowired
	private ChartParamsRepository chartParamsRepository;

	public Chart addNewChart(Chart chart) {
		return chartRepository.save(chart);		
	}
	
	public List<Chart> getAllCharts() {
		List<Chart> charts = new ArrayList<>();
		chartRepository.findAll().forEach(charts::add);
		return charts;
	}
	/**
	 * Add the params to a chart. If it fails in some point it will remove
	 * the inserted chart params in case there is any.
	 * @param chartParams
	 * @param chartId
	 * @return
	 */
	public boolean addParamsToChart(List<ChartParams> chartParams, Long chartId) {
		List<ChartParams> chartParamsList = new ArrayList<>();
		for(ChartParams chartParam: chartParams) {
			ChartParamsId id = new ChartParamsId();
			id.setChartId(chartParam.getChart().getId());
			id.setParamId(chartParam.getParam().getId());
			id.setContextSourceId(chartParam.getContextSource().getId());
			chartParam.setChartParamsId(id);
			if(chartParamsRepository.saveAll(chartParams) !=null) {
				chartParamsList.add(chartParam);
			}
		}
		if(chartParamsList.size()!=chartParams.size()) {
			// something went wrong, delete previous inserted
			deleteChartParams(chartParamsList);
			chartRepository.deleteById(chartId);
			return false;
		}
		return true;
	}
	public ChartParams addParamToChart(ChartParams chartParam) {
		ChartParamsId id = new ChartParamsId();
		id.setChartId(chartParam.getChart().getId());
		id.setParamId(chartParam.getParam().getId());
		id.setContextSourceId(chartParam.getContextSource().getId());
		chartParam.setChartParamsId(id);
		return chartParamsRepository.save(chartParam);
	}
	
	public List<ChartParams> getChartParamsByChart(Long chartId) {
		return chartParamsRepository.getByChartParamsIdChartId(chartId);
	}
	
	
	public void deleteChartParams(List<ChartParams> chartParamsList) {
		for(ChartParams c: chartParamsList) {
			chartParamsRepository.delete(c);
		}
	}
	
	public void deleteChartParamsByChartId(Long chartId) {
		chartParamsRepository.deleteByChartId(chartId);
	}
	
	public List<FullParams> getChartParamsByChartId(Long chartId) {
		return chartParamsRepository.findByChartParamsIdChartId(chartId);		
	}

	public Optional<Chart> getChartById(Long chartId) {
		return chartRepository.findById(chartId);
		
	}

	public void deleteChartById(Long chartId) {
		chartRepository.deleteById(chartId);
		
	}

	public List<Chart> getChartsByCVId(Long cvId) {
		return chartRepository.findByCvId(cvId);
	}
	
}
