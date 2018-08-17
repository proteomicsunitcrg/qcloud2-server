package eu.qcloud.chart;

import java.util.List;
import java.util.UUID;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.Instrument.Instrument;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.view.ViewDisplay;

@Entity
@Table(name="chart")
public class Chart {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "chart_seq")
    @SequenceGenerator(name = "chart_seq", sequenceName = "chart_seq", allocationSize = 1)
	private Long id;
    
    @OneToMany(mappedBy="view")
    private List<ViewDisplay> viewDisplay;
    
    @ManyToOne
	@JoinColumn(name="cvId",insertable=true, updatable= false, nullable=false)
    private Instrument cv;
    
    @ManyToOne
	@JoinColumn(name="sampleTypeId",insertable=true, updatable= false,nullable=false)
    private SampleType sampleType;
    
    @Column(name="name")
    private String name;
    
    @Column(name="is_threshold", columnDefinition="tinyint(1) default 0")
    private boolean isThresholdEnabled;

    @Column(name = "apiKey", updatable = true, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;
    
    @Column(name="is_normalized", columnDefinition="tinyint(1) default 0")
    private boolean isNormalized;
    
    public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

	public List<ViewDisplay> getViewDisplay() {
		return viewDisplay;
	}

	public void setViewDisplay(List<ViewDisplay> viewDisplay) {
		this.viewDisplay = viewDisplay;
	}

	public Chart() {}
	
	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instrument getCv() {
		return cv;
	}

	public void setCv(Instrument cv) {
		this.cv = cv;
	}

	public SampleType getSampleType() {
		return sampleType;
	}

	public void setSampleType(SampleType sampleType) {
		this.sampleType = sampleType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Chart(Long id, Instrument cv, SampleType sampleType, String name, List<ViewDisplay> display) {		
		this.id = id;
		this.cv = cv;
		this.sampleType = sampleType;
		this.name = name;
		this.viewDisplay = display;
	}
	
	public boolean getIsThresholdEnabled() {
		return isThresholdEnabled;
	}

	public void setIsThresholdEnabled(boolean isThresholdEnabled) {
		this.isThresholdEnabled = isThresholdEnabled;
	}

	public boolean isNormalized() {
		return isNormalized;
	}

	public void setNormalized(boolean isNormalized) {
		this.isNormalized = isNormalized;
	}
    
}
