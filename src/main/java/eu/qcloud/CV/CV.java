package eu.qcloud.CV;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import eu.qcloud.category.Category;

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
    @Size(min = 4, max = 50)
    private String name;
    
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})    
    @JoinColumn(name="category_id")    
    private Category category;

/*    
    @OneToMany(mappedBy="cv")
    private List<DataSource> dataSource;
    
    public List<DataSource> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<DataSource> dataSource) {
		this.dataSource = dataSource;
	}
	*/

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
    
}
