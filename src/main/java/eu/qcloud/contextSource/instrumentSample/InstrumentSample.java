package eu.qcloud.contextSource.instrumentSample;

import javax.persistence.Entity;

import eu.qcloud.contextSource.ContextSource;

@Entity(name="instrument_sample")
public class InstrumentSample extends ContextSource{

	private String qCCV;

	public String getqCCV() {
		return qCCV;
	}

	public void setqCCV(String qCCV) {
		this.qCCV = qCCV;
	}
	
}
