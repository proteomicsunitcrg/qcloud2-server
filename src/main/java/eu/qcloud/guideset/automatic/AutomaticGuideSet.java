package eu.qcloud.guideset.automatic;

import java.util.Date;

import javax.persistence.Entity;

import eu.qcloud.guideset.GuideSet;

@Entity(name="automatic")
public class AutomaticGuideSet extends GuideSet {
	
	public AutomaticGuideSet() {}
	
	public AutomaticGuideSet(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	private int files;

	public int getFiles() {
		return files;
	}

	public void setFiles(int files) {
		this.files = files;
	}
	
	public Boolean getIsUserDefined() {
		return false;
	}
}
