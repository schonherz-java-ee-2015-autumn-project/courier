package hu.schonherz.java.training.courier.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class User extends GlobalEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String fullname;
	private String password;
	@Column(length = 20, columnDefinition = "bigint(20) default 0")
	private Long transporting = 0L;
	@Column(nullable = false)
	private boolean removed;
	@ManyToMany
	private List<Role> roles;

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

	public User() {
		super();
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Long getTransporting() {
		return transporting;
	}

	public void setTransporting(Long transporting) {
		this.transporting = transporting;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

}
