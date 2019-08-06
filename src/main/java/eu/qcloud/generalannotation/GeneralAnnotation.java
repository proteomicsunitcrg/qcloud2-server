package eu.qcloud.generalannotation;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "general_annotation")
public class GeneralAnnotation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "general_annotation_seq")
    @SequenceGenerator(name = "general_annotation_seq", sequenceName = "general_annotation_seq", allocationSize = 1)
    private Long id;

    @Column(name = "description")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date", columnDefinition = "DATE")
    @NotNull
    private Date date;

    @Column(name = "apiKey", updatable = true, nullable = false, unique = true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;
    
    @Column(name = "is_active", columnDefinition = "BIT")
	private boolean active;


    public GeneralAnnotation() {
    }

    public GeneralAnnotation(Long id, String description, @NotNull @NotBlank Date date, UUID apiKey) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.apiKey = apiKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public GeneralAnnotation(Long id, String description, @NotNull @NotBlank Date date, UUID apiKey, boolean active) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.apiKey = apiKey;
        this.active = active;
    }


    

}