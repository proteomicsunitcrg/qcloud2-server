package eu.qcloud.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.qcloud.data.ProcessorType;

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
	
	@Column(name="processor",nullable= true)	
	private ProcessorType processor;
	
	@Column(name="QCCV")
	private String qCCV;
	
	public String getqCCV() {
		return qCCV;
	}

	public void setqCCV(String qCCV) {
		this.qCCV = qCCV;
	}

	public ProcessorType getProcessor() {
		return processor;
	}

	public void setProcessor(ProcessorType processor) {
		this.processor = processor;
	}

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
