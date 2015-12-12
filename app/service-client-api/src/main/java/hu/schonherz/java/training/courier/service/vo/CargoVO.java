package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.List;

public class CargoVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private RestaurantVO restaurant;
	List<AddressVO> addresses;
	private Long status;
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CargoVO [id=" + id + ", restaurant=" + restaurant + ", addresses=" + addresses + ", cargoStatus="
				+ status + ", totalValue=" + totalValue + "]";
	}

}
