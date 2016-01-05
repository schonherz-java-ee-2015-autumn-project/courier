package hu.schonherz.java.training.courier.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;


@Entity
public class Address extends GlobalEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String address;
	@OneToMany(cascade = CascadeType.ALL)
	private List<AddressDetails> details;
	private Date deadline;
	@Enumerated(EnumType.STRING)
	private Payment payment;
	@Enumerated(EnumType.STRING)
	private AddressStatus status;

	public List<AddressDetails> getDetails() {
		return details;
	}

	public void setDetails(List<AddressDetails> details) {
		this.details = details;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public AddressStatus getStatus() {
		return status;
	}

	public void setStatus(AddressStatus status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
