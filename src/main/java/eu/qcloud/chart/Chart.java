package eu.qcloud.chart;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.qcloud.CV.CV;
import eu.qcloud.sampleType.SampleType;

@Entity
@Table(name="chart")
public class Chart {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "chart_seq")
    @SequenceGenerator(name = "chart_seq", sequenceName = "chart_seq", allocationSize = 1)
	private Long id;
    
    
    @ManyToOne
	@JoinColumn(name="cvId",insertable=true, updatable= false)
    private CV cv;
    
    @ManyToOne
	@JoinColumn(name="sampleTypeId",insertable=true, updatable= false)
    private SampleType sampleType;
    
    @Column(name="name")
    private String name;
    
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

	public Chart(Long id, CV cv, SampleType sampleType, String name) {		
		this.id = id;
		this.cv = cv;
		this.sampleType = sampleType;
		this.name = name;
	}
    
    
    
    
}
