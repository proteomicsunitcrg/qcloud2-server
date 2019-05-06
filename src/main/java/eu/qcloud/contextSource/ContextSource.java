package eu.qcloud.contextSource;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import eu.qcloud.traceColor.TraceColor;

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
	
	@ManyToOne
	@JoinColumn(name="trace_color_id")
	private TraceColor traceColor;
	
	private int shadeGrade;

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

	public TraceColor getTraceColor() {
		return traceColor;
	}

	public void setTraceColor(TraceColor traceColor) {
		this.traceColor = traceColor;
	}

	public int getShadeGrade() {
		return shadeGrade;
	}

	public void setShadeGrade(int shadeGrade) {
		this.shadeGrade = shadeGrade;
	}
	
}
