package eu.qcloud.threshold.processor;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import eu.qcloud.data.Data;
import eu.qcloud.threshold.params.ThresholdParams;

/**
 * Threshold processor for peak area
 * @author dmancera
 *
 */
public class AreaProcessor extends ThresholdProcessor {

	@Override
	public void process(ThresholdParams thresholdParam) {
		// get the data
		
		DescriptiveStatistics ds = new DescriptiveStatistics();
		for(Data d: guideSetData) {
			if(d.getValue()>0) {
				ds.addValue(log2(d.getValue()));
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
	
	public static final float log2(float f){
	    return (float) (Math.log(f)/Math.log(2.0));
	}
	

}
