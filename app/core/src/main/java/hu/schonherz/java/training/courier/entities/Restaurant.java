package hu.schonherz.java.training.courier.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Restaurant extends GlobalEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phone;

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

	@Override
	public String toString() {
		return "Restaurant [name=" + name + ", address=" + address + ", phone=" + phone + "]";
	}

}
