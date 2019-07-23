/**
 * Node class
 * @author Daniel Mancera <daniel.mancera@crg.eu>
 */
package eu.qcloud.node;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import eu.qcloud.communityline.CommunityLine;
import eu.qcloud.communityline.CommunityLineNodeRelation;
import eu.qcloud.dataSource.DataSource;
import eu.qcloud.security.model.User;

@Entity
@Table(name = "node")
public class Node {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "node_seq")
	@SequenceGenerator(name = "node_seq", sequenceName = "node_seq", allocationSize = 1)
	@JsonBackReference(value = "id")
	private Long id;

	@Column(name = "apiKey", updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
	@org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDBinaryType")
	private UUID apiKey;

	@Column
	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

	@Column(name = "NAME", length = 50, unique = true)
	@NotNull
	private String name;

	@OneToMany(mappedBy = "node")
	private List<DataSource> dataSource;

	@OneToMany(mappedBy = "node", cascade = CascadeType.ALL)
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	@JsonBackReference(value = "communityLine")
	@OneToMany(mappedBy = "communityLine", cascade = CascadeType.ALL)
	private List<CommunityLineNodeRelation> communityLine;

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<DataSource> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<DataSource> dataSource) {
		this.dataSource = dataSource;
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

	public List<CommunityLineNodeRelation> getCommunityLine() {
		return communityLine;
	}

	public void setCommunityLine(List<CommunityLineNodeRelation> communityLines) {
		this.communityLine = communityLines;
	}

	public Node(Long id, UUID apiKey, String country, @NotNull String name, List<DataSource> dataSource,
			List<User> users, List<CommunityLineNodeRelation> communityLines) {
		this.id = id;
		this.apiKey = apiKey;
		this.country = country;
		this.name = name;
		this.dataSource = dataSource;
		this.users = users;
		this.communityLine = communityLines;
	}

	public Node() {
	}

}