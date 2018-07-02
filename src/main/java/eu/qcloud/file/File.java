package eu.qcloud.file;

import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.sampleType.SampleType;

@Entity
@Table(name="file")
public class File {
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "file_seq")
    @SequenceGenerator(name = "file_seq", sequenceName = "file_seq", allocationSize = 1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="sample_type_id")
	private SampleType sampleType;
		
	@ManyToOne
	@JoinColumn(name="labsystem_id")
	private LabSystem labSystem;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="creation_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name="filename")
	private String filename;
	
	@Column(name="checksum",unique=true)
	private String checksum;

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SampleType getSampleType() {
		return sampleType;
	}

	public void setSampleType(SampleType sampleType) {
		this.sampleType = sampleType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public LabSystem getLabSystem() {
		return labSystem;
	}

	public void setLabSystem(LabSystem labSystem) {
		this.labSystem = labSystem;
	}

	
}
