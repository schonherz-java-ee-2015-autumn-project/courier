package hu.schonherz.java.training.courier.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Item extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Long quantity;
	private double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
