package eu.qcloud.contextSource.peptide;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.sampleComposition.SampleComposition;

@Entity(name="peptide")
public class Peptide extends ContextSource{
	
	private String sequence;
	
	// private String abbreviatedSequence;
	
	private Float mz;
	
	private Integer charge;
	
	@OneToMany(mappedBy = "peptide")
	private List<SampleComposition> peptide;
	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	/*
	public String getAbbreviatedSequence() {
		return abbreviatedSequence;
	}

	public void setAbbreviatedSequence(String abbreviatedSequence) {
		this.abbreviatedSequence = abbreviatedSequence;		
	}
	*/

	public Float getMz() {
		return mz;
	}

	public void setMz(Float mz) {
		this.mz = mz;
	}

	public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}
	
	
	
}
