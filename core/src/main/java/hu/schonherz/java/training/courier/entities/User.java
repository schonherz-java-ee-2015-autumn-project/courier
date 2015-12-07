package hu.schonherz.java.training.courier.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String fullname;
	private String password;

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

}
