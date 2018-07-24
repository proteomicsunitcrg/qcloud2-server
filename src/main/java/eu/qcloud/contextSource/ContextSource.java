package eu.qcloud.contextSource;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="context_source")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ContextSource {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	protected Long id;
	
	@Column(unique = true)
	protected String name;
	
	protected String abbreviated;
	
	@Column(name = "apiKey", updatable = true, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;

	public String getAbbreviated() {
		return abbreviated;
	}

	public void setAbbreviated(String abbreviated) {
		this.abbreviated = abbreviated;
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

	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}
}
