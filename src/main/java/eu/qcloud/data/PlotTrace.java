package eu.qcloud.data;

import java.util.ArrayList;
import java.util.List;

import eu.qcloud.traceColor.TraceColor;

public class PlotTrace {
	
	private String abbreviated;
	
	private TraceColor traceColor;
	
	private int shade;
	
	private List<PlotTracePoint> plotTracePoints;
	

	public PlotTrace() {
		this.plotTracePoints = new ArrayList<>();
	}

	public String getAbbreviated() {
		return abbreviated;
	}

	public void setAbbreviated(String abbreviated) {
		this.abbreviated = abbreviated;
	}

	public TraceColor getTraceColor() {
		return traceColor;
	}

	public void setTraceColor(TraceColor traceColor) {
		this.traceColor = traceColor;
	}

	public int getShade() {
		return shade;
	}

	public void setShade(int shade) {
		this.shade = shade;
	}

	public List<PlotTracePoint> getPlotTracePoints() {
		return plotTracePoints;
	}

	public void setPlotTracePoints(List<PlotTracePoint> plotTracePoints) {
		this.plotTracePoints = plotTracePoints;
	}

}
