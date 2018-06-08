package eu.qcloud.contextSource.peptide.isotopologue;

import eu.qcloud.contextSource.peptide.Peptide;

// @Entity(name="isotopologue")
public class Isotopologue extends Peptide {
	
	// @ManyToOne
	// @JoinColumn(name="peptideId",insertable=true, updatable= true)
	private Peptide mainPeptide;
	
	// @Column(name="concentration")
	private Float concentration;
	
	public Float getConcentration() {
		return concentration;
	}

	public void setConcentration(Float concentration) {
		this.concentration = concentration;
	}

	public Peptide getMainPeptide() {
		return mainPeptide;
	}

	public void setMainPeptide(Peptide mainPeptide) {
		this.mainPeptide = mainPeptide;
	}
	
}
