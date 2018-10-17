package eu.qcloud.threshold;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
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
import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemService;
import eu.qcloud.threshold.ThresholdRepository.withParamsWithoutThreshold;
import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.labsystemstatus.LabSystemStatus;
import eu.qcloud.threshold.params.ThresholdParams;
import eu.qcloud.threshold.params.ThresholdParamsRepository.paramsNoThreshold;
import eu.qcloud.utils.factory.ThresholdForPlotImpl;

@RestController
// @PreAuthorize("hasRole('ADMIN')")
/**
 * Thresholds have different parameters that are configured in each
 * Threshold.class extended classes. A threshold must have a threshold processor
 * implementing the Processor interface. Also, a threshold must have a direction
 * and some constraints. Please check this classes to know how it work.
 * 
 * @author dmancera
 *
 */
public class ThresholdController {

	@Autowired
	private ThresholdService thresholdService;

	@Autowired
	private ChartService chartService;

	@Autowired
	private LabSystemService labSystemService;

	/**
	 * Get all thresholds
	 * 
	 * @return all the trhresholds
	 */
	@RequestMapping(value = "/api/threshold", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List<withParamsWithoutThreshold> getAll() {
		return thresholdService.getAll();
	}

	@RequestMapping(value = "/api/threshold/mini", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List<withParamsWithoutThreshold> getAllMini() {
		return thresholdService.getMini();
	}

	/**
	 * Get all threshold types Threshold types are an enumeration
	 * 
	 * @return a string array with the values
	 */
	@RequestMapping(value = "/api/threshold/types", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List<String> getThresholdTypes() {
		List<String> thresholds = new ArrayList<>();
		for (ThresholdType t : ThresholdType.values()) {
			thresholds.add(t.name());
		}
		return thresholds;
	}

	/**
	 * Get all threshold directions Threshold directions are an enumeration
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/threshold/directions", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List<String> getThresholdDirections() {
		List<String> directions = new ArrayList<>();
		for (Direction d : Direction.values()) {
			directions.add(d.name());
		}
		return directions;
	}

	/**
	 * Save a new threshold in the database.
	 * 
	 * @param threshold
	 *            the threshold to be saved
	 */
	@RequestMapping(value = "/api/threshold/{type}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public Threshold addNewThreshold(@PathVariable ThresholdType type, @RequestBody Threshold threshold) {
		// Check if a threshold of that param already exists

		Optional<Threshold> t = thresholdService.findThresholdBySampleTypeQCCVAndParamQCCVAndCVQCCV(
				threshold.getSampleType().getQualityControlControlledVocabulary(), threshold.getParam().getqCCV(),
				threshold.getCv().getCVId());
		if (t.isPresent()) {
			throw new DataIntegrityViolationException("Threshold already exists");
		}

		return thresholdService.saveThreshold(type, threshold);

	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/threshold/plot/{chartApiKey}/{labSystemApiKey}", method = RequestMethod.GET)
	public ThresholdForPlotImpl getPlotThreshold(@PathVariable UUID chartApiKey, @PathVariable UUID labSystemApiKey) {
		// get the param
		ChartParams chartParam = chartService.getTopChartParamByChartApiKey(chartApiKey);
		return thresholdService.calculateThresholdForPlotByParamIdAndSampleTypeIdAndLabSystemApiKey(
				chartParam.getParam().getId(), chartParam.getChart().getSampleType().getId(), labSystemApiKey);
	}
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/threshold/nonconformityplot/{thresholdApiKey}/{fileChecksum}/{contextSourceApiKey}", method = RequestMethod.GET)
	public ThresholdForPlotImpl getNonConformityPlotThresholdWithoutGuideSet(@PathVariable UUID thresholdApiKey,
			@PathVariable String fileChecksum, @PathVariable UUID contextSourceApiKey) {
		return thresholdService.getNonConformityThresholdWithoutGuideSet(thresholdApiKey, fileChecksum, contextSourceApiKey);
	}

	public paramsNoThreshold getThresholdParams(Long thresholdId) {
		return null;
	}

	@RequestMapping(value = "/api/threshold/params", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public void saveThresholdParams(@RequestBody List<ThresholdParams> thresholdParams) {
		thresholdService.saveThresholdParams(thresholdParams);
	}

	/**
	 * Return the constraints for a given threshold type
	 * 
	 * @param thresholdType
	 * @return
	 */
	@RequestMapping(value = "/api/threshold/constraints/admin/{thresholdType}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ThresholdConstraint getThresholdConstraint(@PathVariable ThresholdType thresholdType) {
		return ThresholdFactory.getAdminConstraints(thresholdType);
	}

	/**
	 * Return all the thresholds by labsystem apikey
	 * 
	 * @param labSystemApiKey
	 * @return an array with the thresholds of a given labsystem
	 */
	@RequestMapping(value = "/api/threshold/node/{labSystemApiKey}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MANAGER')")
	public List<withParamsWithoutThreshold> getNodeThresholdsBySystemApiKey(@PathVariable UUID labSystemApiKey) {
		return thresholdService.findAllThresholdsByLabSystemApiKey(labSystemApiKey);
	}

	@RequestMapping(value = "/api/threshold/switchmonitor/{thresholdApiKey}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('MANAGER')")
	public void switchThresholdMonitoring(@PathVariable UUID thresholdApiKey) {
		thresholdService.switchThresholdMonitoring(thresholdApiKey);
	}
	
	@RequestMapping(value = "/api/threshold/switchcontextsource/{thresholdApiKey}/{contextSourceApiKey}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('MANAGER')")
	public void switchThresholdContextSourceMonitoring(@PathVariable UUID thresholdApiKey, @PathVariable UUID contextSourceApiKey) {
		thresholdService.switchThresholdContextSourceMonitoring(thresholdApiKey, contextSourceApiKey);
	}

	/**
	 * Update a threshold with new threshold parameters
	 * 
	 * @param thresholdId
	 *            the threshold id
	 * @param thresholdParams
	 *            the new threshold parameters
	 */
	@RequestMapping(value = "/api/threshold/{thresholdApiKey}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public void updateThresholdParams(@PathVariable UUID thresholdApiKey,
			@RequestBody List<ThresholdParams> thresholdParams) {
		// get threshold
		thresholdService.updateThresholdParams(thresholdApiKey, thresholdParams);
	}

	/**
	 * Return the lab system status. It will check the monitored values of the last
	 * file of the system.
	 * 
	 * @param the
	 *            lab system apikey
	 * @return the labsystem status
	 */
	@RequestMapping(value = "/api/threshold/status/{labSystemApiKey}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public List<LabSystemStatus> getLabSystemStatus(@PathVariable UUID labSystemApiKey) {
		Optional<LabSystem> ls = labSystemService.findSystemByApiKey(labSystemApiKey);
		if (ls.isPresent()) {
			LabSystem labSystem = ls.get();
			return thresholdService.getLabSystemStatus(labSystem);
		} else {
			throw new DataRetrievalFailureException("Lab system not found.");
		}

	}

	/*
	 * Exception handlers
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNotFound(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	@ExceptionHandler(InvalidActionException.class)
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

}
