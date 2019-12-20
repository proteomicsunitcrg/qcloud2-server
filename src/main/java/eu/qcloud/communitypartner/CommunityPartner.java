package eu.qcloud.communitypartner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "c_p")
public class CommunityPartner {

    @Id
    @Column(name = "c_p_i")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "c_p_s")
    @SequenceGenerator(name = "c_p_s", sequenceName = "c_p_s", allocationSize = 1)
    private Long id;

    @Column(name = "n")
    private String name;

    @Column(name = "l")
    private String logo;

    @Column(name = "w_p")
    private String webPage;

    @Column(name = "e")
    private String email;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CommunityPartner() {
    }

    public CommunityPartner(Long id, String name, String logo, String webPage, String email) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.webPage = webPage;
        this.email = email;
    }

}