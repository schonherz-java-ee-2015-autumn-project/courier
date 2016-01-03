package hu.schonherz.java.training.courier.web.beans;

import java.io.IOException;
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
import hu.schonherz.java.training.courier.service.vo.AddressDetailsVO;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;

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
	List<AddressVO> addresses;
	private Long totalDistance;
	private Long totalDuration;

	@PostConstruct
	public void init() {

		Long id = getUserSessionBean().getUserVO().getTransporting();

		if (id == null || id == 0) {
			id = (Long) getFacesExternalContext().getSessionMap().get("cargoId");
			if (id == null)
				try {
					getFacesExternalContext().redirect("../secured/profile.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				List<AddressDetailsVO> details = addresses.get(j).getDetails();
				for (int k = 0; k < details.size(); k++)
					addressPrice += details.get(k).getItem().getPrice() * details.get(k).getQuantity();
				addresses.get(j).setTotalValue(addressPrice);
				cargoPrice += addressPrice;
			}
			selectedCargo.setTotalValue(cargoPrice);
			addressList = selectedCargo.getRestaurant().getAddress() + ";" + updateRoute();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargoStatusChanged(Long value) throws Exception {

		CargoStatus status = CargoStatus.getValue(value);
		selectedCargo.setStatus(status);
		selectedCargo.setTotalDistance(getTotalDistance());
		selectedCargo.setTotalDuration(getTotalDuration());
		getCargoService().updateCargoStatusById(selectedCargo.getId(), status.toString(), getTotalDistance(),
				getTotalDuration());
		addressList = updateRoute();

		if (status.equals(CargoStatus.getValue(4L))) {
			getUserSessionBean().getUserVO().setTransporting(0L);
			getUserService().save(getUserSessionBean().getUserVO());
			getFacesExternalContext().getSessionMap().remove("cargoId");
			getFacesExternalContext().redirect("../secured/available.xhtml");
		}

	}

	public String updateRoute() {

		List<String> stringAddress = new ArrayList<String>();

		for (int j = 0; j < addresses.size(); j++) {

			if (addresses.get(j).getStatus() == null) {
				stringAddress.add(addresses.get(j).getAddress());

			}
		}

		if (addresses.size() > 1)
			return StringUtils.join(stringAddress, ";");
		else
			return stringAddress.toString();

	}

	public void addressStatusChanged(Long addressId, Long addressStatusId) throws Exception {

		AddressStatus addressStatus = (AddressStatus) AddressStatus.getValue(addressStatusId);
		AddressVO addressVO = getAddressService().findAddressById(addressId);
		addressVO.setStatus(addressStatus);
		getAddressService().save(addressVO);

		if (addressStatus.equals(AddressStatus.getValue(1L))) {

			int index = addresses.indexOf(addressVO);
			if (selectedCargo.getIncome() == null)
				selectedCargo.setIncome(addresses.get(index).getTotalValue());
			else
				selectedCargo.setIncome(selectedCargo.getIncome() + addresses.get(index).getTotalValue());

		}

		addresses.remove(addressVO);
		addressList = updateRoute();

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

	public Long getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Long totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Long getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(Long totalDuration) {
		this.totalDuration = totalDuration;
	}

	public List<AddressVO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressVO> addresses) {
		this.addresses = addresses;
	}

}
