package eu.qcloud.data.processor.processors;

import java.util.List;

import eu.qcloud.data.DataForPlot;
import eu.qcloud.data.processor.DataProcessor;
import eu.qcloud.dataSource.GuideSet;

public class Processor implements DataProcessor{
	
	protected List<DataForPlot> data;
	
	protected GuideSet guideSet;
	
	public GuideSet getGuideSet() {
		return guideSet;
	}

	public void setGuideSet(GuideSet guideSet) {
		this.guideSet = guideSet;
	}

	public List<DataForPlot> getData() {
		return data;
	}

	public void setData(List<DataForPlot> data) {
		this.data = data;
	}

	public Processor() {
	}
	
	@Override
	public List<DataForPlot> processData() {
		return null;
	}

	@Override
	public boolean isGuideSetRequired() {

		return false;
	}
	
	
}
