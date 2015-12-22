package hu.schonherz.java.training.courier.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cargo extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToOne
	private User user;
	@OneToOne
	private Restaurant restaurant;
	@OneToMany
	List<Address> addresses;
	@Enumerated(EnumType.STRING)
	private CargoStatus status;
	private Long globalid;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CargoStatus getStatus() {
		return status;
	}

	public void setStatus(CargoStatus status) {
		this.status = status;
	}

	public Long getGlobalid() {
		return globalid;
	}

	public void setGlobalid(Long globalid) {
		this.globalid = globalid;
	}

}
