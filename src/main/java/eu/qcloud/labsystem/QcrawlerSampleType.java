package eu.qcloud.labsystem;

import java.util.UUID;

public class QcrawlerSampleType {

	private String name;

	private String qccv;

	private QcrawlerSampleTypeCategory sampleTypeCategory;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public QcrawlerSampleType(String name, UUID apiKey) {
		this.name = name;
	}

	public String getQccv() {
		return qccv;
	}

	public void setQccv(String qccv) {
		this.qccv = qccv;
	}

	public QcrawlerSampleType() {
	}

	public QcrawlerSampleTypeCategory getSampleTypeCategory() {
		return sampleTypeCategory;
	}

	public void setSampleTypeCategory(QcrawlerSampleTypeCategory sampleTypeCategory) {
		this.sampleTypeCategory = sampleTypeCategory;
	}

	public QcrawlerSampleType(String name, QcrawlerSampleTypeCategory sampleTypeCategory) {
		this.name = name;
		this.sampleTypeCategory = sampleTypeCategory;
	}

	public QcrawlerSampleType(String name, String qccv, QcrawlerSampleTypeCategory sampleTypeCategory) {
		this.name = name;
		this.qccv = qccv;
		this.sampleTypeCategory = sampleTypeCategory;
	}

}
