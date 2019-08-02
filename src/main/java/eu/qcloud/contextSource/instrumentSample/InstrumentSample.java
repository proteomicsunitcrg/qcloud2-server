package eu.qcloud.contextSource.instrumentSample;

import javax.persistence.Column;
import javax.persistence.Entity;

import eu.qcloud.contextSource.ContextSource;

@Entity(name = "instrument_sample")
public class InstrumentSample extends ContextSource {

	@Column(name = "quality_control_controlled_vocabulary", unique = true)
	private String qualityControlControlledVocabulary;

	public String getqCCV() {
		return qualityControlControlledVocabulary;
	}

	public void setqCCV(String qCCV) {
		this.qualityControlControlledVocabulary = qCCV;
	}

}
