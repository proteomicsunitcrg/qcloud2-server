package eu.qcloud.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.CV.CV;
import eu.qcloud.CV.CVRepository;
import eu.qcloud.chart.ChartRepository.NoView;
import eu.qcloud.chart.chartParams.ChartParams;
import eu.qcloud.chart.chartParams.ChartParamsId;
import eu.qcloud.chart.chartParams.ChartParamsRepository;
import eu.qcloud.chart.chartParams.ChartParamsRepository.FullParams;
import eu.qcloud.sampleType.SampleType;
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
	
	@Autowired
	private CVRepository cVRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public Chart addNewChart(Chart chart) {
		chart.setApiKey(UUID.randomUUID());
		Optional<CV> cv = cVRepository.getByCVId(chart.getCv().getCVId());
		if(!cv.isPresent()) {
			throw new DataRetrievalFailureException("Chart not found");
		}
		chart.setCv(cv.get());
		return chartRepository.save(chart);		
	}
	
	public Chart updateChart(Chart updatedChart) {
		Optional<Chart> original = chartRepository.findByApiKey(updatedChart.getApiKey());
		if(!original.isPresent()) {
			throw new DataRetrievalFailureException("Chart not found");
		}
		Chart chart = original.get();
		updatedChart.setId(chart.getId());
		
		return chartRepository.save(updatedChart);
		
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
	
	public List<ChartParams> getChartParamsByChartApiKey(UUID chartApiKey) {
		Optional<Chart> chart = chartRepository.findByApiKey(chartApiKey);
		if(!chart.isPresent()) {
			throw new DataRetrievalFailureException("Chart not found");
		}
		return chartParamsRepository.getByChartParamsIdChartId(chart.get().getId());
	}
	
	
	public void deleteChartParams(List<ChartParams> chartParamsList) {
		for(ChartParams c: chartParamsList) {
			chartParamsRepository.delete(c);
		}
	}
	
	public void deleteChartParamsByChartId(Long chartId) {
		chartParamsRepository.deleteByChartId(chartId);
	}
	
	public List<FullParams> getFullChartParamsByChartId(Long chartId) {
		return chartParamsRepository.findByChartParamsIdChartId(chartId);		
	}
	
	public ChartParams getChartParamByChartId(Long chartId) {
		return chartParamsRepository.findTopByChartId(chartId);
	}

	public Optional<Chart> getChartById(Long chartId) {
		return chartRepository.findById(chartId);
		
	}

	public void deleteChartById(Long chartId) {
		chartRepository.deleteById(chartId);
	}
	
	public void deleteChartByApiKey(UUID chartApiKey) {
		chartRepository.deleteByApiKey(chartApiKey);
	}

	public List<Chart> getChartsByCVId(Long cvId) {
		return chartRepository.findByCvId(cvId);
	}

	public List<NoView> getAllChartsWithoutView() {
		return chartRepository.findAllCharts();

	}

	public List<NoView> getChartsByCVIdWithoutView(String cvId) {
		return chartRepository.findByCvIdWithoutView(cvId);
	}
	
	public List<NoView> getChartsByCVIdAndSampleTypeCategoryId(Long cvId, Long sampleTypeId) {
		return chartRepository.findByCvIdAndSampleTypeId(cvId, sampleTypeId);
	}
	
	/**
	 * Get a list of the sample types of the charts by cv
	 * @param cvId the id of the CV
	 * @return
	 */
	public List<SampleType> getSampleTypesOfChartsByCV(Long cvId) {
		List<SampleType> sampleTypes = new ArrayList<>();
		chartRepository.findChartSampleTypesByCvId(cvId)
			.forEach(sampleTypes::add);
		return sampleTypes;
	}

	public List<FullParams> getFullChartParamsByChartApiKey(UUID chartApiKey) {
		Optional<Chart> chart = chartRepository.findByApiKey(chartApiKey);
		if(!chart.isPresent()) {
			
		}
		return chartParamsRepository.findByChartParamsIdChartId(chart.get().getId());
	}

	public Optional<Chart> getChartByApiKey(UUID chartApiKey) {
		Optional<Chart> chart = chartRepository.findByApiKey(chartApiKey); 
		if(!chart.isPresent()) {
			throw new DataRetrievalFailureException("Chart not found");
		}
		return chart;

	}

	public void generate() {
		chartRepository.findAll().forEach(c -> {
			c.setApiKey(UUID.randomUUID());
			chartRepository.save(c);
		});		
		
	}

}
