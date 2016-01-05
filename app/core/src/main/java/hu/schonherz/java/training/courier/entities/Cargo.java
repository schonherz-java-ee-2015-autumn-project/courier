package hu.schonherz.java.training.courier.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Cargo extends GlobalEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private User user;
	@ManyToOne(cascade = CascadeType.ALL)
	private Restaurant restaurant;
	@OneToMany(cascade = CascadeType.ALL)
	List<Address> addresses;

	@Column(length = 32, columnDefinition = "varchar(32) default 'Free'")
	@Enumerated(EnumType.STRING)
	private CargoStatus status = CargoStatus.getValue(1L);

	private Long totalDistance;
	private Long totalDuration;
	private Date deliveredAt;

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

	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

}
