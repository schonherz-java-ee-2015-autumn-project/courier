package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.Date;
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
	private Date deliveredAt;
	private Long globalid;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
		result = prime * result + ((globalid == null) ? 0 : globalid.hashCode());
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((totalValue == null) ? 0 : totalValue.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CargoVO other = (CargoVO) obj;
		if (addresses == null) {
			if (other.addresses != null)
				return false;
		} else if (!addresses.equals(other.addresses))
			return false;
		if (globalid == null) {
			if (other.globalid != null)
				return false;
		} else if (!globalid.equals(other.globalid))
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		if (status != other.status)
			return false;
		if (totalValue == null) {
			if (other.totalValue != null)
				return false;
		} else if (!totalValue.equals(other.totalValue))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

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

	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

}
