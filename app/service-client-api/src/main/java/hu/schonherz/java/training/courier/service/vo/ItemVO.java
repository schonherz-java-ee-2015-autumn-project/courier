package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;

public class ItemVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Double price;
	private Long globalid;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ItemVO [id=" + id + ", name=" + name + ", price=" + price + "]";
	}

	public Long getGlobalid() {
		return globalid;
	}

	public void setGlobalid(Long globalid) {
		this.globalid = globalid;
	}

}
