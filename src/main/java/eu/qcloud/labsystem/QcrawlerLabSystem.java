package eu.qcloud.labsystem;

import java.util.List;
import java.util.UUID;

public class QcrawlerLabSystem {

	private String name;

	private UUID apiKey;

	private List<QcrawlerSampleType> sampleTypes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

	public List<QcrawlerSampleType> getSampleTypes() {
		return sampleTypes;
	}

	public void setSampleTypes(List<QcrawlerSampleType> sampleTypes) {
		this.sampleTypes = sampleTypes;
	}

	/**
	 *
	 * @param name
	 * @param apiKey
	 * @param sampleTypes
	 */
	public QcrawlerLabSystem(String name, UUID apiKey, List<QcrawlerSampleType> sampleTypes) {
		this.name = name;
		this.apiKey = apiKey;
		this.sampleTypes = sampleTypes;
	}

}
