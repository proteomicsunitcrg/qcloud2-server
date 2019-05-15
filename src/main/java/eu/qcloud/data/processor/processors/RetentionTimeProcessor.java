package eu.qcloud.data.processor.processors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
					System.out.println("VALUE: " + d.getValue());
					guideSetValues.get(d.getContextSource().getAbbreviated()).add(d.getValue());
					System.out.println(	guideSetValues.get(d.getContextSource().getAbbreviated()).add(d.getValue()));
				}
			}else {
				System.out.println("ELSE");
				guideSetValues.put(d.getContextSource().getAbbreviated(), new ArrayList<>());
				guideSetValues.get(d.getContextSource().getAbbreviated()).add(d.getValue());
			}
		}
		/**
		 * This hashmap stores the means in order to substract to the
		 * retention time values of each pepetide
		 */
		HashMap<String, Float> means = new HashMap<>();
		
		for(Map.Entry<String, ArrayList<Float>> entry: guideSetValues.entrySet()) {
			System.out.println("Entry" + entry.getKey());
			means.put(entry.getKey(), calculateMeanOfArrayList(entry.getValue()));
			System.out.println("Entry val: " + calculateMeanOfArrayList(entry.getValue()));
		}
		
		for(DataForPlot d: this.data) {
			Float value = d.getValue();
			/*
			 * At this time 2018/8/30 we consider a value of 0 at the retention
			 * time as a null value.
			 * Daniel Mancera
			 */
			if(value == 0f) {
				d.setValue(Float.NaN);
				continue;
			}
			System.out.println("CS NAME" + d.getContextSourceName());
			System.out.println("MEAN HASH: " + Arrays.asList(means));
			Float nV = (value-means.get(d.getContextSourceName()))/60;
			System.out.println("MEAAAAAAAAAAAAAAAAAAAAAAAAAAN: " + means.get(d.getContextSourceName()));
			if(nV.isNaN()) {
				//d.setValue(Float.NaN);
				continue;
			}
			Float rounded =nV;
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
