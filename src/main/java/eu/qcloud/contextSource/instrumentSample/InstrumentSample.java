package eu.qcloud.contextSource.instrumentSample;

import javax.persistence.Entity;

import eu.qcloud.contextSource.ContextSource;

@Entity(name="element")
public class InstrumentSample extends ContextSource{

	private String abbreviatedName;

	public String getAbbreviatedName() {
		return abbreviatedName;
	}

	public void setAbbreviatedName(String abbreviatedName) {
		this.abbreviatedName = abbreviatedName;
	}
	
}
