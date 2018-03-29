package eu.qcloud.view;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.qcloud.CV.CV;
import eu.qcloud.security.model.User;

@Entity
@Table(name="view")
public class View {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "view_seq")
    @SequenceGenerator(name = "view_seq", sequenceName = "view_seq", allocationSize = 1)
	private Long id;
	
	private String name;
	
	@Column(name ="is_default",nullable= false,columnDefinition="tinyint(1) default 0")
	private boolean isDefault;

	@ManyToOne
	@JoinColumn(name="userId",insertable=true, updatable= false,nullable=true)
	private User user;
	
	@OneToOne
	@JoinColumn(name="cvId",insertable=true, updatable= false,nullable=true,unique=true)
	private CV cv;
	
	@OneToMany(mappedBy="view")
	private List<ViewDisplay> viewDisplay;
	
	public CV getCv() {
		return cv;
	}

	public void setCv(CV cv) {
		this.cv = cv;
	}
	
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
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
	
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
