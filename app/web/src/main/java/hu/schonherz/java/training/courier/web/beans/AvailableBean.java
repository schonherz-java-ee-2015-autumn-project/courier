package hu.schonherz.java.training.courier.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import hu.schonherz.java.training.courier.service.AddressService;
import hu.schonherz.java.training.courier.service.ItemService;
import hu.schonherz.java.training.courier.service.RestaurantService;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;

@ManagedBean(name = "availableBean")
@ViewScoped
public class AvailableBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<RestaurantVO> availables;
	private List<ItemVO> items;
	private List<AddressVO> addresses;
	@ManagedProperty("#{restaurantService}")
	private RestaurantService restaurantService;
	@ManagedProperty("#{itemService}")
	private ItemService itemService;
	@ManagedProperty("#{addressService}")
	private AddressService addressService;

	@PostConstruct
	public void init() {
		try {
			availables = restaurantService.findAll();
			items = itemService.findAll();
			addresses = addressService.findAll();
			System.out.println("fasz " + addresses);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<RestaurantVO> getAvailables() {
		return availables;
	}

	public void setAvailables(List<RestaurantVO> availables) {
		this.availables = availables;
	}

	public RestaurantService getRestaurantService() {
		return restaurantService;
	}

	public void setRestaurantService(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	public List<ItemVO> getItems() {
		return items;
	}

	public void setItems(List<ItemVO> items) {
		this.items = items;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public List<AddressVO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressVO> addresses) {
		this.addresses = addresses;
	}

	public AddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	
}
