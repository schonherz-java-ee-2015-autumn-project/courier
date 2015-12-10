package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.OneToMany;

import hu.schonherz.java.training.courier.entities.Item;

public class AddressVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String address;
	@OneToMany
	private List<Item> items;
	private Date deadline;
	private String payment;
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "AddressVO [id=" + id + ", address=" + address + ", items=" + items + ", deadline=" + deadline
				+ ", payment=" + payment + ", status=" + status + "]";
	}

	

}
