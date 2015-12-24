package hu.schonherz.java.training.courier.web.beans;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang3.StringUtils;

import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.service.AddressServiceLocal;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;

@ManagedBean(name = "mapBean")
@ViewScoped
public class MapBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	CargoServiceLocal cargoService;
	@EJB
	UserServiceLocal userService;
	@ManagedProperty(value = "#{userSessionBean}")
	UserSessionBean userSessionBean;
	@EJB
	AddressServiceLocal addressService;
	private CargoVO selectedCargo;
	private String addressList;
	private List<Payment> allPaymentStatus = Arrays.asList(Payment.values());

	@PostConstruct
	public void init() {

		Long id = (Long) getFacesExternalContext().getSessionMap().get("cargoId");
		try {
			selectedCargo = getCargoService().findCargoById(id);
			double cargoPrice = 0;
			double addressPrice = 0;

			List<AddressVO> addresses = selectedCargo.getAddresses();
			for (int j = 0; j < addresses.size(); j++) {
				addressPrice = 0;
				List<ItemVO> items = addresses.get(j).getItems();
				for (int k = 0; k < items.size(); k++)
					addressPrice += items.get(k).getPrice() * items.get(k).getQuantity();
				addresses.get(j).setTotalValue(addressPrice);
				cargoPrice += addressPrice;
			}
			selectedCargo.setTotalValue(cargoPrice);

		} catch (

		Exception e)

		{

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addressList = selectedCargo.getRestaurant().getAddress();

	}

	public void cargoStatusChanged(Long value) throws Exception {
		CargoStatus status = CargoStatus.getValue(value);
		selectedCargo.setStatus(status);
		getCargoService().save(selectedCargo);
		updateRoute();
		System.out.println(addressList);

		if (status.equals(CargoStatus.getValue(4L))) {
			getUserSessionBean().getUserVO().setTransporting(0L);
			getUserService().save(getUserSessionBean().getUserVO());
			getFacesExternalContext().redirect("../secured/available.xhtml");
		}

	}

	public void updateRoute() {
		List<AddressVO> addresses = selectedCargo.getAddresses();

		List<String> stringAddress = new ArrayList<String>();

		for (int j = 0; j < addresses.size(); j++) {
			stringAddress.add(addresses.get(j).getAddress());
		}

		if (addresses.size() > 1)
			addressList = StringUtils.join(stringAddress, ";");
		else
			addressList = stringAddress.toString();

	}

	public void addressStatusChanged(Long address, Long buttonValue) throws Exception {
		AddressStatus addressStatus = (AddressStatus) AddressStatus.getValue(buttonValue);
		Long addressId = (Long) address;
		AddressVO addressVO = getAddressService().findAddressById(addressId);
		addressVO.setStatus(addressStatus);
		List<AddressVO> addresses = selectedCargo.getAddresses();

		int index = 0;
		if (addresses.contains(addressVO)) {
			addresses.remove(index);
		}
		getAddressService().save(addressVO);

		updateRoute();

	}

	public void paymentStatusChanged(AjaxBehaviorEvent e) throws Exception {
		Payment paymentStatus = (Payment) ((UIOutput) e.getSource()).getValue();
		Long addressId = (long) (Long) e.getComponent().getAttributes().get("addressId");
		AddressVO addressVO = getAddressService().findAddressById(addressId);
		addressVO.setPayment(paymentStatus);
		getAddressService().save(addressVO);

	}

	public CargoServiceLocal getCargoService() {
		return cargoService;
	}

	public void setCargoService(CargoServiceLocal cargoService) {
		this.cargoService = cargoService;
	}

	public AddressServiceLocal getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressServiceLocal addressService) {
		this.addressService = addressService;
	}

	public CargoVO getSelectedCargo() {
		return selectedCargo;
	}

	public void setSelectedCargo(CargoVO selectedCargo) {
		this.selectedCargo = selectedCargo;
	}

	public ExternalContext getFacesExternalContext() {

		return getFacesContext().getExternalContext();
	}

	public FacesContext getFacesContext() {

		return FacesContext.getCurrentInstance();
	}

	public String getAddressList() {
		return addressList;
	}

	public void setAddressList(String addressList) {
		this.addressList = addressList;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public UserServiceLocal getUserService() {
		return userService;
	}

	public void setUserService(UserServiceLocal userService) {
		this.userService = userService;
	}

	public List<Payment> getAllPaymentStatus() {
		return allPaymentStatus;
	}

	public void setAllPaymentStatus(List<Payment> allPaymentStatus) {
		this.allPaymentStatus = allPaymentStatus;
	}

}
