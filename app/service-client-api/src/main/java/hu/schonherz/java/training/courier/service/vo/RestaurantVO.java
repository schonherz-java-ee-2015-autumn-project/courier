package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;

public class RestaurantVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String address;
	private String phone;
	private Long globalid;

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

	public Long getGlobalid() {
		return globalid;
	}

	public void setGlobalid(Long globalid) {
		this.globalid = globalid;
	}

	@Override
	public String toString() {
		return "RestaurantVO [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + "]";
	}

}
