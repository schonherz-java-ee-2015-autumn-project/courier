package hu.schonherz.java.training.courier.webservice.beans;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import hu.schonherz.java.training.courier.service.CargoService;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.webservice.CourierWebService;
import hu.schonherz.java.training.courier.webservice.CourierWebServiceImplService;

@ManagedBean(name="cargoListBean")
@ViewScoped
public class CargoListBean implements Serializable {
	private static final Logger logger = Logger.getLogger(CargoListBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	CargoService cargoService;
	
	private List<CargoVO> cargoList;

	public List<CargoVO> getCargoList() {
		return cargoList;
	}

	public void setCargoList(List<CargoVO> cargoList) {
		this.cargoList = cargoList;
	}
	
	public void updateCargos(Long status) {
		
		try {
			List<CargoVO> cargosInDb = cargoService.findAllByStatus(status);
			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	public void getCargos() {
		URL wsdl = null;
		try {
			wsdl = new URL(
					"http://localhost:8088/mockCourierWebServiceImplPortBinding?WSDL");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		QName qName = new QName("http://webservice.courier.training.java.schonherz.hu/","CourierWebServiceImplService");
		CourierWebServiceImplService courierWebService = new CourierWebServiceImplService(wsdl, qName);
		CourierWebService serviceImpl = courierWebService.getCourierWebServiceImplPort();
		//System.out.println("Getting cargo list from webservice, right now we are mocking.");
		logger.info("LOG: Getting cargos list from webservice, right now we are mocking.");
		setCargoList(serviceImpl.getFreeCargos());
	}

}
