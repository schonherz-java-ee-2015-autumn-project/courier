package hu.schonherz.java.training.courier.webservice.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.administrator.CourierService;
import hu.schonherz.administrator.CourierServiceImpl;
import hu.schonherz.administrator.InvalidDateException_Exception;
import hu.schonherz.administrator.RemoteCargoDTO;
import hu.schonherz.administrator.RemoteCargoState;
import hu.schonherz.administrator.SynchronizationService;
import hu.schonherz.administrator.SynchronizationServiceImpl;
import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.webservice.CargoWebServiceLocal;
import hu.schonherz.java.training.courier.service.webservice.CargoWebServiceRemote;
import hu.schonherz.java.training.courier.webservice.converters.RemoteCargoConverter;
import hu.schonherz.java.training.courier.webservice.converters.RemoteCargoStateConverter;
import hu.schonherz.java.training.courier.webservice.converters.RemoteDeliveryStateConverter;
import hu.schonherz.java.training.courier.webservice.converters.RemotePaymentConverter;

@Stateless(mappedName = "cargoWebService")
@Local(CargoWebServiceLocal.class)
@Remote(CargoWebServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class CargoWebServiceImpl implements CargoWebServiceLocal, CargoWebServiceRemote {

	private final static Logger logger = Logger.getLogger(CargoWebServiceImpl.class);

	@EJB
	CargoServiceLocal cargoServiceLocal;

	@EJB(beanName = "remoteCargoConverter")
	RemoteCargoConverter remoteCargoConveter;

	private String courierUrl;
	private String synchronizationUrl;
	private String namespaceURI;
	private String courierLocalPart;
	private String synchronizationLocalPart;
	private Properties wsdlProperties;

	private CourierServiceImpl courierServiceImpl;
	private CourierService courierService;

	private SynchronizationServiceImpl synchronizationServiceImpl;
	private SynchronizationService synchronizationService;

	@PostConstruct
	void init() {
		if (remoteCargoConveter == null) {
			logger.info("INFO:remoteCargoConveter is null!");
		}
		wsdlProperties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			wsdlProperties.load(classLoader.getResourceAsStream("wsdllocation.properties"));
		} catch (IOException e1) {
			logger.info("Error:", e1);
		}

		courierUrl = wsdlProperties.getProperty("courier.service.url");
		courierLocalPart = wsdlProperties.getProperty("courier.localPart");

		synchronizationUrl = wsdlProperties.getProperty("synchronization.service.url");
		synchronizationLocalPart = wsdlProperties.getProperty("synchronization.localPart");

		namespaceURI = wsdlProperties.getProperty("namespaceURI");

		logger.info("INFO: WS -> COURIERURL:" + courierUrl);
		logger.info("INFO: WS -> COURIERLocalPart:" + courierLocalPart);

		logger.info("INFO: WS -> SYNCHRONIUAZIONUrl:" + synchronizationUrl);
		logger.info("INFO: WS -> SYNCHRONIUAZIONLocalPart:" + synchronizationLocalPart);

		logger.info("INFO: WS -> namespaceURI:" + namespaceURI);

		URL courierWsdl = null;
		URL synchronizationWsdl = null;

		try {
			courierWsdl = new URL(courierUrl);
			synchronizationWsdl = new URL(synchronizationUrl);
		} catch (MalformedURLException e) {
			logger.info("Error:", e);
		}

		QName courierqName = new QName(namespaceURI, courierLocalPart);
		QName synchronizationqName = new QName(namespaceURI, synchronizationLocalPart);
		// csatlakozunk a webServicehez.
		courierServiceImpl = new CourierServiceImpl(courierWsdl, courierqName);
		setCourierService(courierServiceImpl.getCourierServiceImplPort());
		// csatlakozunk a synchronization WebServicehez
		setSynchronizationServiceImpl(new SynchronizationServiceImpl(synchronizationWsdl, synchronizationqName));
		setSynchronizationService(getSynchronizationServiceImpl().getSynchronizationServiceImplPort());
	}

	@Override
	public void getFreeCargosFromAdministration() throws Exception {
		// courierWebService majd lekéri az admin modultól a szállításokat.
		logger.info("INFO: Asking for cargos with Web Service");

		GregorianCalendar gregorianDate = new GregorianCalendar();
		gregorianDate.setTime(new Date());
		gregorianDate.add(Calendar.HOUR, -1);
		XMLGregorianCalendar dateForCargo = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianDate);
		logger.info("INFO: Date:" + gregorianDate.getTime());

		List<CargoVO> cargosInDB = cargoServiceLocal.findAll();
		logger.info("INFO: Cargos in db size:" + cargosInDB.size());
		List<RemoteCargoDTO> cargosInWS = getSynchronizationService().getCargosByDate(dateForCargo);
		logger.info("INFO: Cargos with Web Service was asked, its size:" + cargosInWS.size());
		updateCargos(cargosInDB, cargosInWS);

	}

	public void updateCargos(List<CargoVO> cargosInDB, List<RemoteCargoDTO> cargosInWS) {
		logger.info("INFO: Updating cargos");
		Integer newCargos = 0, updatedCargos = 0;
		CargoVO newCargo;
		CargoVO existingCargo;
		List<Long> existingIds = new ArrayList<>();
		for (CargoVO dbCargo : cargosInDB) {
			existingIds.add(dbCargo.getGlobalid());
//			logger.info("globalId:" + dbCargo.getGlobalid());
		}

		// amint megkaptuk az admin modultól az implementációt azonnal
		// beiktatjuk
		for (RemoteCargoDTO wsCargo : cargosInWS) {
			newCargo = remoteCargoConveter.toLocalVO(wsCargo);
			if (!existingIds.contains((Long) wsCargo.getId()) && !wsCargo.isIsDeleted()) {
				try {

					try {

						newCargo.setRegdate(new Date());
						newCargo.setModdate(new Date());
						cargoServiceLocal.save(newCargo);
						newCargos++;

					} catch (Exception e) {
						logger.info("Error something went wrong while trying to save new cargo!");
						logger.info("ERROR", e);
					}

				} catch (Exception e) {
					logger.error("Error:" + e.getMessage());
				}
			} else {
				try {
					try {
						existingCargo = cargoServiceLocal.findCargoByGlobalid(wsCargo.getId());
						if (!existingCargo.equals(newCargo)) {
//							logger.info("A cargo should be updated!");
//							logger.info("Cargo status:" + newCargo.getStatus());
							cargoServiceLocal.updateCargoByGlobalId(newCargo);
						}
						updatedCargos++;
					} catch (Exception e) {
						logger.info("Error something went wrong while trying to update an existing cargo!");
						logger.info("ERROR", e);
					}

				} catch (Exception e) {
					logger.error("Error:", e);
				}
			}
		}
		logger.info("Update Success! New Cargos: " + newCargos + " Updated cargos:" + updatedCargos);
	}

	@Override
	public Long assignUserToCargo(Long userId, Long cargoId) throws Exception {
		try {
			getCourierService().assignCargoToCourier(cargoId, userId);
			return (long) 0;
		} catch (Exception e) {
			logger.info("ERROR:", e);
			return (long) 1;
		}

	}

	public String getSynchronizationUrl() {
		return synchronizationUrl;
	}

	public void setSynchronizationUrl(String synchronizationUrl) {
		this.synchronizationUrl = synchronizationUrl;
	}

	public String getSynchronizationLocationUrl() {
		return synchronizationLocalPart;
	}

	public void setSynchronizationLocationUrl(String synchronizationLocationUrl) {
		this.synchronizationLocalPart = synchronizationLocationUrl;
	}

	@Override
	public void testEJB() {
		logger.info("INFO: This log came from cargoWebService EJB!");

	}

	@Override
	public Long changeDeliveryState(Long addressId, Long userId, AddressStatus status) throws Exception {

		try {
			getCourierService().changeDeliveryState(addressId, userId,
					RemoteDeliveryStateConverter.toRemoteState(status));
			return (long) 0;
		} catch (Exception e) {
			logger.info("ERROR:", e);
			return (long) 1;
		}

	}

	@Override
	public Long changePaymentState(Long userId, Long addressId, Payment payment) throws Exception {
		try {
			getCourierService().changePaymentState(userId, addressId, RemotePaymentConverter.toRemotePayment(payment));
			return (long) 0;
		} catch (Exception e) {
			logger.info("ERROR:", e);
			return (long) 1;
		}
	}

	@Override
	public Long changeCargoState(Long cargoId, Long userId, CargoStatus cargoStatus) throws Exception {
		try {
			RemoteCargoState remoteState = RemoteCargoStateConverter.toRemoteCargoState(cargoStatus);
			logger.info("INFO: CargoState is " + remoteState.toString());
			getCourierService().changeCargoState(cargoId, userId, remoteState);
			return (long) 0;
		} catch (Exception e) {
			logger.info("ERROR:", e);
			return (long) 1;
		}
	}

	public SynchronizationService getSynchronizationService() {
		return synchronizationService;
	}

	public void setSynchronizationService(SynchronizationService synchronizationService) {
		this.synchronizationService = synchronizationService;
	}

	public SynchronizationServiceImpl getSynchronizationServiceImpl() {
		return synchronizationServiceImpl;
	}

	public void setSynchronizationServiceImpl(SynchronizationServiceImpl synchronizationServiceImpl) {
		this.synchronizationServiceImpl = synchronizationServiceImpl;
	}

	public CourierService getCourierService() {
		return courierService;
	}

	public void setCourierService(CourierService courierService) {
		this.courierService = courierService;
	}

	@Override
	public List<CargoVO> getCargosListFromWebService() {
		GregorianCalendar gregorianDate = new GregorianCalendar();
		gregorianDate.setTime(new Date());
		gregorianDate.add(Calendar.HOUR, -1);
		XMLGregorianCalendar dateForCargo = null;
		try {
			dateForCargo = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianDate);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		try {
			return remoteCargoConveter.toLocalVO(synchronizationService.getCargosByDate(dateForCargo));
		} catch (InvalidDateException_Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
