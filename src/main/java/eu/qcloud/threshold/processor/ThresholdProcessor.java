package eu.qcloud.threshold.processor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.data.Data;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.params.ThresholdParams;

public class ThresholdProcessor implements Processor {

	protected Threshold threshold;

	protected GuideSet guideSet;

	protected List<Data> guideSetData;

	public ThresholdProcessor() {
	}

	@JsonIgnore
	public List<Data> getGuideSetData() {
		return guideSetData;
	}

	public void setGuideSetData(List<Data> guideSetData) {
		this.guideSetData = guideSetData;
	}

	public Threshold getThreshold() {
		return threshold;
	}

	public void setThreshold(Threshold threshold) {
		this.threshold = threshold;
	}

	public GuideSet getGuideSet() {
		return guideSet;
	}

	public void setGuideSet(GuideSet guideSet) {
		this.guideSet = guideSet;
	}

	@Override
	public void process(ThresholdParams thresholdParam) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isGuideSetRequired() {
		// TODO Auto-generated method stub
		return false;
	}

}
