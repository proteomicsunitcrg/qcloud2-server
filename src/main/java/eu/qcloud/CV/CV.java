package eu.qcloud.CV;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.category.Category;
import eu.qcloud.dataSource.DataSource;

@Entity
@Table(name = "cv")
public class CV {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", length = 50, unique = true)
    @NotNull    
    private String name;
    
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})    
    @JoinColumn(name="category_id")    
    private Category category;
    
    @Lob
    @Column(name="definition")
    private String definition;
    
    @Column(name="cv_id")
    private String CVId;
    
    @Column(name="enabled")
    private boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy="cv")
    private List<DataSource> dataSource;
    
    public List<DataSource> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<DataSource> dataSource) {
		this.dataSource = dataSource;
	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public CV() {}
    
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getCVId() {
		return CVId;
	}

	public void setCVId(String cVId) {
		CVId = cVId;
	}    
    
}
