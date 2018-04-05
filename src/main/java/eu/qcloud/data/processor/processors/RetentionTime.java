package eu.qcloud.data.processor.processors;

import java.util.List;

import eu.qcloud.data.DataForPlot;

public class RetentionTime extends Processor {

	public RetentionTime() {
		super();
		
	}
	
	@Override
	public List<DataForPlot> processData() {
		for(DataForPlot d: this.data) {
			float value = d.getValue();
			d.setValue(value);
		}
		return this.data;
	}

	@Override
	public String toString() {
		return "RetentionTime []";
	}


	@Override
	public boolean isGuideSetRequired() {
		return true;

	}
	

	

}
