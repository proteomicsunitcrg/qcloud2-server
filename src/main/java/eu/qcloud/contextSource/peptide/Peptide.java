package eu.qcloud.contextSource.peptide;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.sampleComposition.SampleComposition;

@Entity(name="peptide")
public class Peptide extends ContextSource{
	
	private String sequence;
	
	private String abbreviatedSequence;
	
	@OneToMany(mappedBy = "peptide")
	private List<SampleComposition> peptide;
	
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
