package eu.qcloud.data;

import java.util.Date;

/**
 * This is a helper class I had to do because the projection does not seems
 * to work properly when there are tuple queries to the database.
 * It was returning all null values when the non projected object was full of data.
 * @author dmancera
 *
 */
public class DataForPlot {
	
	protected String fileFilename;
	protected Date fileCreationDate;
	
	protected String contextSourceName;
	
	protected float value;
	
	public DataForPlot() {}
	
	public DataForPlot(String filename, Date fileCreationDate, String contextSourceName, float value) {
		super();
		this.fileFilename = filename;
		this.fileCreationDate = fileCreationDate;
		this.contextSourceName = contextSourceName;
		this.value = value;
	}


	public String getFileFilename() {
		return fileFilename;
	}

	public void setFileFilename(String fileFilename) {
		this.fileFilename = fileFilename;
	}
	
	public Date getFileCreationDate() {
		return fileCreationDate;
	}

	public void setFileCreationDate(Date fileCreationDate) {
		this.fileCreationDate = fileCreationDate;
	}

	public String getContextSourceName() {
		return contextSourceName;
	}

	public void setContextSourceName(String contextSourceName) {
		this.contextSourceName = contextSourceName;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	
}
