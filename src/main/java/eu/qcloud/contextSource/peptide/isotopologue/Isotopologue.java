package eu.qcloud.contextSource.peptide.isotopologue;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import eu.qcloud.contextSource.peptide.Peptide;

@Entity(name="isotopologue")
public class Isotopologue extends Peptide {
	
	@ManyToOne
	@JoinColumn(name="peptideId",insertable=true, updatable= true)
	private Peptide mainPeptide;

	public Peptide getMainPeptide() {
		return mainPeptide;
	}

	public void setMainPeptide(Peptide mainPeptide) {
		this.mainPeptide = mainPeptide;
	}
	
}
