package eu.qcloud.labsystem;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import eu.qcloud.dataSource.DataSource;

/**
 * This class represents a system of LC/MS at this moment
 * CAUTION:
 * It do not stores any data related to the node owner of the system.
 * It is recovered via system_data_sources for avoid redundancy.
 * A system will be saved only if it has some data source attached
 * The way for recover the node systems is doing a query of the 
 * system data sources, check the SystemRepository.findAllByNode() function.
 * @author dmancera
 *
 */
@Entity
@Table(name = "labsystem")
public class LabSystem {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "labsystem_seq")
    @SequenceGenerator(name = "labsystem_seq", sequenceName = "labsystem_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "NAME", length = 50)
    @NotNull    
	private String name;
	
	@ManyToMany
	private List<DataSource> dataSources;
	
	@OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "guide_set")
	private GuideSet guideSet;
	
	@Column(name = "apiKey", updatable = false, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
	private UUID apiKey;
	
	
	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

	public GuideSet getGuideSet() {
		return guideSet;
	}

	public void setGuideSet(GuideSet guideSet) {
		this.guideSet = guideSet;
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

	public List<DataSource> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<DataSource> dataSources) {
		this.dataSources = dataSources;
	}


}
