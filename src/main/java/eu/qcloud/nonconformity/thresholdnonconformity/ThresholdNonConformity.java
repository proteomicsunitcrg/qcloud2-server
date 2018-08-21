package eu.qcloud.nonconformity.thresholdnonconformity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.file.File;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.threshold.InstrumentStatus;
import eu.qcloud.threshold.Threshold;

/**
 * This class holds the non conformity.
 * @author dmancera
 *
 */
@Entity
@Table(name="threshold_non_conformity")
public class ThresholdNonConformity {
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "threshold_non_conformity_seq")
    @SequenceGenerator(name = "threshold_non_conformity_seq", allocationSize = 1)
	private Long id;
	
	@ManyToOne(optional= false)
	private File file;
	
	@ManyToOne(optional= false)
	private ContextSource contextSource;
	
	@ManyToOne(optional= false)
	private Threshold threshold;
	
	@ManyToOne(optional= true)
	private GuideSet guideSet;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private InstrumentStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ContextSource getContextSource() {
		return contextSource;
	}

	public void setContextSource(ContextSource contextSource) {
		this.contextSource = contextSource;
	}

	@JsonIgnore
	public Threshold getThreshold() {
		return threshold;
	}

	public void setThreshold(Threshold threshold) {
		this.threshold = threshold;
	}

	public GuideSet getGuideSet() {
		return guideSet;
	}

	public void setGuideSet(GuideSet guideSet) {
		this.guideSet = guideSet;
	}

	public InstrumentStatus getStatus() {
		return status;
	}

	public void setStatus(InstrumentStatus status) {
		this.status = status;
	}
	
}
