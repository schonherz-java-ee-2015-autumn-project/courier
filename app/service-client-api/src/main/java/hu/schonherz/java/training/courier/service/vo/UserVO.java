package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String fullname;
	private String username;
	private String password;
	private Long transporting;
	private List<RoleVO> roles;
	private Date regdate;
	private Date moddate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleVO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", fullname=" + fullname + ", username=" + username + ", password=" + password
				+ ", transporting=" + transporting + ", roles=" + roles + ", regdate=" + getRegdate() + ", moddate="
				+ getModdate() + "]";
	}

	public Long getTransporting() {
		return transporting;
	}

	public void setTransporting(Long transporting) {
		this.transporting = transporting;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getModdate() {
		return moddate;
	}

	public void setModdate(Date moddate) {
		this.moddate = moddate;
	}

}
