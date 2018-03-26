package eu.qcloud.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.qcloud.security.model.User;

@Entity
@Table(name="view")
public class View {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "view_seq")
    @SequenceGenerator(name = "view_seq", sequenceName = "chart_seq", allocationSize = 1)
	private Long id;
	
	private String name;
	
	@Column(name ="is_default",nullable= false,columnDefinition="tinyint(1) default 0")
	boolean isDefault;

	@ManyToOne
	@JoinColumn(name="userId",insertable=true, updatable= false,nullable=true)
	private User user;
	
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
