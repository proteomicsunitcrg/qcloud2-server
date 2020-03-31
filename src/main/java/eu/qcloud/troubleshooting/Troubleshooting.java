package eu.qcloud.troubleshooting;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "troubleshooting")
public class Troubleshooting {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "troubleshooting_parent_seq")
    @SequenceGenerator(name = "troubleshooting_parent_seq", sequenceName = "troubleshooting_parent_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "qccv", nullable = false, unique = true)
    private String qccv;

    @Column(name = "apiKey", updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    @org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Troubleshooting parent; 

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Troubleshooting> childs;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private TroubleshootingType type;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQccv() {
        return qccv;
    }

    public void setQccv(String qccv) {
        this.qccv = qccv;
    }

    public UUID getApiKey() {
        return apiKey;
    }

    public void setApiKey(UUID apiKey) {
        this.apiKey = apiKey;
    }

    public List<Troubleshooting> getChilds() {
        return childs;
    }

    public void setChilds(List<Troubleshooting> childs) {
        this.childs = childs;
    }

    public Troubleshooting() {
    }

    public Troubleshooting(Long id, String name, String description, String qccv, UUID apiKey,
            List<Troubleshooting> childs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qccv = qccv;
        this.apiKey = apiKey;
        this.childs = childs;
    }

    public Troubleshooting getParent() {
        return parent;
    }

    public void setParent(Troubleshooting parent) {
        this.parent = parent;
    }

	public TroubleshootingType getType() {
		return type;
	}

	public void setType(TroubleshootingType type) {
		this.type = type;
	}



}
    