package eu.qcloud.troubleshooting;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="troubleshooting")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Troubleshooting {
	
	@JsonIgnore
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "troubleshooting_seq")
    @SequenceGenerator(name = "troubleshooting_seq", sequenceName = "troubleshooting_seq", allocationSize = 1)
	private Long id;
	
	@Column(name= "name", nullable= false)
	private String name;
	
	@Column(name= "description", nullable= true)
	private String description;
	
	@Column(name= "qccv", nullable= false, unique= true)
	private String qccv;
	
	@Column(name = "apiKey", updatable = false, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
	private UUID apiKey;
	
	public Troubleshooting() {}
	
	public Troubleshooting(String name, String description, String qccv, UUID apiKey) {
		this.name = name;
		this.description = description;
		this.qccv = qccv;
		this.apiKey = apiKey;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQccv() {
		return qccv;
	}

	public void setQccv(String qccv) {
		this.qccv = qccv;
	}

	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}
}
