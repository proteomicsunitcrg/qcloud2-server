package eu.qcloud.data;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.contextSource.instrumentSample.InstrumentSample;
import eu.qcloud.contextSource.instrumentSample.InstrumentSampleService;
import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.contextSource.peptide.PeptideService;
import eu.qcloud.data.insertmodel.DataFromPipeline;
import eu.qcloud.file.File;
import eu.qcloud.file.FileService;
import eu.qcloud.param.Param;
import eu.qcloud.param.ParamRepository;


/**
 * Data controller. It handles the add new data and
 * recover data.
 * @author dmancera
 *
 */
@RestController
public class DataController {

	@Autowired
	private DataService dataService;
	@Autowired
	private PeptideService peptideService;
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ParamRepository paramRepository;;

	@Autowired
	private InstrumentSampleService instrumentSampleService;
	
	
	@RequestMapping(value="/api/data",method= RequestMethod.GET)
	public List<Data> getData() {
		return dataService.getAllData();
	}
	
	@RequestMapping(value="/api/data",method= RequestMethod.POST)	
	public Data addData(@RequestBody Data data) {
		return dataService.addData(data);
	}
	/**
	 * Insert new data into the database. Before add new data there
	 * must be a file of reference. Use this function if your parameter
	 * holds more than one context source
	 * @param paramId
	 * @param quantificationSourceSequence
	 * @param checksum
	 * @param data
	 * @return
	 */
	@RequestMapping(value="/api/data/{paramQCCV}/{quantificationSourceSequence}/{checksum}",method = RequestMethod.POST)
	public Data insertData(@PathVariable String paramQCCV,
			@PathVariable String quantificationSourceSequence,
			@PathVariable String checksum,@RequestBody Data data) {
		Peptide p = peptideService.findPeptideBySequence(quantificationSourceSequence);
		File f = fileService.getFileByChecksum(checksum);
		Param param = paramRepository.findByQccv(paramQCCV);
		data.setDataId(new DataId(param.getId(),p.getId(),f.getId()));
		return dataService.addData(data);
	}
	
	
	/**
	 * Insert new data into the database. Before add new data there
	 * must be a file of reference. Use this function only if your
	 * parameter has only one context source.
	 * @param paramId
	 * @param instrumentSampleId
	 * @param checksum
	 * @param data
	 * @return
	 */
	@RequestMapping(value="/api/data/simple/{paramQCCV}/{instrumentSampleQCCV}/{checksum}",method = RequestMethod.POST)
	public Data insertSimpleData(@PathVariable String paramQCCV,
			@PathVariable String instrumentSampleQCCV,
			@PathVariable String checksum,@RequestBody Data data) {
		
		InstrumentSample is = instrumentSampleService.findByQCCV(instrumentSampleQCCV);
		Param p = paramRepository.findByQccv(paramQCCV);
		
		File f = fileService.getFileByChecksum(checksum);
		data.setDataId(new DataId(p.getId(),is.getId(),f.getId()));
		return dataService.addData(data);
	}
	
	/**
	 * This function retrieves data between two dates.
	 * @param startDate
	 * @param endDate
	 * @param chartId
	 * @param dataSourceId
	 * @param sampleTypeId
	 * @return a list of data in a form of a DataForPlot, another class.
	 */
	@RequestMapping(value="/api/data/{startDate}/{endDate}/{chartApiKey}/{labSystemApiKey}/{sampleTypeQCCV}", method=RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public List<DataForPlot> getPlotData(@PathVariable String startDate,
			@PathVariable  String endDate,
			@PathVariable UUID chartApiKey, 
			@PathVariable UUID labSystemApiKey,
			@PathVariable String sampleTypeQCCV) {
		Date start = Date.valueOf(startDate);
		Date end = Date.valueOf(endDate);
		return dataService.getPlotData(start, end, chartApiKey, labSystemApiKey, sampleTypeQCCV);
	}
	
	@RequestMapping(value="/api/data/iso/{checksum}/{abbreviated}", method=RequestMethod.GET)
	public List<DataForPlot> isotopologueData(@PathVariable String checksum,@PathVariable String abbreviated ) {
		return dataService.getIsotopologueData(checksum, abbreviated);
	}
	
	/**
	 * Insert data from the pipeline into the database
	 * @param dataFromPipeline the data to insert
	 */
	@RequestMapping(value="/api/data/pipeline", method=RequestMethod.POST)
	public void insertDataFromPipeline(@RequestBody DataFromPipeline dataFromPipeline) {
		dataService.insertDataFromPipeline(dataFromPipeline);		
	}
	
	/*
	 * Helper classes
	 */
		
	@ExceptionHandler(IllegalArgumentException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
	    response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
	}
	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleConflict(HttpServletResponse response, Exception e) throws IOException {
	    response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
	
}
