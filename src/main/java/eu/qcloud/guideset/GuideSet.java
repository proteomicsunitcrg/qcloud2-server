package eu.qcloud.guideset;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.sampleType.SampleType;
@Entity
@Table(name="guide_set")
public class GuideSet {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "guide_set_seq")
    @SequenceGenerator(name = "guide_set_seq", sequenceName = "guide_set_seq", allocationSize = 1)
	private Long id;
	
	@JsonFormat(pattern="yyyy-MM-dd hh:mm")
	@Column(name="start_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@JsonFormat(pattern="yyyy-MM-dd hh:mm")
	@Column(name="end_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@Column(name = "apiKey", updatable = true, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;
	
	@Transient
	private long totalFiles;
	
	@Transient
	private long labSystemTotalFiles;

	@ManyToOne
	@JoinColumn(name="sample_type_id")
	private SampleType sampleType;
	
	@Column(name="is_active", columnDefinition="tinyint(1) default 0")
	private Boolean isActive;
	
	@Column(name="is_user_defined", columnDefinition="tinyint(1) default 1")
	private Boolean isUserDefined;
	
	public Boolean getIsUserDefined() {
		return isUserDefined;
	}

	public void setIsUserDefined(Boolean isUserDefined) {
		this.isUserDefined = isUserDefined;
	}

	public GuideSet() {}

	public GuideSet(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public SampleType getSampleType() {
		return sampleType;
	}

	public void setSampleType(SampleType sampleType) {
		this.sampleType = sampleType;
	}
	
	public long getTotalFiles() {
		return totalFiles;
	}

	public void setTotalFiles(long totalFiles) {
		this.totalFiles = totalFiles;
	}
	
	public long getLabSystemTotalFiles() {
		return labSystemTotalFiles;
	}

	public void setLabSystemTotalFiles(long labSystemTotalFiles) {
		this.labSystemTotalFiles = labSystemTotalFiles;
	}
	
	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GuideSet other = (GuideSet) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
	
	
	
}
