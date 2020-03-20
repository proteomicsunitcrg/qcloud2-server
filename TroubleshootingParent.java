package eu.qcloud.troubleshooting.troubleshootingParent;

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

import eu.qcloud.troubleshooting.action.Action;
import eu.qcloud.troubleshooting.problem.Problem;

@Entity
@Table(name = "troubleshooting_parent")
public class TroubleshootingParent {

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
    
    // @Column(name = "actions", nullable = true)
    @ManyToMany
    List<Action> action;

    @ManyToMany
    List<Problem> problem;


    public TroubleshootingParent() {
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

    public TroubleshootingParent(Long id, String name, String description, String qccv, UUID apiKey) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qccv = qccv;
        this.apiKey = apiKey;
    }

    public TroubleshootingParent(String name, String description, String qccv, UUID apiKey) {
		this.name = name;
		this.description = description;
		this.qccv = qccv;
		this.apiKey = apiKey;
	}

    public List<Action> getAction() {
        return action;
    }

    public void setAction(List<Action> action) {
        this.action = action;
    }

    public List<Problem> getProblem() {
        return problem;
    }

    public void setProblem(List<Problem> problem) {
        this.problem = problem;
    }

}
