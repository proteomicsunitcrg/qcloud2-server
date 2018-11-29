package eu.qcloud.nonconformity.problem;

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

import eu.qcloud.nonconformity.action.Action;
import eu.qcloud.nonconformity.reason.Reason;

@Entity
@Table(name="non_conformity_problem")
public class Problem {
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "problem_seq")
    @SequenceGenerator(name = "problem_seq", sequenceName = "problem_seq", allocationSize = 1)
	private Long id;
	
	private String name;
	
	private String description;
	
	@ManyToMany(cascade = {CascadeType.PERSIST})
	List<Reason> reasons;
	
	@ManyToMany(cascade = {CascadeType.PERSIST})
	List<Action> actions;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Reason> getReasons() {
		return reasons;
	}

	public void setReasons(List<Reason> reasons) {
		this.reasons = reasons;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

}
