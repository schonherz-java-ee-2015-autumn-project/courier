package hu.schonherz.java.training.courier.webservice.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.vo.CargoVO;

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

	public List<CargoVO> getCargoList() {
		return cargoList;
	}

	public void setCargoList(List<CargoVO> cargoList) {
		this.cargoList = cargoList;
	}

	/*
	 * public void updateCargos(Long status) {
	 * 
	 * try { List<CargoVO> cargosInDb = cargoService.findAllByStatus(status);
	 * 
	 * } catch (Exception e) { logger.info(e.getMessage()); } }
	 */
	public void getCargos() {
		URL wsdl = null;
		try {
			wsdl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// QName qName = new QName(namespaceURI, localPart);
		// CourierWebServiceImplService courierWebService = new
		// CourierWebServiceImplService(wsdl, qName);
		// CourierWebService serviceImpl =
		// courierWebService.getCourierWebServiceImplPort();
		logger.info("LOG: Getting cargos list from webservice, right now we are mocking.");
		// webServiceClientLocal.getCargos();
		// setCargoList();
		// setCargoList(serviceImpl.getFreeCargos());
	}

}
