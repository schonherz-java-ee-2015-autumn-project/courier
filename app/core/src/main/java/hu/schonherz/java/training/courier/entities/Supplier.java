package hu.schonherz.java.training.courier.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Supplier extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String address;
	private String phone;
	private String email;

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

}
