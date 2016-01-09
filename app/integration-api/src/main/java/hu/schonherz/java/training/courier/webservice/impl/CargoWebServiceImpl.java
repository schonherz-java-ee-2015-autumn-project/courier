package hu.schonherz.java.training.courier.webservice.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.administrator.CourierService;
import hu.schonherz.administrator.CourierServiceImpl;
import hu.schonherz.administrator.RemoteCargoDTO;
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
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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
		courierService = courierServiceImpl.getCourierServiceImplPort();
		// csatlakozunk a synchronization WebServicehez
		synchronizationServiceImpl = new SynchronizationServiceImpl(synchronizationWsdl, synchronizationqName);
		synchronizationService = synchronizationServiceImpl.getSynchronizationServiceImplPort();
	}

	@Override
	public void getFreeCargosFromAdministration() throws Exception {
		// courierWebService majd lekéri az admin modultól a szállításokat.
		logger.info("INFO: Asking for cargos with Web Service");
		List<CargoVO> cargosInDB = cargoServiceLocal.findAllByStatus(CargoStatus.getValue(1L));

		GregorianCalendar gregorianDate = new GregorianCalendar();
		gregorianDate.setTime(new Date());
		XMLGregorianCalendar dateForCargo = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianDate);

		List<RemoteCargoDTO> cargosInWS = synchronizationService.getCargosByDate(dateForCargo);
		logger.info("INFO: Cargos with Web Service was asked, its size:" + cargosInWS.size());
		updateCargos(cargosInDB, cargosInWS);

	}

	@Override
	public Long setCargoStatus(Long globalId, CargoStatus status) throws Exception {
		// Ide jön majd a státusz update az admin moduk felé, egyenlõre hogy ne
		// legyen baj minden sikeres

		return (long) 0;
	}

	public void updateCargos(List<CargoVO> cargosInDB, List<RemoteCargoDTO> cargosInWS) {
		logger.info("INFO: Updating cargos");
		Integer newCargos = 0;
		CargoVO newCargo;

		List<Long> existingIds = new ArrayList<>();
		for (CargoVO dbCargo : cargosInDB) {
			existingIds.add(dbCargo.getGlobalid());
		}

		// amint megkaptuk az admin modultól az implementációt azonnal
		// beiktatjuk
		for (RemoteCargoDTO wsCargo : cargosInWS) {

			if (!existingIds.contains((Long) wsCargo.getId())) {
				try {

					newCargo = remoteCargoConveter.toLocalVO(wsCargo);
					try {
						cargoServiceLocal.save(newCargo);
					} catch (Exception e) {
						logger.info("Error something went wrong while trying to save new cargo!");
						logger.info("ERROR", e);
					}
					logger.info("INFO: GloblId:" + newCargo.getGlobalid());
					logger.info("INFO: Status:" + newCargo.getStatus());
					logger.info("INFO: TotalValue:" + newCargo.getTotalValue());
					for (AddressVO address : newCargo.getAddresses()) {
						logger.info("INFO:" + address.getAddress());
					}
					newCargos++;
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error:" + e.getMessage());
				}
			}
		}
		System.out.println("Update Success! New Cargos: " + newCargos);
	}

	@Override
	public Long assignUserToCargo(Long userId, Long cargoId) throws Exception {
		try {
			courierService.assignCargoToCourier(cargoId, userId);
		} catch (Exception e) {
			logger.info("ERROR:", e);
			return (long) 1;
		}
		return (long) 0;
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
			courierService.changeDeliveryState(addressId, userId, RemoteDeliveryStateConverter.toRemoteState(status));
			return (long) 0;
		} catch (Exception e) {
			return (long) 1;
		}

	}

	@Override
	public Long changePaymentState(Long userId, Long addressId, Payment payment) throws Exception {
		try {
			courierService.changePaymentState(userId, addressId, RemotePaymentConverter.toRemotePayment(payment));
			return (long) 0;
		} catch (Exception e) {
			return (long) 1;
		}
	}

	@Override
	public Long changeCargoState(Long cargoId, Long userId, CargoStatus cargoStatus) throws Exception {
		try {
			courierService.changeCargoState(cargoId, userId, RemoteCargoStateConverter.toRemoteCargoState(cargoStatus));
			return (long) 0;
		} catch (Exception e) {
			return (long) 1;
		}
	}

}
