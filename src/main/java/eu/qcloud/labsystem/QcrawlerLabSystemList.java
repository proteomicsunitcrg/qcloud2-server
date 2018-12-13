package eu.qcloud.labsystem;

import java.util.ArrayList;
import java.util.List;

public class QcrawlerLabSystemList {
	
	private List<QcrawlerLabSystem> labSys = new ArrayList<>();

	public List<QcrawlerLabSystem> getLabSys() {
		return labSys;
	}

	public void setLabSys(List<QcrawlerLabSystem> labSys) {
		this.labSys = labSys;
	}

	public QcrawlerLabSystemList(List<QcrawlerLabSystem> labSys) {
		this.labSys = labSys;
	}
	
}
