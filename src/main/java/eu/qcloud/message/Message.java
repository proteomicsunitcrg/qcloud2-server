package eu.qcloud.message;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Title")
    @NotBlank
    private String title;

    @Column(name = "Message")
    @NotBlank
    private String message;

    @NotNull
    @Column(name = "Show", columnDefinition = "BOOLEAN")
    private boolean show;

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




}
