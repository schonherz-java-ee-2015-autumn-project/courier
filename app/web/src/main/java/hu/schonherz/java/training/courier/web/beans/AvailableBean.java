package hu.schonherz.java.training.courier.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import hu.schonherz.java.training.courier.service.CargoService;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;

@ManagedBean(name = "availableBean")
@ViewScoped
public class AvailableBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CargoVO> cargoes;
	@ManagedProperty("#{cargoService}")
	private CargoService cargoService;

	@PostConstruct
	public void init() {
		try {
			cargoes = getCargoService().findAll();
			double price;
			for (int i = 0; i < cargoes.size(); i++) {
				price = 0;
				List<AddressVO> addresses = cargoes.get(i).getAddresses();
				for (int j = 0; j < addresses.size(); j++) {
					List<ItemVO> items = addresses.get(j).getItems();
					for (int k = 0; k < items.size(); k++)
						price = price + items.get(k).getPrice() * items.get(k).getQuantity();
				}
				cargoes.get(i).setTotalValue(price);
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

	public void selected(Long cargoId) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("cargoId", cargoId);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../secured/map.xhtml");
	}

}
