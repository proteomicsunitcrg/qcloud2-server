package eu.qcloud.passwordReset;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import eu.qcloud.security.model.User;

@Entity
public class PasswordReset {
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "password_reset_seq")
    @SequenceGenerator(name = "password_reset_seq", sequenceName = "password_reset_seq", allocationSize = 1)
	protected Long id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name = "token", updatable = true, nullable = false, unique=true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
	private UUID token;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="expiration_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;
	
	@Column
	private int numberOfRequests;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UUID getToken() {
		return token;
	}

	public void setToken(UUID token) {
		this.token = token;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getNumberOfRequests() {
		return numberOfRequests;
	}

	public void setNumberOfRequests(int numberOfRequests) {
		this.numberOfRequests = numberOfRequests;
	}
}
