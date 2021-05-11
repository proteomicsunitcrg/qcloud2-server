package eu.qcloud.message;

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
@Table(name = "message")
public class Message {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "msg_increment")
    @SequenceGenerator(name = "msg_increment", sequenceName = "msg_increment", allocationSize = 1)
    private Long id;

    @Column(name = "Title")
    @NotBlank
    private String title;

    @Column(name = "Message")
    @NotBlank
    private String message;

    @NotNull
    @Column(name = "Show_msg", columnDefinition = "BIT")
    private boolean show;

    @NotNull
    @Column(name = "type")
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "creation_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

    @Column(name = "priority")
    private Integer priority;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean getShow() {
        return this.show;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Message [creationDate=" + creationDate + ", id=" + id + ", message=" + message + ", message_type="
                + type + ", show=" + show + ", title=" + title + "]";
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Message(Long id, @NotBlank String title, @NotBlank String message, @NotNull boolean show,
            @NotNull String message_type, Date creationDate, @NotNull Integer priority) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.show = show;
        this.type = message_type;
        this.creationDate = creationDate;
        this.priority = priority;
    }

    public Message() {
    }

}
