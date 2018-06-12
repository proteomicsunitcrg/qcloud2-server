package eu.qcloud.sampleComposition;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.sampleType.SampleType;

@Entity
@Table(name="sample_composition")
public class SampleComposition {
	
	@EmbeddedId
	private SampleCompositionId sampleCompositionId;
	
	@ManyToOne
	@JoinColumn(name="sampleTypeId",insertable=false, updatable= false)
	private SampleType sampleType;
	
	@ManyToOne
	@JoinColumn(name="peptideId",insertable=false, updatable= false)
	private Peptide peptide;
	
	private Float concentration;
	
	public SampleCompositionId getSampleCompositionId() {
		return sampleCompositionId;
	}
	public void setSampleCompositionId(SampleCompositionId sampleCompositionId) {
		this.sampleCompositionId = sampleCompositionId;
	}
	public Float getConcentration() {
		return concentration;
	}
	public void setConcentration(Float concentration) {
		this.concentration = concentration;
	}
	public SampleType getSampleType() {
		return sampleType;
	}
	public void setSampleType(SampleType sampleType) {
		this.sampleType = sampleType;
	}
	public Peptide getPeptide() {
		return peptide;
	}
	public void setPeptide(Peptide peptide) {
		this.peptide = peptide;
	}
	
	
	
}
