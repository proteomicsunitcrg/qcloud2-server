package eu.qcloud.sampleType;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.sampleComposition.SampleComposition;
import eu.qcloud.sampleTypeCategory.SampleTypeCategory;

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
    
    @OneToMany(mappedBy="sampleType")
    private List<SampleComposition> sampleType;
    
    @ManyToOne
	@JoinColumn(name="sampleTypeCategory",insertable=true, updatable= true)
    private SampleTypeCategory sampleTypeCategory;

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
	@JsonIgnore
	public SampleTypeCategory getSampleTypeCategory() {
		return sampleTypeCategory;
	}

	public void setSampleTypeCategory(SampleTypeCategory sampleTypeCategory) {
		this.sampleTypeCategory = sampleTypeCategory;
	}
	
	
}
