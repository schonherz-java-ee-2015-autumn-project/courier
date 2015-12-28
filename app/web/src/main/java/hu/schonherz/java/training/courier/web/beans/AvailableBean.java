package hu.schonherz.java.training.courier.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.WebServiceClientLocal;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@ManagedBean(name = "availableBean")
@ViewScoped
public class AvailableBean implements Serializable {
	private final static Logger logger = Logger.getLogger(AvailableBean.class);
	private static final long serialVersionUID = 1L;
	private List<CargoVO> cargoes;
	@EJB
	WebServiceClientLocal webServiceClient;

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

	public void pickUpCargo(CargoVO cargo) throws Exception {

		// ellen�rizz�k hogy a Web Servicen kereszt�l k�pesek voltunk-e menteni
		// az aktu�lis cargoStatus-t, ha igen akkor mi is ment�nk adatb�zisba
		// ha viszont nem akkor egy�rtelm�en nem csin�lunk semmit, csak egy
		// hib�t dobunk majd az oldalra
		if (webServiceClient.setCargoStatus(cargo.getGlobalid(), CargoStatus.getValue(2L)) == 0) {
			cargo.setUser(userVO);
			cargo.setStatus(CargoStatus.getValue(2L));

			getCargoService().save(cargo);
			userVO.setTransporting(cargo.getId());
			getUserService().save(userVO);
			getUserSessionBean().getUserVO().setTransporting(cargo.getId());
			showOnMap(cargo.getId());
			logger.info("INFO:Succes cargoStatus setting with WebService. ");
		} else {
			getFacesContext().addMessage("errorMessage",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Something wrong happened."));
			logger.info("ERROR:Error while setting cargoStatus with WebService.");
		}

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
