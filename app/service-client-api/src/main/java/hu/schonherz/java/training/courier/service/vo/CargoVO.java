package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.List;

import hu.schonherz.java.training.courier.entities.CargoStatus;

public class CargoVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private UserVO user;
	private RestaurantVO restaurant;
	List<AddressVO> addresses;
	private CargoStatus status;
	private Double totalValue;
	private Double income;
	private Long totalDistance;
	private Long totalDuration;
	private Long globalid;

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

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
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

	public Long getGlobalid() {
		return globalid;
	}

	public void setGlobalid(Long globalid) {
		this.globalid = globalid;
	}

	public Long getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Long totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Long getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(Long totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

}
