package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;

public class ItemVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Long quantity;
	private double price;
	private Long globalid;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ItemVO [id=" + id + ", name=" + name + ", quantity=" + quantity + ", price=" + price + "]";
	}

	public Long getGlobalid() {
		return globalid;
	}

	public void setGlobalid(Long globalid) {
		this.globalid = globalid;
	}

}
