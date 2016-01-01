package hu.schonherz.java.training.courier.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.service.AddressServiceLocal;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;
import hu.schonherz.java.training.courier.service.webservice.CargoWebServiceLocal;

@ManagedBean(name = "mapBean")
@ViewScoped
public class MapBean implements Serializable {
	private final static Logger logger = Logger.getLogger(MapBean.class);
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{userSessionBean}")
	UserSessionBean userSessionBean;
	@EJB
	AddressServiceLocal addressService;
	@EJB
	CargoServiceLocal cargoService;
	@EJB
	UserServiceLocal userService;

	private CargoVO selectedCargo;
	private String addressList;
	private List<Payment> allPaymentStatus = Arrays.asList(Payment.values());
	List<AddressVO> addresses;
	@EJB
	CargoWebServiceLocal cargoWebService;

	public List<AddressVO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressVO> addresses) {
		this.addresses = addresses;
	}

	@PostConstruct
	public void init() {

		Long id = getUserSessionBean().getUserVO().getTransporting();

		if (id == null || id == 0) {
			id = (Long) getFacesExternalContext().getSessionMap().get("cargoId");
			if (id == null)
				try {
					getFacesExternalContext().redirect("../secured/available.xhtml");
				} catch (IOException e) {
					logger.info("Error:", e);
				}
		}

		loadCargo(id);

	}

	public void loadCargo(Long id) {
		try {
			selectedCargo = getCargoService().findCargoById(id);
			double cargoPrice = 0;
			double addressPrice = 0;
			addresses = new ArrayList<>();
			List<AddressVO> allAddress = selectedCargo.getAddresses();
			for (AddressVO addressVO : allAddress) {
				if (addressVO.getStatus() == null) {
					addresses.add(addressVO);
				}
			}

			for (int j = 0; j < addresses.size(); j++) {
				addressPrice = 0;
				List<ItemVO> items = addresses.get(j).getItems();
				for (int k = 0; k < items.size(); k++)
					addressPrice += items.get(k).getPrice() * items.get(k).getQuantity();
				addresses.get(j).setTotalValue(addressPrice);
				cargoPrice += addressPrice;
			}
			selectedCargo.setTotalValue(cargoPrice);
			addressList = selectedCargo.getRestaurant().getAddress();

		} catch (Exception e) {
			logger.info("Error:", e);
		}
	}

	public void cargoStatusChanged(Long value) throws Exception {
		CargoStatus status = CargoStatus.getValue(value);

		if (cargoWebService.setCargoStatus(selectedCargo.getGlobalid(), status) == 0) {
			selectedCargo.setStatus(status);
			getCargoService().updateCargoStatusById(selectedCargo.getId(), status.toString());
			updateRoute();
			logger.info("INFO:AddressList -> " + addressList);

			if (status.equals(CargoStatus.getValue(4L))) {
				getUserSessionBean().getUserVO().setTransporting(0L);
				getUserService().save(getUserSessionBean().getUserVO());
				getFacesExternalContext().getSessionMap().remove("cargoId");
				getFacesExternalContext().redirect("../secured/available.xhtml");
			}
		} else {
			getFacesContext().addMessage("errorMessage",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Something wrong happened!"));
			logger.info("INFO:Error while setting cargo status.");
		}

	}

	public void updateRoute() {

		List<String> stringAddress = new ArrayList<String>();

		for (int j = 0; j < addresses.size(); j++) {

			if (addresses.get(j).getStatus() == null) {
				stringAddress.add(addresses.get(j).getAddress());

			}
		}

		if (addresses.size() > 1)
			addressList = StringUtils.join(stringAddress, ";");
		else
			addressList = stringAddress.toString();

	}

	public void addressStatusChanged(Long addressId, Long addressStatusId) throws Exception {
		AddressStatus addressStatus = (AddressStatus) AddressStatus.getValue(addressStatusId);

		AddressVO addressVO = getAddressService().findAddressById(addressId);
		addressVO.setStatus(addressStatus);
		getAddressService().save(addressVO);
		addresses.remove(addressVO);
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
