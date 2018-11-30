package eu.qcloud.troubleshooting.annotation;

import java.util.Date;
import java.util.List;

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
import eu.qcloud.troubleshooting.problem.Problem;
import eu.qcloud.troubleshooting.reason.Reason;

@Entity
@Table(name="non_conformity_annotation")
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
	List<Reason> reasons;

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

	public List<Reason> getReasons() {
		return reasons;
	}

	public void setReasons(List<Reason> reasons) {
		this.reasons = reasons;
	}
	
}
