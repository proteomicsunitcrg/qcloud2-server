package eu.qcloud.data.insertmodel;

import java.util.List;

import eu.qcloud.file.File;

public class DataFromPipeline {
	
	private File file;
	
	private List<ParameterData> data;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<ParameterData> getData() {
		return data;
	}

	public void setData(List<ParameterData> data) {
		this.data = data;
	}
	
}
