package hu.schonherz.java.training.courier.webservice.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.webservice.CourierWebService;
import hu.schonherz.java.training.courier.webservice.CourierWebServiceImplService;


@ManagedBean(name = "cargoListBean")
@ViewScoped
public class CargoListBean implements Serializable {
	private static final Logger logger = Logger.getLogger(CargoListBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Properties wsdlProperties;

	private String url;
	private String namespaceURI;
	private String localPart;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNamespaceUri() {
		return namespaceURI;
	}

	public void setNamespaceUri(String namespaceUri) {
		this.namespaceURI = namespaceUri;
	}

	public String getLocalPart() {
		return localPart;
	}

	public void setLocalPart(String localPart) {
		this.localPart = localPart;
	}

	@EJB
	CargoServiceLocal cargoService;

	@PostConstruct
	public void init() {
		System.out.println("CargoListBean: init");
		wsdlProperties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			wsdlProperties.load(classLoader.getResourceAsStream("wsdllocation.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		url = wsdlProperties.getProperty("url");
		namespaceURI = wsdlProperties.getProperty("namespaceURI");
		localPart = wsdlProperties.getProperty("localPart");
	}

	private List<CargoVO> cargoList;
	private List<CargoVO> cargoListFromDB;
	private List<CargoVO> cargoListFromWS;

	public List<CargoVO> getCargoList() {
		return cargoList;
	}

	public void setCargoList(List<CargoVO> cargoList) {
		this.cargoList = cargoList;
	}

	public List<CargoVO> getCargoListFromDB() {
		return cargoListFromDB;
	}

	public void setCargoListFromDB(List<CargoVO> cargoListFromDB) {
		this.cargoListFromDB = cargoListFromDB;
	}

	public List<CargoVO> getCargoListFromWS() {
		return cargoListFromWS;
	}

	public void setCargoListFromWS(List<CargoVO> cargoListFromWS) {
		this.cargoListFromWS = cargoListFromWS;
	}

	public void updateCargos() {
		Integer newCargos = 0;
		CargoVO newCargo;
		List<Long> existingIds = new ArrayList<>();
		for(CargoVO dbCargo : cargoListFromDB) {
			existingIds.add(dbCargo.getGlobalid());
		}
		
		for(CargoVO wsCargo : cargoListFromWS) {
			
			if(!existingIds.contains((Long) wsCargo.getGlobalid())) {
				try {
					newCargo = cargoService.save(wsCargo);
					newCargos++;
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error:" + e.getMessage());
				}
			}
		}
		System.out.println("Update Success! New Cargos: " + newCargos);
	}

	public void getCargos() {
		System.out.println("CargoListBean: getCargos");
		URL wsdl = null;
		try {
			wsdl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		QName qName = new QName(namespaceURI, localPart);
		CourierWebServiceImplService courierWebService = new CourierWebServiceImplService(wsdl, qName);
		CourierWebService serviceImpl = courierWebService.getCourierWebServiceImplPort();
		logger.info("LOG: Getting cargos list from webservice, right now we are mocking.");
		setCargoListFromWS(serviceImpl.getFreeCargos());
		try {
			setCargoListFromDB(cargoService.findAllByStatus(CargoStatus.getValue(1L)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateCargos();
		try {
			setCargoListFromDB(cargoService.findAllByStatus(CargoStatus.getValue(1L)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setCargoList(getCargoListFromDB());
	}

}
