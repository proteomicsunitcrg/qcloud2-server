package eu.qcloud.data;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.contextSource.peptide.PeptideService;
import eu.qcloud.data.DataRepository.MiniData;
import eu.qcloud.dataSource.DataSourceService;
import eu.qcloud.file.File;
import eu.qcloud.file.FileService;
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;



@RestController
public class DataController {

	@Autowired
	private DataService dataService;
	@Autowired
	private PeptideService peptideService;
	@Autowired
	private FileService fileService;
	@Autowired
	private DataSourceService dataSourceService;
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/api/data",method= RequestMethod.GET)
	public List<Data> getData() {
		return dataService.getAllData();
	}
	
	@RequestMapping(value="/api/data",method= RequestMethod.POST)	
	public Data addData(@RequestBody Data data) {
		return dataService.addData(data);
	}
	/*
	@RequestMapping(value="/api/data/{fileId}",method= RequestMethod.GET)
	public MiniData getSomeData(@PathVariable Long fileId) {
		return dataService.getMiniData(fileId);
	}
	*/
	@RequestMapping(value="/api/data/{paramId}/{quantificationSourceSequence}/{checksum}",method = RequestMethod.POST)
	public Data insertData(@PathVariable Long paramId,
			@PathVariable String quantificationSourceSequence,
			@PathVariable String checksum,@RequestBody Data data) {
		Peptide p = peptideService.findPeptideBySequence(quantificationSourceSequence);
		File f = fileService.getFileByChecksum(checksum);
		data.setDataId(new DataId(paramId,p.getId(),f.getId()));
		return dataService.addData(data);
	}
	@RequestMapping(value="/testing")
	public void test() {
		throw new IllegalArgumentException("The 'name' parameter must not be null or empty");			
		
	}
	/*
	@RequestMapping(value="/api/data/{startDate}/{endDate}", method=RequestMethod.GET)
	public List<MiniData> getDataBetweenDates(@PathVariable String startDate,@PathVariable  String endDate) {
		Date start = Date.valueOf(startDate);
		Date end = Date.valueOf(endDate);
		return dataService.getDataBetweenDates(start, end);
	}
	*/
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value="/api/data/{startDate}/{endDate}/{chartId}/{dataSourceId}", method=RequestMethod.GET)
	public List<MiniData> getPlotData(@PathVariable String startDate,@PathVariable  String endDate,@PathVariable Long chartId, @PathVariable Long dataSourceId) {
		Date start = Date.valueOf(startDate);
		Date end = Date.valueOf(endDate);
		return dataService.getPlotData(start, end, chartId, dataSourceId);
	}
	
	
	/*
	@RequestMapping(value="/api/data/{startDate}/{endDate}/{dataSourceId}", method=RequestMethod.GET)
	public List<MiniData> getDataBetweenDatesByDataSourceId(@PathVariable String startDate,
			@PathVariable  String endDate,
			@PathVariable Long dataSourceId) {
		
		//Check if current user has the requested node		
		User user = getManagerFromSecurityContext();
		if(!dataSourceService.checkIfNodeHasDataSource(dataSourceId,user.getNode().getId())) {
			throw new IllegalArgumentException("Algo haces mal binguero.");
		}
		
		Date start = Date.valueOf(startDate);
		Date end = Date.valueOf(endDate);
		return dataService.getDataBetweenDatesByDataSourceId(start, end, dataSourceId);
	}
	*/
	/*
	 * Helper classes
	 */
	/**
	 * Get the current user from the security context
	 * 
	 * @return the logged user
	 */
	private User getManagerFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User manager = userService.getUserByUsername(authentication.getName());
		return manager;
	}
	
	
	@ExceptionHandler(IllegalArgumentException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
	    response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
	}
	
}
