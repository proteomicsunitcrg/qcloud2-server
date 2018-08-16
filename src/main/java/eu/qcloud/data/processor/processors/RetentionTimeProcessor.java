package eu.qcloud.data.processor.processors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.qcloud.data.Data;
import eu.qcloud.data.DataForPlot;

public class RetentionTimeProcessor extends Processor {

	public RetentionTimeProcessor() {
		super();
		
	}
	
	@Override
	public List<DataForPlot> processData() {
		
		HashMap<String, ArrayList<Float>> guideSetValues = new HashMap<>();
		/**
		 * This for is formatting the data of the guide set in order
		 * to perform the calculation of the mean
		 */
		for(Data d: guideSetData) {
			if(guideSetValues.containsKey(d.getContextSource().getAbbreviated())) {
				if(d.getValue()!=0f) {
					guideSetValues.get(d.getContextSource().getAbbreviated()).add(d.getValue());					
				}
			}else {
				guideSetValues.put(d.getContextSource().getAbbreviated(), new ArrayList<>());
			}
		}
		/**
		 * This hashmap stores the means in order to substract to the
		 * retention time values of each pepetide
		 */
		HashMap<String, Float> means = new HashMap<>();
		
		for(Map.Entry<String, ArrayList<Float>> entry: guideSetValues.entrySet()) {
			means.put(entry.getKey(), calculateMeanOfArrayList(entry.getValue()));
		}
		
		for(DataForPlot d: this.data) {
			Float value = d.getValue();
			Float nV = (value-means.get(d.getContextSourceName()))/60;
			if(nV.isNaN()) {
				//d.setValue(Float.NaN);
				continue;
			}
			Float rounded =round(nV,1);
			d.setValue(rounded);
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
	private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
	

	

}
