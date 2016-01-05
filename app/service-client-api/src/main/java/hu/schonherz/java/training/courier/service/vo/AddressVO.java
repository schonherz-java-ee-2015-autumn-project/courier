package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.OneToMany;

import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.Payment;


public class AddressVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String address;
	@OneToMany
	private List<AddressDetailsVO> details;
	private Date deadline;
	private Long globalid;
	private Payment payment;
	private Double totalValue;
	private AddressStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<AddressDetailsVO> getDetails() {
		return details;
	}

	public void setDetails(List<AddressDetailsVO> details) {
		this.details = details;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AddressVO other = (AddressVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AddressVO [id=" + id + ", address=" + address + ", details=" + details + ", deadline=" + deadline
				+ ", payment=" + payment + ", totalValue=" + totalValue + ", status=" + status + "]";
	}

	public Long getGlobalid() {
		return globalid;
	}

	public void setGlobalid(Long globalid) {
		this.globalid = globalid;
	}

}
