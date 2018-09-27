package eu.qcloud.category;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_seq", allocationSize = 1)
    private Long id;
    
    @Column(name = "NAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;
    
    @Column(name="MAIN", columnDefinition="bit default 0")
    private boolean isMainDataSource;
    
    @Column(name = "apiKey", updatable = false, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;
    
    public boolean isMainDataSource() {
		return isMainDataSource;
	}

	public void setMainDataSource(boolean isMainDataSource) {
		this.isMainDataSource = isMainDataSource;
	}

	public Category() {}
    
	@JsonIgnore
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

	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}
    
}
