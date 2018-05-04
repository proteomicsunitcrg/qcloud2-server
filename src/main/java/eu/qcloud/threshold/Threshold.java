package eu.qcloud.threshold;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import eu.qcloud.CV.CV;
import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.param.Param;
import eu.qcloud.sampleType.SampleType;
/**
 * This class represents a threshold. There are two ENUMS used
 * to hold what direction has the threshold and what type of threshold
 * it is.
 * The unique contraint of the table will prevent a user to have 
 * more than one threshold at one instrument per sample type and param.
 * Lab system can be null in order to save the DEFAULT threshold in case
 * of the MANUAL type of threshold has not user values yet.
 * @author dmancera
 *
 */
@Entity
@Table(name="threshold",
	uniqueConstraints= {@UniqueConstraint(columnNames= {"sample_type_id","param_id","cv_id","lab_system_id"})})
public class Threshold {
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "threshold_seq")
    @SequenceGenerator(name = "threshold_seq", sequenceName = "threshold_seq", allocationSize = 1)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="direction",nullable= true)
	private Direction direction;
	
	@Column(name="type",nullable= true)
	private ThresholdType thresholdType;
	
	@Column(name="steps")
	private int steps;
	
	@Column(name="step_value")
	private float stepValue;
	
	@ManyToOne
	@JoinColumn(name="sample_type_id", nullable= false)
	private SampleType sampleType;
	
	@ManyToOne
	@JoinColumn(name="param_id", nullable= false)
	private Param param;
	
	@ManyToOne
	@JoinColumn(name="cv_id", nullable= false)
	private CV cv;
	
	@ManyToMany	
	@JoinTable(name="threshold_params",uniqueConstraints= {@UniqueConstraint(columnNames= {"threshold_id","context_sources_id"})})
	private List<ContextSource> contextSources;
	
	@OneToOne(optional = true)
	@JoinColumn(name="lab_system_id")
	private LabSystem labSystem;
	
	public LabSystem getLabSystem() {
		return labSystem;
	}

	public void setLabSystem(LabSystem labSystem) {
		this.labSystem = labSystem;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public CV getCv() {
		return cv;
	}

	public void setCv(CV cv) {
		this.cv = cv;
	}

	public List<ContextSource> getContextSources() {
		return contextSources;
	}

	public void setContextSources(List<ContextSource> contextSources) {
		this.contextSources = contextSources;
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

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public float getStepValue() {
		return stepValue;
	}

	public void setStepValue(float stepValue) {
		this.stepValue = stepValue;
	}

	public SampleType getSampleType() {
		return sampleType;
	}

	public void setSampleType(SampleType sampleType) {
		this.sampleType = sampleType;
	}

	public ThresholdType getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(ThresholdType thresholdType) {
		this.thresholdType = thresholdType;
	}
	
}
