package eu.qcloud.message;

import javax.persistence.Column;
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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "msg_increment")
    @SequenceGenerator(name = "msg_increment", sequenceName = "msg_increment", allocationSize = 1)
    private Long id;

    @Column(name = "Title")
    @NotBlank
    private String title;

    @Column(name = "Message")
    @NotBlank
    private String message;
    // wrong column type encountered in column [show_msg] in table [message]; found [bit (Types#BIT)], but expecting [boolean (Types#BOOLEAN)]
    @NotNull
    @Column(name = "Show_msg", columnDefinition = "BIT")
    private boolean show;

    @NotNull
    @Column(name = "Message_type")
    private String message_type;

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
    public void setType(String message_type){
        this.message_type = message_type;
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
    public String getMessageType() {
        return this.message_type;
    }




}
