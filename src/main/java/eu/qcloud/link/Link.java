package eu.qcloud.link;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "link")
public class Link {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "link_increment")
    @SequenceGenerator(name = "link_increment", sequenceName = "link_increment", allocationSize = 1)
    private Long id;

    @Column(name = "api_key", updatable = true, nullable = false, unique = true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;
    
    @Column(name = "url", nullable = false)
    private String url;

    
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getApiKey() {
        return apiKey;
    }

    public void setApiKey(UUID apiKey) {
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Link() {
    }

    public Link(Long id, UUID apiKey, String url, String name) {
        this.id = id;
        this.apiKey = apiKey;
        this.url = url;
        this.name = name;
    }


}