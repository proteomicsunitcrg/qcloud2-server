package eu.qcloud.threshold.processor;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import eu.qcloud.data.Data;
import eu.qcloud.threshold.params.ThresholdParams;

public class SigmaProcessor extends ThresholdProcessor {
	@Override
	public void process(ThresholdParams thresholdParam) {
		System.out.println("using sigma processor");
		// get the data
		
		DescriptiveStatistics ds = new DescriptiveStatistics();
		for(Data d: guideSetData) {
			if(d.getValue()>0) {
				ds.addValue(d.getValue());
			} else {
				// ds.addValue(0f);	
			}
		}
		thresholdParam.setInitialValue((float)ds.getMean());
		thresholdParam.setStepValue((float)ds.getStandardDeviation());
	}

	@Override
	public boolean isGuideSetRequired() {
		return true;
	}
	
}
