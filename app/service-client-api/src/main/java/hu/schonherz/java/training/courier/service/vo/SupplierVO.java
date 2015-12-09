package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;

public class SupplierVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String address;
	private String phone;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "SupplierVO [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", email="
				+ email + "]";
	}

}
