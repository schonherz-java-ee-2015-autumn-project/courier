package hu.schonherz.java.training.courier.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.CargoService;
import hu.schonherz.java.training.courier.service.UserService;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@ManagedBean(name = "availableBean")
@ViewScoped
public class AvailableBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CargoVO> cargoes;
	@ManagedProperty("#{cargoService}")
	private CargoService cargoService;
	@ManagedProperty("#{userService}")
	private UserService userService;
	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;
	UserVO userVO;

	@PostConstruct
	public void init() {
		try {
			userVO = getUserSessionBean().getUserVO();
			cargoes = getCargoService().findAllByStatus(CargoStatus.getValue(1L));
			double cargoPrice;
			double addressPrice;
			for (int i = 0; i < cargoes.size(); i++) {
				cargoPrice = 0;
				List<AddressVO> addresses = cargoes.get(i).getAddresses();
				for (int j = 0; j < addresses.size(); j++) {
					addressPrice = 0;
					List<ItemVO> items = addresses.get(j).getItems();
					for (int k = 0; k < items.size(); k++)
						addressPrice += items.get(k).getPrice() * items.get(k).getQuantity();
					addresses.get(j).setTotalValue(addressPrice);
					cargoPrice += addressPrice;
				}
				cargoes.get(i).setTotalValue(cargoPrice);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<CargoVO> getCargoes() {
		return cargoes;
	}

	public void setCargoes(List<CargoVO> cargoes) {
		this.cargoes = cargoes;
	}

	public CargoService getCargoService() {
		return cargoService;
	}

	public void setCargoService(CargoService cargoService) {
		this.cargoService = cargoService;
	}

	public void showOnMap(Long cargoId) throws IOException {

		getFacesExternalContext().getSessionMap().put("cargoId", cargoId);
		getFacesExternalContext().redirect("../secured/map.xhtml");
	}

	public void pickUpCargo(CargoVO cargo) {

		cargo.setUser(userVO);
		cargo.setStatus(CargoStatus.getValue(2L));
		try {
			getCargoService().save(cargo);
			userVO.setTransporting(1L);
			getUserService().save(userVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			getFacesExternalContext().redirect("../secured/inprogress.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public ExternalContext getFacesExternalContext() {

		return getFacesContext().getExternalContext();
	}

	public FacesContext getFacesContext() {

		return FacesContext.getCurrentInstance();
	}
}
