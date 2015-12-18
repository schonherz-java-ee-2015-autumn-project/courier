package hu.schonherz.java.training.courier.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.service.AddressService;
import hu.schonherz.java.training.courier.service.CargoService;
import hu.schonherz.java.training.courier.service.UserService;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@ManagedBean(name = "inProgressBean")
@ViewScoped
public class InProgressBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CargoVO> cargo;
	@ManagedProperty("#{cargoService}")
	private CargoService cargoService;
	@ManagedProperty("#{addressService}")
	private AddressService addressService;
	@ManagedProperty("#{userService}")
	private UserService userService;
	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;
	UserVO userVO;
	private List<Payment> allPaymentStatus = Arrays.asList(Payment.values());
	private List<CargoStatus> allCargoStatus = Arrays.asList(CargoStatus.values());
	private List<AddressStatus> allAddressStatus = Arrays.asList(AddressStatus.values());

	@PostConstruct
	public void init() {
		try {

			cargo = getCargoService().findCargoesByUserIdAndStatus(getUserSessionBean().getUserVO().getId(),
					CargoStatus.getValue(2L));

			double cargoPrice;
			double addressPrice;
			for (int i = 0; i < cargo.size(); i++) {
				cargoPrice = 0;
				List<AddressVO> addresses = cargo.get(i).getAddresses();
				for (int j = 0; j < addresses.size(); j++) {
					addressPrice = 0;
					List<ItemVO> items = addresses.get(j).getItems();
					for (int k = 0; k < items.size(); k++)
						addressPrice += items.get(k).getPrice() * items.get(k).getQuantity();
					addresses.get(j).setTotalValue(addressPrice);
					cargoPrice += addressPrice;
				}
				cargo.get(i).setTotalValue(cargoPrice);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public CargoService getCargoService() {
		return cargoService;
	}

	public void setCargoService(CargoService cargoService) {
		this.cargoService = cargoService;
	}

	public void cargoStatusChanged(AjaxBehaviorEvent e) throws Exception {
		CargoStatus cargoStatus = (CargoStatus) ((UIOutput) e.getSource()).getValue();
		Long cargoId = (long) (Long) e.getComponent().getAttributes().get("cargoId");
		CargoVO cargoVO = getCargoService().findCargoById(cargoId);
		cargoVO.setStatus(cargoStatus);
		getCargoService().save(cargoVO);
		if (cargoStatus.equals(CargoStatus.getValue(5L))) {
			getUserSessionBean().getUserVO().setTransporting(0L);
			getUserService().save(getUserSessionBean().getUserVO());
			FacesContext context = FacesContext.getCurrentInstance();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../secured/available.xhtml");
		}

		System.out.println("cargostatus = " + cargoStatus + " 5L = " + CargoStatus.getValue(5L));

	}

	public void addressStatusChanged(AjaxBehaviorEvent e) throws Exception {
		AddressStatus addressStatus = (AddressStatus) ((UIOutput) e.getSource()).getValue();
		Long addressId = (Long) e.getComponent().getAttributes().get("addressId");
		AddressVO addressVO = getAddressService().findAddressById(addressId);
		addressVO.setStatus(addressStatus);
		getAddressService().save(addressVO);
		System.out.println("status = " + addressStatus + " address id = " + addressId);
		// Update cargo status in database!
	}

	public void paymentStatusChanged(AjaxBehaviorEvent e) throws Exception {
		Payment paymentStatus = (Payment) ((UIOutput) e.getSource()).getValue();
		Long addressId = (long) (Long) e.getComponent().getAttributes().get("addressId");
		AddressVO addressVO = getAddressService().findAddressById(addressId);
		addressVO.setPayment(paymentStatus);
		getAddressService().save(addressVO);
		System.out.println("status = " + paymentStatus + " address id " + addressId);
		// Update cargo status in database!
	}

	public void showOnMap(Long cargoId) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("cargoId", cargoId);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../secured/map.xhtml");
	}

	public List<CargoVO> getCargo() {
		return cargo;
	}

	public void setCargo(List<CargoVO> cargo) {
		this.cargo = cargo;
	}

	public List<CargoStatus> getAllCargoStatus() {
		return allCargoStatus;
	}

	public void setAllCargoStatus(List<CargoStatus> allCargoStatus) {
		this.allCargoStatus = allCargoStatus;
	}

	public List<AddressStatus> getAllAddressStatus() {
		return allAddressStatus;
	}

	public void setAllAddressStatus(List<AddressStatus> allAddressStatus) {
		this.allAddressStatus = allAddressStatus;
	}

	public List<Payment> getAllPaymentStatus() {
		return allPaymentStatus;
	}

	public void setAllPaymentStatus(List<Payment> allPaymentStatus) {
		this.allPaymentStatus = allPaymentStatus;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public AddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
