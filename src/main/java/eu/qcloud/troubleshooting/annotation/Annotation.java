package eu.qcloud.troubleshooting.annotation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.security.model.User;
import eu.qcloud.troubleshooting.Troubleshooting;

@Entity
@Table(name = "annotation")
public class Annotation {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "annotation_seq")
	@SequenceGenerator(name = "annotation_seq", sequenceName = "annotation_seq", allocationSize = 1)
	private Long id;

	@Column(name = "date")
	private Date date;

	@Column(name = "annotation_date")
	private Date annotationDate;

	@ManyToOne
	@JoinColumn(name = "lab_system_id")
	private LabSystem labSystem;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "api_key", updatable = true, nullable = false, unique = true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDBinaryType")
	private UUID apiKey;

	@Column(name = "description")
	private String description;

	@Column(length = 1000)
	private String note;

	@ManyToMany
	private List<Troubleshooting> troubleshootings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LabSystem getLabSystem() {
		return labSystem;
	}

	public void setLabSystem(LabSystem labSystem) {
		this.labSystem = labSystem;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getAnnotationDate() {
		return annotationDate;
	}

	public void setAnnotationDate(Date annotationDate) {
		this.annotationDate = annotationDate;
	}

	public Annotation() {
	}

	public List<Troubleshooting> getTroubleshootings() {
		return troubleshootings;
	}

	public void setTroubleshootings(List<Troubleshooting> troubleshootings) {
		this.troubleshootings = troubleshootings;
	}

	public Annotation(Long id, Date date, Date annotationDate, LabSystem labSystem, User user, UUID apiKey,
			String description, List<Troubleshooting> troubleshootings) {
		this.id = id;
		this.date = date;
		this.annotationDate = annotationDate;
		this.labSystem = labSystem;
		this.user = user;
		this.apiKey = apiKey;
		this.description = description;
		this.troubleshootings = troubleshootings;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Annotation(Long id, Date date, Date annotationDate, LabSystem labSystem, User user, UUID apiKey,
			String description, String note, List<Troubleshooting> troubleshootings) {
		this.id = id;
		this.date = date;
		this.annotationDate = annotationDate;
		this.labSystem = labSystem;
		this.user = user;
		this.apiKey = apiKey;
		this.description = description;
		this.note = note;
		this.troubleshootings = troubleshootings;
	}


}
