package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.List;

import hu.schonherz.java.training.courier.entities.Address;
import hu.schonherz.java.training.courier.entities.Restaurant;

public class CargoVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Restaurant restaurant;
	List<Address> addresses;
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
