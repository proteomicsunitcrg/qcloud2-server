package eu.qcloud.file;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.sampleType.SampleType;

/**
 * Tracks the full receive/process lifecycle of every raw file the pipeline
 * sees - unlike {@link File}, which only gets a row once a file has been
 * fully processed. One row per checksum, updated in place as its status
 * changes.
 */
@Entity
@Table(name = "pipeline_file")
public class PipeLineFile {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pipeline_file_seq")
	@SequenceGenerator(name = "pipeline_file_seq", sequenceName = "pipeline_file_seq", allocationSize = 1)
	private Long id;

	@Column(name = "checksum", unique = true, nullable = false)
	private String checksum;

	@Column(name = "filename")
	private String filename;

	@ManyToOne
	@JoinColumn(name = "labsystem_id")
	private LabSystem labSystem;

	@ManyToOne
	@JoinColumn(name = "sample_type_id")
	private SampleType sampleType;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private PipelineFileStatus status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "received_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date receivedDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	// Set once, by the pipeline itself right as it starts actually running
	// this file (not by trigger.sh at receive time) - lets the dashboard show
	// pure compute duration, excluding any Slurm queue wait between receiving
	// the file and the pipeline actually starting to process it.
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "processing_started_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date processingStartedDate;

	@Column(name = "sample")
	private String sample;

	@Column(name = "qc_code")
	private String qcCode;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "acquisition_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date acquisitionDate;

	@Column(name = "instrument_uuid")
	private String instrumentUuid;

	@Column(name = "database_name")
	private String databaseName;

	@Column(name = "size_mb")
	private Double sizeMb;

	@Lob
	@Column(name = "error_reason")
	private String errorReason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
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

	public SampleType getSampleType() {
		return sampleType;
	}

	public void setSampleType(SampleType sampleType) {
		this.sampleType = sampleType;
	}

	public PipelineFileStatus getStatus() {
		return status;
	}

	public void setStatus(PipelineFileStatus status) {
		this.status = status;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getProcessingStartedDate() {
		return processingStartedDate;
	}

	public void setProcessingStartedDate(Date processingStartedDate) {
		this.processingStartedDate = processingStartedDate;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getQcCode() {
		return qcCode;
	}

	public void setQcCode(String qcCode) {
		this.qcCode = qcCode;
	}

	public Date getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public String getInstrumentUuid() {
		return instrumentUuid;
	}

	public void setInstrumentUuid(String instrumentUuid) {
		this.instrumentUuid = instrumentUuid;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public Double getSizeMb() {
		return sizeMb;
	}

	public void setSizeMb(Double sizeMb) {
		this.sizeMb = sizeMb;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

}
