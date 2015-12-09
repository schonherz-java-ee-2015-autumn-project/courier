package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;

public class ItemVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String item;
	private int quantity;
	private int value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
