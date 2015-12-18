package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.List;

import hu.schonherz.java.training.courier.entities.CargoStatus;

public class CargoVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private UserVO user;
	private RestaurantVO restaurant;
	List<AddressVO> addresses;
	private CargoStatus status;
	private double totalValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RestaurantVO getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantVO restaurant) {
		this.restaurant = restaurant;
	}

	public List<AddressVO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressVO> addresses) {
		this.addresses = addresses;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public CargoStatus getStatus() {
		return status;
	}

	public void setStatus(CargoStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CargoVO [id=" + id + ", user=" + user + ", restaurant=" + restaurant + ", addresses=" + addresses
				+ ", status=" + status + ", totalValue=" + totalValue + "]";
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

}
