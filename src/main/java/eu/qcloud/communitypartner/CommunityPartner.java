package eu.qcloud.communitypartner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "community_partner")
public class CommunityPartner {

    @Id
    @Column(name = "community_partner_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "community_partner_seq")
    @SequenceGenerator(name = "community_partner_seq", sequenceName = "community_partner_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "logo")
    private String logo;

    @Column(name = "web_page")
    private String webPage;

    @Column(name = "email")
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