package eu.qcloud.data.processor;

import java.util.List;

import eu.qcloud.data.DataForPlot;

public interface DataProcessor {
	
	List<DataForPlot> processData();
	
	boolean isGuideSetRequired();

}
