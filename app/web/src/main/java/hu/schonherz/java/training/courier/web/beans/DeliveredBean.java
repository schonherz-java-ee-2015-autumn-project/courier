package hu.schonherz.java.training.courier.web.beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.vo.AddressDetailsVO;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;

@ManagedBean(name = "deliveredBean")
@ViewScoped
public class DeliveredBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CargoVO> cargoes;
	@EJB
	CargoServiceLocal cargoService;
	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;
	private Date currentDate;
	private Date startDate;
	private Date endDate;
	Calendar now = Calendar.getInstance();

	@PostConstruct
	public void init() {
		try {
			currentDate = getMidnight();
			startDate = currentDate;
			endDate = currentDate;
			cargoes = getCargoService().findCargoesByUserIdAndStatusBetweenDatesOrderedByDeliveryDate(
					getUserSessionBean().getUserVO(), CargoStatus.getValue(4L), getStartDate(),
					modifyDate(getEndDate()));
			calculateDetails();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void calculateDetails() {

		double cargoPrice;
		double addressPrice;
		double negativeIncome;
		for (int i = 0; i < cargoes.size(); i++) {
			cargoPrice = 0;
			negativeIncome = 0;
			List<AddressVO> addresses = cargoes.get(i).getAddresses();
			for (int j = 0; j < addresses.size(); j++) {
				addressPrice = 0;
				List<AddressDetailsVO> details = addresses.get(j).getDetails();
				for (int k = 0; k < details.size(); k++)
					addressPrice += details.get(k).getItem().getPrice() * details.get(k).getQuantity();
				addresses.get(j).setTotalValue(addressPrice);
				cargoPrice += addressPrice;

				if (addresses.get(j).getStatus().equals(AddressStatus.getValue(2L)))
					negativeIncome += addressPrice;
			}
			cargoes.get(i).setTotalValue(cargoPrice);
			cargoes.get(i).setIncome(cargoPrice - negativeIncome);
		}
	}

	public void submit() throws Exception {
		cargoes = getCargoService().findCargoesByUserIdAndStatusBetweenDatesOrderedByDeliveryDate(
				getUserSessionBean().getUserVO(), CargoStatus.getValue(4L), getStartDate(), modifyDate(getEndDate()));
		calculateDetails();
	}

	public Date getMidnight() {
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		return now.getTime();
	}

	public Date modifyDate(Date date) {

		return new Date(date.getTime() + (1000 * 60 * 60 * 24));

	}

	public List<CargoVO> getCargoes() {
		return cargoes;
	}

	public void setCargoes(List<CargoVO> cargoes) {
		this.cargoes = cargoes;
	}

	public CargoServiceLocal getCargoService() {
		return cargoService;
	}

	public void setCargoService(CargoServiceLocal cargoService) {
		this.cargoService = cargoService;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}