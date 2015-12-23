package hu.schonherz.java.training.courier.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@ManagedBean(name = "inProgressBean")
@ViewScoped
public class InProgressBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CargoVO> cargo;
	@EJB
	CargoServiceLocal cargoService;
	@EJB
	UserServiceLocal userService;
	@ManagedProperty(value = "#{userSessionBean}")
	UserSessionBean userSessionBean;
	UserVO userVO;

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

	public CargoServiceLocal getCargoService() {
		return cargoService;
	}

	public void setCargoService(CargoServiceLocal cargoService) {
		this.cargoService = cargoService;
	}

	public void showOnMap(Long cargoId) throws IOException {
		getFacesExternalContext().getSessionMap().put("cargoId", cargoId);
		getFacesExternalContext().redirect("../secured/map.xhtml");
	}

	public List<CargoVO> getCargo() {
		return cargo;
	}

	public void setCargo(List<CargoVO> cargo) {
		this.cargo = cargo;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public UserServiceLocal getUserService() {
		return userService;
	}

	public void setUserService(UserServiceLocal userService) {
		this.userService = userService;
	}

	public ExternalContext getFacesExternalContext() {

		return getFacesContext().getExternalContext();
	}

	public FacesContext getFacesContext() {

		return FacesContext.getCurrentInstance();
	}

}
