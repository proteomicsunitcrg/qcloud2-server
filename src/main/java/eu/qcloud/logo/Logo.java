package eu.qcloud.logo;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "logo")
public class Logo {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "msg_increment")
    @SequenceGenerator(name = "msg_increment", sequenceName = "msg_increment", allocationSize = 1)
    private Long id;

    @Column(name = "api_key", updatable = true, nullable = false, unique = true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;
    
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "alt")
    private String alt;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active", columnDefinition = "BIT")
    private boolean active;

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

    public Logo() {
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Logo(Long id, UUID apiKey, String url, String alt, String name, boolean active) {
        this.id = id;
        this.apiKey = apiKey;
        this.url = url;
        this.alt = alt;
        this.name = name;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Logo [active=" + active + ", alt=" + alt + ", apiKey=" + apiKey + ", id=" + id + ", name=" + name
                + ", url=" + url + "]";
    }

}