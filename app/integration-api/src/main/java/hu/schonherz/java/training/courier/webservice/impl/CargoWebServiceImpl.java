package hu.schonherz.java.training.courier.webservice.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.webservice.CargoWebServiceLocal;
import hu.schonherz.java.training.courier.service.webservice.CargoWebServiceRemote;

@Stateless(mappedName = "userWebService")
@Local(CargoWebServiceLocal.class)
@Remote(CargoWebServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class CargoWebServiceImpl implements CargoWebServiceLocal, CargoWebServiceRemote {

	private final static Logger logger = Logger.getLogger(CargoWebServiceImpl.class);

	@EJB
	CargoServiceLocal cargoServiceLocal;

	private String url;
	private String namespaceURI;
	private String localPart;
	private Properties wsdlProperties;

	@PostConstruct
	void init() {

		wsdlProperties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			wsdlProperties.load(classLoader.getResourceAsStream("wsdllocation.properties"));
		} catch (IOException e1) {
			logger.info("Error:", e1);
		}

		url = wsdlProperties.getProperty("url");
		namespaceURI = wsdlProperties.getProperty("namespaceURI");
		localPart = wsdlProperties.getProperty("localPart");
		logger.info("INFO: WS -> URL:" + url);
		logger.info("INFO: WS -> namespaceURI:" + namespaceURI);
		logger.info("INFO: WS -> localPart:" + localPart);

		URL wsdl = null;
		try {
			wsdl = new URL(url);
		} catch (MalformedURLException e) {
			logger.info("Error:", e);
		}

		QName qName = new QName(namespaceURI, localPart);
	}

	@Override
	public void getFreeCargosFromAdministration() throws Exception {
		// courierWebService majd lekéri az admin modultól a szállításokat.
		List<CargoVO> cargosInDB = cargoServiceLocal.findAllByStatus(CargoStatus.getValue(1L));
		updateCargos(cargosInDB);
	}

	@Override
	public Long setCargoStatus(Long globalId, CargoStatus status) throws Exception {
		//Ide jön majd a státusz update az admin moduk felé, egyenlõre hogy ne legyen baj minden sikeres
		return (long) 0;
	}

	public void updateCargos(List<CargoVO> cargosInDB /*,List<CargoWebVO> cargosInWS*/ ) {
		Integer newCargos = 0;
		CargoVO newCargo;
		
		List<Long> existingIds = new ArrayList<>();
		for (CargoVO dbCargo : cargosInDB) {
			existingIds.add(dbCargo.getGlobalid());
		}
		
		// amint megkaptuk az admin modultól az implementációt azonnal beiktatjuk
//		for (CargoVO wsCargo : cargoListFromWS) {
//
//			if (!existingIds.contains((Long) wsCargo.getGlobalid())) {
//				try {
//					newCargo = cargoServiceLocal.save(wsCargo);
//					newCargos++;
//				} catch (Exception e) {
//					e.printStackTrace();
//					logger.error("Error:" + e.getMessage());
//				}
//			}
//		}
//		System.out.println("Update Success! New Cargos: " + newCargos);
	}

}
