package eu.qcloud.troubleshooting.annotation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.qcloud.troubleshooting.action.Action;
import eu.qcloud.troubleshooting.cause.Cause;
import eu.qcloud.troubleshooting.problem.Problem;

@Entity
@Table(name="annotation")
public class Annotation {
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "annotation_seq")
    @SequenceGenerator(name = "annotation_seq", sequenceName = "annotation_seq", allocationSize = 1)
	private Long id;
	
	private Date date;
	@ManyToMany(cascade = {CascadeType.PERSIST})
	List<Problem> problems;
	
	@ManyToMany(cascade = {CascadeType.PERSIST})
	List<Action> actions;
	
	@ManyToMany(cascade = {CascadeType.PERSIST})
	List<Cause> reasons;
	
	@Column(name = "apiKey", updatable = false, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
	private UUID apiKey;
	
	private String description;

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

	public List<Problem> getProblems() {
		return problems;
	}

	public void setProblems(List<Problem> problems) {
		this.problems = problems;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public List<Cause> getReasons() {
		return reasons;
	}

	public void setReasons(List<Cause> reasons) {
		this.reasons = reasons;
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
	
}
