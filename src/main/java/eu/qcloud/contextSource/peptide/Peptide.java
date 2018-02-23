package eu.qcloud.contextSource.peptide;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.sampleType.SampleType;

@Entity(name="peptide")
public class Peptide extends ContextSource{
	
	private String sequence;
	
	private String abbreviatedSequence;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "peptides")
	private List<SampleType> sampleTypes;
	
	public List<SampleType> getSampleTypes() {
		return sampleTypes;
	}
	
	public void setSampleTypes(List<SampleType> sampleTypes) {
		this.sampleTypes = sampleTypes;
	}
	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getAbbreviatedSequence() {
		return abbreviatedSequence;
	}

	public void setAbbreviatedSequence(String abbreviatedSequence) {
		this.abbreviatedSequence = abbreviatedSequence;
	}
	
}
