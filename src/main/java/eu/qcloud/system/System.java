package eu.qcloud.system;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "system")
public class System {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system_seq")
    @SequenceGenerator(name = "system_seq", sequenceName = "system_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "NAME", length = 50)
    @NotNull    
	private String name;
	
	@ManyToMany
	private List<DataSource> dataSources;

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
