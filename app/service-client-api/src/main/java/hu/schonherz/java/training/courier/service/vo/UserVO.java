package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.List;

public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String fullname;
	private String username;
	private String password;
	private SupplierVO supplier;
	private List<RoleVO> roles;

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

	public SupplierVO getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierVO supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", fullname=" + fullname + ", username=" + username + ", password=" + password
				+ ", supplier=" + supplier + ", roles=" + roles + "]";
	}

}
