package hu.schonherz.java.training.courier.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import hu.schonherz.java.training.courier.service.CargoService;
import hu.schonherz.java.training.courier.service.vo.CargoVO;

@ManagedBean(name = "mapBean")
@ViewScoped
public class MapBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty("#{cargoService}")
	private CargoService cargoService;
	private CargoVO selectedCargo;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Long id = (Long) context.getExternalContext().getSessionMap().get("cargoId");
		try {
			selectedCargo = getCargoService().findCargoById(id);
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

	public CargoVO getSelectedCargo() {
		return selectedCargo;
	}

	public void setSelectedCargo(CargoVO selectedCargo) {
		this.selectedCargo = selectedCargo;
	}



}
