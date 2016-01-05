package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;

public class AddressDetailsVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private ItemVO item;
	private Long quantity;
	private Long globalid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemVO getItem() {
		return item;
	}

	public void setItem(ItemVO item) {
		this.item = item;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "AddressDetailsVO [id=" + id + ", item=" + item + ", quantity=" + quantity + "]";
	}

	public Long getGlobalid() {
		return globalid;
	}

	public void setGlobalid(Long globalid) {
		this.globalid = globalid;
	}

}
