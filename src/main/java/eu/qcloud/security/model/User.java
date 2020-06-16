package eu.qcloud.security.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.node.Node;
import eu.qcloud.userDefaultView.UserDefaultView;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "apiKey", updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID apiKey;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_default_view_id")
    private UserDefaultView userDefaultView;

    @Column(name = "USERNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "FIRSTNAME", length = 50)
    @NotNull
    private String firstname;

    @Column(name = "LASTNAME", length = 50)
    @NotNull
    private String lastname;

    @Column(name = "EMAIL", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "telegram_code", length = 255)
    private String telegram_code;

    @Column(name = "telegram_chat_id")
    private Long telegramChatId;

    @Column(name = "spam", columnDefinition = "BIT default true", nullable = false)
    private boolean spam;

    @Column(name = "last_qcrawler_login_date")
    private Date lastQcrawlerLoginDate;

    @Column(name = "last_qcloud_login_date")
    private Date lastQCloudLoginDate;

    @Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_AUTHORITY", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
                    @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID") })
    private List<Authority> authorities;

    @ManyToOne
    @JoinColumn(name = "node_id")
    private Node node;

    @JsonIgnore
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public UUID getApiKey() {
        return apiKey;
    }

    public void setApiKey(UUID apiKey) {
        this.apiKey = apiKey;
    }

    public UserDefaultView getUserDefaultView() {
        return userDefaultView;
    }

    public void setUserDefaultView(UserDefaultView userDefaultView) {
        this.userDefaultView = userDefaultView;
    }

    public String getTelegram_code() {
        return telegram_code;
    }

    public void setTelegram_code(String telegram_code) {
        this.telegram_code = telegram_code;
    }

    public Long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(Long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public boolean isSpam() {
        return spam;
    }

    public void setSpam(boolean spam) {
        this.spam = spam;
    }

    public Date getLastQcrawlerLoginDate() {
        return lastQcrawlerLoginDate;
    }

    public void setLastQcrawlerLoginDate(Date lastQcrawlerLoginDate) {
        this.lastQcrawlerLoginDate = lastQcrawlerLoginDate;
    }

    public User(Long id, UUID apiKey, UserDefaultView userDefaultView,
            @NotNull @Size(min = 4, max = 50) String username, @NotNull @Size(min = 4, max = 100) String password,
            @NotNull String firstname, @NotNull String lastname, @NotNull @Size(min = 4, max = 50) String email,
            String telegram_code, Long telegramChatId, boolean spam, Date lastQcrawlerLoginDate,
            @NotNull Boolean enabled, @NotNull Date lastPasswordResetDate, List<Authority> authorities, Node node) {
        this.id = id;
        this.apiKey = apiKey;
        this.userDefaultView = userDefaultView;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.telegram_code = telegram_code;
        this.telegramChatId = telegramChatId;
        this.spam = spam;
        this.lastQcrawlerLoginDate = lastQcrawlerLoginDate;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
        this.node = node;
    }

    public User() {
    }

    public Date getLastQCloudLoginDate() {
        return lastQCloudLoginDate;
    }

    public void setLastQCloudLoginDate(Date lastQCloudLoginDate) {
        this.lastQCloudLoginDate = lastQCloudLoginDate;
    }

    public User(Long id, UUID apiKey, UserDefaultView userDefaultView,
            @NotNull @Size(min = 4, max = 50) String username, @NotNull @Size(min = 4, max = 100) String password,
            @NotNull String firstname, @NotNull String lastname, @NotNull @Size(min = 4, max = 50) String email,
            String telegram_code, Long telegramChatId, boolean spam, Date lastQcrawlerLoginDate,
            Date lastQCloudLoginDate, @NotNull Boolean enabled, @NotNull Date lastPasswordResetDate,
            List<Authority> authorities, Node node) {
        this.id = id;
        this.apiKey = apiKey;
        this.userDefaultView = userDefaultView;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.telegram_code = telegram_code;
        this.telegramChatId = telegramChatId;
        this.spam = spam;
        this.lastQcrawlerLoginDate = lastQcrawlerLoginDate;
        this.lastQCloudLoginDate = lastQCloudLoginDate;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
        this.node = node;
    }

}