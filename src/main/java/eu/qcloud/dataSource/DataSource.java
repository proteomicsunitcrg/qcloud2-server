package eu.qcloud.dataSource;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.CV.CV;
import eu.qcloud.node.Node;

@Entity
@Table(name="data_source")
public class DataSource {
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "node_seq")
    @SequenceGenerator(name = "node_seq", sequenceName = "node_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "NAME", length = 50, unique = false)	
	private String name;
	
	@Column(name = "apiKey", updatable = false, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
	private UUID apiKey;
	
	@Column(name="enabled",columnDefinition="tinyint(1) default 1")
	private boolean enabled;
	
	@ManyToOne
	@JoinColumn(name="cv_id")
	private CV cv;
	
	@ManyToOne
	@JoinColumn(name="node_id")
	private Node node;
	
	
	@JsonIgnore
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
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

	public CV getCv() {
		return cv;
	}

	public void setCv(CV cv) {
		this.cv = cv;
	}
	
	public UUID getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "DataSource [id=" + id + ", name=" + name + ", cv=" + cv + ", node=" + node + "]";
	}
	
	
}
