package eu.qcloud.data.processor.processors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.qcloud.data.Data;
import eu.qcloud.data.DataForPlot;

public class RetentionTime extends Processor {

	public RetentionTime() {
		super();
		
	}
	
	@Override
	public List<DataForPlot> processData() {
		// get the mean by guideset
		HashMap<String, ArrayList<Float>> values = new HashMap<>();
		for(Data d: guideSetData) {
			if(values.containsKey(d.getContextSource().getName())) {
				values.get(d.getContextSource().getName()).add(d.getValue());
			}else {
				values.put(d.getContextSource().getName(), new ArrayList<>());
			}
		}
		HashMap<String, Float> means = new HashMap<>();
		
		for(Map.Entry<String, ArrayList<Float>> entry: values.entrySet()) {
			means.put(entry.getKey(), calculateMeanOfArrayList(entry.getValue()));
		}
		
		for(DataForPlot d: this.data) {
			float value = d.getValue();
			d.setValue((means.get(d.getContextSourceName())-value)/60);
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
	
	private float calculateMeanOfArrayList(ArrayList<Float> values) {
		float sum = 0f;
		for(Float f: values) {
			sum+=f;
		}
		return sum/values.size();
	}
	

	

}
