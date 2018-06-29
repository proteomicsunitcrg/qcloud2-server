package eu.qcloud.sampleTypeCategory;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.sampleType.SampleType;

@Entity
@Table(name = "sample_type_category")
public class SampleTypeCategory {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sample_category_seq")
    @SequenceGenerator(name = "sample_category_seq", sequenceName = "sample_category_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", length = 50, unique = true)
    @NotNull    
    @Size(min = 3, max = 50)
    private String name;
    
    @Column(name= "COMPLEXITY")
    private SampleTypeComplexity sampleTypeComplexity;
    
    @OneToMany(mappedBy="sampleTypeCategory")
    private List<SampleType> sampleTypes;
    
    @Column(name = "apiKey", updatable = true, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;
    
	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}
	
	@JsonIgnore
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

	public List<SampleType> getSampleTypes() {
		return sampleTypes;
	}

	public void setSampleTypes(List<SampleType> sampleTypes) {
		this.sampleTypes = sampleTypes;
	}
	
	public SampleTypeComplexity getSampleTypeComplexity() {
		return sampleTypeComplexity;
	}

	public void setSampleTypeComplexity(SampleTypeComplexity sampleTypeComplexity) {
		this.sampleTypeComplexity = sampleTypeComplexity;
	}
	

}
