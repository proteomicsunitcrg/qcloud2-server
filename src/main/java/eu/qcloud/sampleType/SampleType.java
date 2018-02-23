package eu.qcloud.sampleType;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import eu.qcloud.contextSource.peptide.Peptide;

@Entity
@Table(name = "sample_type")
public class SampleType {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sample_seq")
    @SequenceGenerator(name = "sample_seq", sequenceName = "sample_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", length = 50, unique = true)
    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "sample_composition", joinColumns = {
			@JoinColumn(name = "sample_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "peptide_id",
					nullable = false, updatable = false) })
    private List<Peptide> peptides;

    
    public List<Peptide> getPeptides() {
		return peptides;
	}
    
	public void setPeptides(List<Peptide> peptides) {
		this.peptides = peptides;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
