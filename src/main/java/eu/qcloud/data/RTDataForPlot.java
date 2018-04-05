package eu.qcloud.data;

import java.util.Date;

/**
 * Auxiliary class for return the data of the
 * retention time plots
 * @author dmancera
 *
 */
public class RTDataForPlot extends DataForPlot {
	
	public RTDataForPlot(String filename, Date fileCreationDate, String contextSourceName, float value, float medianRT) {
		this.fileFilename = filename;
		this.fileCreationDate = fileCreationDate;
		this.contextSourceName = contextSourceName;
		this.value = medianRT -value;
	}
	
	

}
