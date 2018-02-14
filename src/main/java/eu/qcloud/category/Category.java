package eu.qcloud.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    
    @Column(name = "NAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;
    
    public Category() {}
    /*
    @JsonIgnore
    @OneToMany(mappedBy="category",
    		cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    private List<CV> cvs;    
    
    public Category() {}
	
    public List<CV> getCvs() {
		return cvs;
	}
	

	public void setCvs(List<CV> cvs) {
		this.cvs = cvs;
	}
	*/
	
	public Long getId() {
		return id;
	}

	public Category(Long id, String name) {		
		this.id = id;
		this.name = name;
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
    
    
}
