package eu.qcloud.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="param")
public class Param {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "param_seq")
    @SequenceGenerator(name = "param_seq", sequenceName = "param_seq", allocationSize = 1)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	private String isFor;
	
	public String getIsFor() {
		return isFor;
	}

	public void setIsFor(String isFor) {
		this.isFor = isFor;
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
	
	
}
