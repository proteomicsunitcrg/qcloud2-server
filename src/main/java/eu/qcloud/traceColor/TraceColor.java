package eu.qcloud.traceColor;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class TraceColor {
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "trace_color_seq")
    @SequenceGenerator(name = "trace_color_seq", sequenceName = "trace_color_seq", allocationSize = 1)
	private Long id;
	
	private String mainColor;
	
	@Column(name = "apiKey", updatable = false, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
	private UUID apiKey;
	
	public TraceColor() {}

	public TraceColor(String mainColor, UUID apiKey) {
		this.mainColor = mainColor;
		this.apiKey = apiKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMainColor() {
		return mainColor;
	}

	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}

	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

}
