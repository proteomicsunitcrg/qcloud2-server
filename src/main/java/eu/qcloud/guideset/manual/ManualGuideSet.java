package eu.qcloud.guideset.manual;

import javax.persistence.Entity;

import eu.qcloud.guideset.GuideSet;

@Entity(name = "manual")
public class ManualGuideSet extends GuideSet {

	public Boolean getIsUserDefined() {
		return true;
	}
}
