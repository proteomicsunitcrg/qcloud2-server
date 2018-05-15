package eu.qcloud.chart;

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

import eu.qcloud.CV.CV;
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
    private CV cv;
    
    @ManyToOne
	@JoinColumn(name="sampleTypeId",insertable=true, updatable= false,nullable=false)
    private SampleType sampleType;
    
    @Column(name="name")
    private String name;
    
    @Column(name="is_threshold", columnDefinition="tinyint(1) default 0")
    private boolean isThresholdEnabled;

	/*
     * 3/5/18 i've commented this and still works with the
     * current database. If a new database is used mind this
     * line comment
     */
    //@OneToMany(mappedBy="chart")
    public List<ViewDisplay> getViewDisplay() {
		return viewDisplay;
	}

	public void setViewDisplay(List<ViewDisplay> viewDisplay) {
		this.viewDisplay = viewDisplay;
	}

	public Chart() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CV getCv() {
		return cv;
	}

	public void setCv(CV cv) {
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

	public Chart(Long id, CV cv, SampleType sampleType, String name, List<ViewDisplay> display) {		
		this.id = id;
		this.cv = cv;
		this.sampleType = sampleType;
		this.name = name;
		this.viewDisplay = display;
	}
	
	public boolean isThresholdEnabled() {
		return isThresholdEnabled;
	}

	public void setThresholdEnabled(boolean isThresholdEnabled) {
		this.isThresholdEnabled = isThresholdEnabled;
	}
    
    
    
    
}
