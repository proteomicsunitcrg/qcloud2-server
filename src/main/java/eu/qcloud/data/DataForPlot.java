package eu.qcloud.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * This is a helper class I had to do because the projection does not seems
 * to work properly when there are tuple queries to the database.
 * It was returning all null values when the non projected object was full of data.
 * @author dmancera
 *
 */
public class DataForPlot implements Comparable<DataForPlot>{
	
	protected String fileFilename;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")	
	protected Date fileCreationDate;
	
	protected String contextSourceName;
	
	protected Float value;
	
	public DataForPlot() {}
	
	public DataForPlot(String filename, Date fileCreationDate, String contextSourceName, Float value) {
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

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	@Override
	public int compareTo(DataForPlot o) {
		try {
			Float me = Float.parseFloat(contextSourceName);
			Float other = Float.parseFloat(o.getContextSourceName());
			if(me>other) {
				return -1;
			}else {
				return 1;
			}	
		}catch (NullPointerException npe) {
			return 0;
		}catch (NumberFormatException nfe) {
			return 0;
		}
		
	}

	
	
	
	
}
