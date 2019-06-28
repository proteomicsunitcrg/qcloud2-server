package eu.qcloud.utils.factory;

import java.util.ArrayList;
import java.util.List;

import eu.qcloud.threshold.Threshold;

public class ThresholdForPlotFactory {
	
	public static ThresholdForPlotImpl create(Threshold threshold) {
		List<ParamsNoThresholdImpl> params = new ArrayList<>();
		threshold.getThresholdParams().forEach(tp -> {
			params.add(new ParamsNoThresholdImpl(tp.getStepValue(), tp.getInitialValue(), tp.getContextSource(), tp.getIsEnabled()));
		});
		
		return new ThresholdForPlotImpl(threshold.getThresholdType(),
				threshold.getDirection(),
				threshold.getNonConformityDirection(), threshold.getSteps(), threshold.isMonitored(), params, threshold.isCommFeat());
	}

}
