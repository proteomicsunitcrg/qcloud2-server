package eu.qcloud.labsystem;

import java.util.List;
import java.util.UUID;

import eu.qcloud.sampleType.SampleType;

public class QcrawlerLabSystem {
	
	private String name;
	
	private UUID apiKey;
	
	private List<SampleType> sampleTypes;

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

	public List<SampleType> getSampleTypes() {
		return sampleTypes;
	}

	public void setSampleTypes(List<SampleType> sampleTypes) {
		this.sampleTypes = sampleTypes;
	}

	/**
	 * 
	 * @param name
	 * @param apiKey
	 * @param sampleTypes
	 */
	public QcrawlerLabSystem(String name, UUID apiKey, List<SampleType> sampleTypes) {
		this.name = name;
		this.apiKey = apiKey;
		this.sampleTypes = sampleTypes;
	}
	
	
	
	
}
