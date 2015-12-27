package hu.schonherz.java.training.courier.webservice.client.impl;

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
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.WebServiceClientLocal;
import hu.schonherz.java.training.courier.service.WebServiceClientRemote;
import hu.schonherz.java.training.courier.service.converter.UserConverter;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;
import hu.schonherz.java.training.courier.webservice.CourierWebService;
import hu.schonherz.java.training.courier.webservice.CourierWebServiceImplService;

@Stateless
@Local(WebServiceClientLocal.class)
@Remote(WebServiceClientRemote.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class WebServiceClientImpl implements WebServiceClientLocal, WebServiceClientRemote {

	private String url;
	private String namespaceURI;
	private String localPart;
	private Properties wsdlProperties;
	private final static Logger logger = Logger.getLogger(WebServiceClientImpl.class);
	private List<UserVO> userListFromDB;
	private List<UserVO> userListFromWS;
	
	private List<CargoVO> cargoList;
	private List<CargoVO> cargoListFromDB;
	private List<CargoVO> cargoListFromWS;
	private CourierWebServiceImplService courierWebService;
	private CourierWebService serviceImpl;
	@EJB
	UserServiceLocal userService;

	@PostConstruct
	public void init() {
		logger.info("Starting WebServiceClient as EJB service");
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
		logger.info("INFO: WS -> URL:" + url);
		logger.info("INFO: WS -> namespaceURI:" + namespaceURI);
		logger.info("INFO: WS -> localPart:" + localPart);

		URL wsdl = null;
		try {
			wsdl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		QName qName = new QName(namespaceURI, localPart);

		courierWebService = new CourierWebServiceImplService(wsdl, qName);
		serviceImpl = courierWebService.getCourierWebServiceImplPort();
	}

	/**
	 * Az adatb�zisban m�r megl�v� userek friss�t�s�re szolg�l, a l�nyeg az hogy
	 * amik j�nnek felhasz�l�k az � ID-j�k 0 legyen, k�l�nben a saveUsers
	 * met�dus is egy update lesz.
	 */
	private void updateUsers() {
		Integer updatedUsers = 0;
		for (UserVO dbUser : getUserListFromDB()) {
			for (UserVO wsUser : getUserListFromWS()) {
				// logger.info("INFO:"+wsUser.toString());
				if (dbUser.getGlobalid() == wsUser.getGlobalid() && wsUser.getModdate().after(dbUser.getModdate())) {
					try {
						userService.updateUserByGlobalId(wsUser);
						updatedUsers++;
					} catch (Exception e) {
						logger.info("Error:" + e.getMessage());
					}
				}
			}
		}
		logger.info("INFO: Number of updated users:" + updatedUsers);
	}

	/**
	 * Az adatb�zisban m�g nem szerepl� felhaszn�l�k ment�s�re szolg�l.
	 */
	private void saveNewUsers() {
		Integer newUsers = 0;

		UserVO newUser;
		List<Long> existingIds = new ArrayList<>();
		for (UserVO dbUser : getUserListFromDB()) {
			existingIds.add(dbUser.getGlobalid());
		}

		for (UserVO wsUser : getUserListFromWS()) {
			if (!existingIds.contains((Long) wsUser.getGlobalid())) {
				try {
					newUser = userService.save(wsUser);
					newUsers++;
				} catch (Exception e) {
					logger.error("Error:" + e.getMessage());
				}
			}
		}
		logger.info("INFO: Number of new users:" + newUsers);
	}

	/**
	 * Ezzel fogjuk lek�rni a Web Service-t�l az adatokat megadott
	 * pillanatokban.
	 */
	public void getUsersFromWebService() {
		logger.info("INFO: Getting users list from webservice, right now we are mocking.");
		setUserListFromWS(UserConverter.toVoFromWS(serviceImpl.getUsersList()));
		try {
			setUserListFromDB(userService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateUsers();
		saveNewUsers();
	}

	private void updateCargos() {
		Integer newCargos = 0;
		CargoVO newCargo;
		List<Long> existingIds = new ArrayList<>();
		for(CargoVO dbCargo : cargoListFromDB) {
			existingIds.add(dbCargo.getGlobalid());
		}
		
		for(CargoVO wsCargo : cargoListFromWS) {
			
			if(!existingIds.contains((Long) wsCargo.getGlobalid())) {
				try {
				//	newCargo = cargoService.save(wsCargo);
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
		
		/*System.out.println("CargoListBean: getCargos");
		
		logger.info("LOG: Getting cargos list from webservice, right now we are mocking.");
		setCargoListFromWS(serviceImpl.getFreeCargosList());
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
		setCargoList(getCargoListFromDB());*/
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}

	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}

	public String getLocalPart() {
		return localPart;
	}

	public void setLocalPart(String localPart) {
		this.localPart = localPart;
	}

	public Properties getWsdlProperties() {
		return wsdlProperties;
	}

	public void setWsdlProperties(Properties wsdlProperties) {
		this.wsdlProperties = wsdlProperties;
	}

	public List<UserVO> getUserListFromDB() {
		return userListFromDB;
	}

	public void setUserListFromDB(List<UserVO> userListFromDB) {
		this.userListFromDB = userListFromDB;
	}

	public List<UserVO> getUserListFromWS() {
		return userListFromWS;
	}

	public void setUserListFromWS(List<UserVO> userListFromWS) {
		this.userListFromWS = userListFromWS;
	}

	@Override
	public Long setCargoStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserVO> getUserList() {
		getUsersFromWebService();
		return getUserListFromWS();
	}
	
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

}
