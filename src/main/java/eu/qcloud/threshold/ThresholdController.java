package eu.qcloud.threshold;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.chart.ChartService;
import eu.qcloud.chart.chartParams.ChartParams;
import eu.qcloud.threshold.ThresholdRepository.ThresholdForPlot;
import eu.qcloud.threshold.ThresholdRepository.withParamsWithoutThreshold;
import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThreshold;
import eu.qcloud.threshold.params.ThresholdParams;
import eu.qcloud.threshold.params.ThresholdParamsRepository.paramsNoThreshold;
import eu.qcloud.threshold.sigmathreshold.SigmaThreshold;

@RestController
@PreAuthorize("hasRole('ADMIN')")
/**
 * Thresholds have different parameters that are configured
 * in each Threshold.class extension.
 * A threshold must have a threshold processor implemeting the Processor interface.
 * Also, a threshold must have a direction and some constraints. Please check this classes
 * to know how it work.
 * @author dmancera
 *
 */
public class ThresholdController {

	@Autowired
	private ThresholdService thresholdService;
	
	@Autowired
	private ChartService chartService;
	
	/**
	 * Get all thresholds
	 * @return all the trhresholds
	 */
	@RequestMapping(value="/api/threshold", method = RequestMethod.GET)
	public List<withParamsWithoutThreshold> getAll() {
		return thresholdService.getAll();
	}
	@RequestMapping(value="/api/threshold/mini", method = RequestMethod.GET)
	public List<withParamsWithoutThreshold> getAllMini() {
		return thresholdService.getMini();
	}
	
	/**
	 * Get all threshold types
	 * Threshold types are an enumeration
	 * @return a string array with the values
	 */
	@RequestMapping(value="/api/threshold/types", method = RequestMethod.GET)
	public List<String> getThresholdTypes() {
		List<String> thresholds = new ArrayList<>();
		for(ThresholdType t : ThresholdType.values()) {
			thresholds.add(t.name());
		}
		return thresholds;
	}
	
	/**
	 * Get all threshold directions
	 * Threshold directions are an enumeration
	 * @return
	 */
	@RequestMapping(value="/api/threshold/directions", method = RequestMethod.GET)
	public List<String> getThresholdDirections() {
		List<String> directions = new ArrayList<>();
		for(Direction d : Direction.values()) {
			directions.add(d.name());
		}
		return directions;
	}
	
	/**
	 * Save a new threshold in the database.
	 * It will also create the threshold for all the
	 * current labsystems in the database.
	 * @param threshold the threshold to be saved
	 */
	@RequestMapping(value="/api/threshold/{type}", method= RequestMethod.POST)
	public Threshold addNewThreshold(@PathVariable ThresholdType type, @RequestBody Threshold threshold) {
		// Check if a threshold of that param already exists
		
		Optional<Threshold> t = thresholdService.findThresholdBySampleTypeIdAndParamIdAndCvId(threshold.sampleType.getId(),
				threshold.getParam().getId(),threshold.getCv().getId());
		if(t.isPresent()) {
			throw new DataIntegrityViolationException("Threshold already exists");
		}
		
		/**
		 * I had to this because I could not do a downcast from threshold
		 * to a more specific son.
		 */
		switch(type) {
		case SIGMA:
			SigmaThreshold st = new SigmaThreshold();
			st.setCv(threshold.getCv());
			st.setSteps(threshold.getSteps());
			st.setParam(threshold.getParam());
			st.setSampleType(threshold.getSampleType());
			return thresholdService.saveSigmaThreshold(st);
		case HARDLIMIT:
			HardLimitThreshold ht = new HardLimitThreshold();
			ht.setCv(threshold.getCv());
			ht.setSteps(threshold.getSteps());
			ht.setParam(threshold.getParam());
			ht.setSampleType(threshold.getSampleType());
			return thresholdService.saveHardLimitThreshold(ht);
		default:
			return null;
		}
		
	}
	
	@RequestMapping(value="/api/threshold/{sampleTypeId}/{paramId}/{cvId}/{labSystemId}", method = RequestMethod.GET)
	public ThresholdForPlot getThreshold(@PathVariable Long sampleTypeId, @PathVariable Long paramId, @PathVariable Long cvId, @PathVariable Long labSystemId) {
		Threshold t = thresholdService.findThresholdBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(sampleTypeId,paramId,cvId,labSystemId);
		// calculate threshold
		thresholdService.processThreshold(t);			
		return thresholdService.getThreshold(t.getId());
	}
	
	@RequestMapping(value="/api/threshold/plot/{chartId}/{labSystemId}", method = RequestMethod.GET)
	public ThresholdForPlot getPlotThreshold(@PathVariable Long chartId,@PathVariable Long labSystemId) {
		// get the param
		ChartParams chartParam = chartService.getChartParamByChartId(chartId);
		Threshold t = thresholdService.findThresholdBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(
				chartParam.getChart().getSampleType().getId(),
				chartParam.getParam().getId(),
				chartParam.getChart().getCv().getId(),labSystemId);
		// calculate threshold
		
		if(t!=null) {
			thresholdService.processThreshold(t);
			return thresholdService.getThreshold(t.getId());	
		}else {
			return null;
		}
	}
	
	public paramsNoThreshold getThresholdParams(Long thresholdId) {
		return null;
		
	}
	
	@RequestMapping(value="/api/threshold/params", method= RequestMethod.POST)
	public void saveThresholdParams(@RequestBody List<ThresholdParams> thresholdParams) {
		thresholdService.saveThresholdParams(thresholdParams);
	}
	
	@RequestMapping(value="/api/threshold/params", method= RequestMethod.GET)
	public List<paramsNoThreshold> getAllParams() {
		return thresholdService.getAllThresholdParams();
	}
	
	
	
	/**
	 * Return the constraints for a given threshold type
	 * @param thresholdType 
	 * @return
	 */
	@RequestMapping(value="/api/threshold/constraints/admin/{thresholdType}", method = RequestMethod.GET)
	public ThresholdConstraint getThresholdConstraint(@PathVariable ThresholdType thresholdType) {
		return ThresholdFactory.getAdminConstraints(thresholdType);
		
	}
	
	/*
	 * Exception handlers
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
}
