package eu.qcloud.tip;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tip")
public class Tip {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tip_increment")
    @SequenceGenerator(name = "tip_increment", sequenceName = "tip_increment", allocationSize = 1)
    private Long id;

    @Column(name = "Title")
    @NotBlank
    private String title;

    @Column(name = "Message")
    @NotBlank
    private String message;

    @JsonFormat()
    @Column(name = "showAt", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date showAt;

    @NotNull
    @Column(name = "display", columnDefinition = "BIT")
    private boolean display;


    @NotNull
    @Column(name = "published_twitter", columnDefinition = "BIT")
    private boolean publishedTwitter;

    public Tip() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getShowAt() {
        return showAt;
    }

    public void setShowAt(Date showAt) {
        this.showAt = showAt;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isPublishedTwitter() {
        return publishedTwitter;
    }

    public void setPublishedTwitter(boolean publishedTwitter) {
        this.publishedTwitter = publishedTwitter;
    }

    @Override
    public String toString() {
        return "Tip [display=" + display + ", id=" + id + ", message=" + message + ", showAt=" + showAt + ", title="
                + title + "]";
    }


}
