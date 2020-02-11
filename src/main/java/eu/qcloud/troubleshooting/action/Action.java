package eu.qcloud.troubleshooting.action;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.qcloud.troubleshooting.Troubleshooting;
import eu.qcloud.troubleshooting.troubleshootingParent.TroubleshootingParent;

@Entity
@Table(name = "action")
public class Action {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "action_seq")
    @SequenceGenerator(name = "action_seq", sequenceName = "action_seq", allocationSize = 1)
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

    @Column(name = "is_active", columnDefinition = "BIT default true")
    private boolean active;

    // @ManyToMany
	// private List<TroubleshootingParent> parent;

    public Action() {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Action(Long id, String name, String description, String qccv, UUID apiKey, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qccv = qccv;
        this.apiKey = apiKey;
        this.active = active;
    }

    public Action(String name, String description, String qccv, UUID apiKey) {
		this.name = name;
		this.description = description;
		this.qccv = qccv;
		this.apiKey = apiKey;
	}

    // public List<TroubleshootingParent> getParent() {
    //     return parent;
    // }

    // public void setParent(List<TroubleshootingParent> parent) {
    //     this.parent = parent;
    // }

}
