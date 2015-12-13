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
import hu.schonherz.java.training.courier.service.CargoService;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;

@ManagedBean(name = "inProgressBean")
@ViewScoped
public class InProgressBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CargoVO> cargo;
	@ManagedProperty("#{cargoService}")
	private CargoService cargoService;
	private String cargoStatus;
	private String addressStatus;
	private String paymentStatus;
	private List<Payment> allPaymentStatus = Arrays.asList(Payment.values());
	private List<CargoStatus> allCargoStatus = Arrays.asList(CargoStatus.values());
	private List<AddressStatus> allAddressStatus = Arrays.asList(AddressStatus.values());

	@PostConstruct
	public void init() {
		try {
			//Set cargo id to user!
			cargo = getCargoService().findCargoesById(2L);

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

	public void cargoStatusChanged(AjaxBehaviorEvent e) {
		cargoStatus = (String) ((UIOutput) e.getSource()).getValue();
		Long cargoStatusId = (long) allCargoStatus.indexOf(cargoStatus);

		// Update cargo status in database!
	}

	public void addressStatusChanged(AjaxBehaviorEvent e) {
		addressStatus = (String) ((UIOutput) e.getSource()).getValue();
		Long addressStatusId = (long) allAddressStatus.indexOf(addressStatus);

		// Update cargo status in database!
	}

	public void paymentStatusChanged(AjaxBehaviorEvent e) {
		paymentStatus = (String) ((UIOutput) e.getSource()).getValue();
		Long paymentStatusId = (long) allPaymentStatus.indexOf(paymentStatus);

		// Update cargo status in database!
	}

	public void selected(Long cargoId) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("cargoId", cargoId);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../secured/map.xhtml");
	}

	public CargoStatus convertStatus(Long statusId) {
		return CargoStatus.getValue(statusId);

	}

	public Payment convertPayment(Long statusId) {
		return Payment.getValue(statusId);

	}

	public List<CargoVO> getCargo() {
		return cargo;
	}

	public void setCargo(List<CargoVO> cargo) {
		this.cargo = cargo;
	}

	public String getCargoStatus() {
		return cargoStatus;
	}

	public void setCargoStatus(String cargoStatus) {
		this.cargoStatus = cargoStatus;
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

	public String getAddressStatus() {
		return addressStatus;
	}

	public void setAddressStatus(String addressStatus) {
		this.addressStatus = addressStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public List<Payment> getAllPaymentStatus() {
		return allPaymentStatus;
	}

	public void setAllPaymentStatus(List<Payment> allPaymentStatus) {
		this.allPaymentStatus = allPaymentStatus;
	}

}
