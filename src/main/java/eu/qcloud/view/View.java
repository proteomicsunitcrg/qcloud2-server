package eu.qcloud.view;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.Instrument.Instrument;
import eu.qcloud.sampleTypeCategory.SampleTypeCategory;
import eu.qcloud.security.model.User;
/**
 * This is the view class representing both default views
 * and user views.
 * @author dmancera
 *
 */
@Entity
@Table(name="view", uniqueConstraints= {
		@UniqueConstraint(columnNames= {"cvId","sampleTypeCategoryId"})
})
public class View {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "view_seq")
    @SequenceGenerator(name = "view_seq", sequenceName = "view_seq", allocationSize = 1)
	private Long id;
	
	private String name;
	
	@Column(name ="is_default", nullable= false, columnDefinition="bit default 1")
	private boolean isDefault;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="userId",insertable=true, updatable= false,nullable=true)
	private User user;
	
	@OneToOne
	@JoinColumn(name="cvId",insertable=true, updatable= false,nullable=true,unique=false)
	private Instrument cv;
	
	@OneToMany(mappedBy="view")
	private List<ViewDisplay> viewDisplay;
	
	@ManyToOne
	@JoinColumn(name="sampleTypeCategoryId", insertable=true, updatable=false, nullable=true, unique = false)
	private SampleTypeCategory sampleTypeCategory;
	
	@Column(name = "apiKey", updatable = false, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
	private UUID apiKey;
	
	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

	public SampleTypeCategory getSampleTypeCategory() {
		return sampleTypeCategory;
	}

	public void setSampleTypeCategory(SampleTypeCategory sampleTypeCategory) {
		this.sampleTypeCategory = sampleTypeCategory;
	}

	public Instrument getCv() {
		return cv;
	}

	public void setCv(Instrument cv) {
		this.cv = cv;
	}
	
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	//@JsonIgnore
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
	
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
