package eu.qcloud.data.processor.processors;

import java.util.ArrayList;
import java.util.List;

import eu.qcloud.data.Data;
import eu.qcloud.data.DataForPlot;
import eu.qcloud.data.processor.DataProcessor;
import eu.qcloud.labsystem.GuideSet;

public abstract class Processor implements DataProcessor{
	/**
	 * The requested data for the plot
	 */
	protected List<DataForPlot> data;
	
	/**
	 * The guide set, probable this will be out of the
	 * class
	 */
	protected GuideSet guideSet;
	
	/**
	 * Requested data of the guide set. Used to process
	 * some data... maybe to get mean on RT, etc...
	 */
	protected ArrayList<Data> guideSetData;
	
	public ArrayList<Data> getGuideSetData() {
		return guideSetData;
	}

	public void setGuideSetData(ArrayList<Data> guideSetData) {
		this.guideSetData = guideSetData;
	}

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
		return data;
	}

	@Override
	public boolean isGuideSetRequired() {

		return false;
	}
	
	
}
