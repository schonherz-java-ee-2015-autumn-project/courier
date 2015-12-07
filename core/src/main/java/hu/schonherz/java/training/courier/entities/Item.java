package hu.schonherz.java.training.courier.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Item extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String item;
	private int value;

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

}
