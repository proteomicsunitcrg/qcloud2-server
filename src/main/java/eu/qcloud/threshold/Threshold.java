package eu.qcloud.threshold;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.Instrument.Instrument;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.param.Param;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.params.ThresholdParams;
/**
 * This class represents a threshold. There are two ENUMS used
 * to hold what direction has the threshold and what type of threshold
 * it is.
 * The unique constraint of the table will prevent a user to have 
 * more than one threshold at one instrument per sample type and param.
 * Lab system can be null in order to save the DEFAULT threshold in case
 * of the MANUAL type of threshold has not user values yet.
 * @author dmancera
 *
 */
import eu.qcloud.threshold.processor.ThresholdProcessor;
@Entity
@Table(name="threshold")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Threshold implements Cloneable {
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "threshold_seq")
    @SequenceGenerator(name = "threshold_seq", sequenceName = "threshold_seq", allocationSize = 1)
	protected Long id;
	
	@Column(name="name")
	protected String name;
	
	@Column(name="is_monitored", columnDefinition="bit default 1")
    private boolean isMonitored;
	
	@Column(name="is_enabled", columnDefinition="bit default 1")
    private boolean isEnabled;
	
    @Column(name = "apiKey", updatable = true, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;
    
    @Enumerated(EnumType.STRING)
	@Column(name="non_conformity_direction", columnDefinition="varchar(255)")
    private Direction nonConformityDirection;
	
	@Transient
	protected ThresholdConstraint adminThresholdConstraint;
	
	@Transient
	protected ThresholdConstraint managerThresholdConstraint;
	
	@Transient
	protected Direction direction;
	
	@Transient
	protected ThresholdType thresholdType;
	
	@Transient
	protected ThresholdProcessor processor;	
	
	@ManyToOne
	@JoinColumn(name="sample_type_id", nullable= false)
	protected SampleType sampleType;
	
	@ManyToOne
	@JoinColumn(name="param_id", nullable= false)
	protected Param param;
	
	@ManyToOne
	@JoinColumn(name="cv_id", nullable= false)
	protected Instrument instrument;
	
	@Column(name="steps")
	protected int steps;
	
	@OneToOne(optional = true)
	@JoinColumn(name="lab_system_id")
	protected LabSystem labSystem;
	
	@OneToMany(mappedBy="threshold")
	protected List<ThresholdParams> thresholdParams;

	@Column(name = "is_comm_feat", columnDefinition = "bit default 0")
	private boolean isCommFeat;
	
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

	public Instrument getCv() {
		return instrument;
	}

	public void setCv(Instrument cv) {
		this.instrument = cv;
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

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public List<ThresholdParams> getThresholdParams() {
		return thresholdParams;
	}

	public void setThresholdParams(List<ThresholdParams> thresholdParams) {
		this.thresholdParams = thresholdParams;
	}
	@JsonIgnore
	public ThresholdProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(ThresholdProcessor processor) {
		this.processor = processor;
	}

	public ThresholdConstraint getAdminThresholdConstraint() {
		return adminThresholdConstraint;
	}

	public void setAdminThresholdConstraint(ThresholdConstraint adminThresholdConstraint) {
		this.adminThresholdConstraint = adminThresholdConstraint;
	}

	public ThresholdConstraint getManagerThresholdConstraint() {
		return managerThresholdConstraint;
	}

	public void setManagerThresholdConstraint(ThresholdConstraint managerThresholdConstraint) {
		this.managerThresholdConstraint = managerThresholdConstraint;
	}

	public boolean isMonitored() {
		return isMonitored;
	}

	public void setMonitored(boolean isMonitored) {
		this.isMonitored = isMonitored;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

//	public boolean getIsZeroNoData() {
//		return isZeroNoData;
//	}
//
//	public void setIsZeroNoData(boolean isZeroNoData) {
//		this.isZeroNoData = isZeroNoData;
//	}

	public Direction getNonConformityDirection() {
		return nonConformityDirection;
	}

	public void setNonConformityDirection(Direction nonConformityDirection) {
		this.nonConformityDirection = nonConformityDirection;
	}
	
	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}
	@Override
	public Threshold clone() {
		try {
			return (Threshold)super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Could not clone");

		}
		return null;
	}

	public boolean isCommFeat() {
		return isCommFeat;
	}

	public void setCommFeat(boolean isCommFeat) {
		this.isCommFeat = isCommFeat;
	}

	@Override
	public String toString() {
		return "Threshold [adminThresholdConstraint=" + adminThresholdConstraint + ", apiKey=" + apiKey + ", direction="
				+ direction + ", id=" + id + ", instrument=" + instrument + ", isCommFeat=" + isCommFeat
				+ ", isEnabled=" + isEnabled + ", isMonitored=" + isMonitored + ", labSystem=" + labSystem
				+ ", managerThresholdConstraint=" + managerThresholdConstraint + ", name=" + name
				+ ", nonConformityDirection=" + nonConformityDirection + ", param=" + param + ", processor=" + processor
				+ ", sampleType=" + sampleType + ", steps=" + steps + ", thresholdParams=" + thresholdParams
				+ ", thresholdType=" + thresholdType + "]";
	}

	
}
