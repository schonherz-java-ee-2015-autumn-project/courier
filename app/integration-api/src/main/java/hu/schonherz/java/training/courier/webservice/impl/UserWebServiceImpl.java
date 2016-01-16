package hu.schonherz.java.training.courier.webservice.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.administrator.NotAllowedRoleException_Exception;
import hu.schonherz.administrator.SynchronizationService;
import hu.schonherz.administrator.SynchronizationServiceImpl;
import hu.schonherz.administrator.UserRole;
import hu.schonherz.administrator.WebUserDTO;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.UserVO;
import hu.schonherz.java.training.courier.service.webservice.UserWebServiceLocal;
import hu.schonherz.java.training.courier.service.webservice.UserWebServiceRemote;

@Stateless(mappedName = "userWebService")
@Local(UserWebServiceLocal.class)
@Remote(UserWebServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
// @Interceptors({ SpringBeanAutowiringInterceptor.class })
public class UserWebServiceImpl implements UserWebServiceLocal, UserWebServiceRemote {

	private final static Logger logger = Logger.getLogger(UserWebServiceImpl.class);
	private final static UserRole ROLE = UserRole.COURIER;
	@EJB
	UserServiceLocal userServiceLocal;

	private String url;
	private String namespaceURI;
	private String localPart;
	private Properties wsdlProperties;

	private SynchronizationServiceImpl synchServiceImpl;
	private SynchronizationService synchService;

	@PostConstruct
	void init() {
		logger.info("Starting WebServiceClient as EJB service");
		wsdlProperties = new Properties();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			wsdlProperties.load(classLoader.getResourceAsStream("wsdllocation.properties"));
		} catch (IOException e1) {
			logger.info("Error:", e1);
		}

		url = wsdlProperties.getProperty("synchronization.service.url");
		namespaceURI = wsdlProperties.getProperty("namespaceURI");
		localPart = wsdlProperties.getProperty("synchronization.localPart");
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

		synchServiceImpl = new SynchronizationServiceImpl(wsdl, qName);
		synchService = synchServiceImpl.getSynchronizationServiceImplPort();
	}

	@Override
	public void getUsersFromAdministration() throws Exception {
		logger.info("INFO:Trying to get users from WebService!");

//		 GregorianCalendar gregorianDate = new GregorianCalendar();
//		 gregorianDate.setTime(userServiceLocal.findLastModDate());
//		 XMLGregorianCalendar lastModDate =
//		 DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianDate);
//		
//		 logger.info("INFO: lastModDate:" + gregorianDate.getTime());

		try {
			List<WebUserDTO> usersInWS = synchService.getUsersByRole(ROLE);
			List<UserVO> usersInDB = userServiceLocal.findAll();
			updateUsers(usersInDB, usersInWS);
			saveNewUsers(usersInDB, usersInWS);
		} catch (NotAllowedRoleException_Exception e) {
			logger.info("ERROR:", e);
		}

	}

	public UserVO convertToUserVO(WebUserDTO userDTO) {
		UserVO userVO = new UserVO();
		userVO.setId((long) 0);
		userVO.setGlobalid(userDTO.getId());
		userVO.setFullname(userDTO.getName());
		userVO.setUsername(userDTO.getUsername());
		userVO.setPassword(userDTO.getPassword());
		userVO.setModdate(userDTO.getModdate().toGregorianCalendar().getTime());
		userVO.setRemoved(userDTO.isRemove());
		return userVO;
	}

	private void updateUsers(List<UserVO> usersInDB, List<WebUserDTO> usersInWS) {
		logger.info("INFO:updateUsers");
		Integer updatedUsers = 0;
		int globalId, id;
		for (UserVO dbUser : usersInDB) {
			for (WebUserDTO wsUser : usersInWS) {
				globalId = dbUser.getGlobalid() == null ? 0 : dbUser.getGlobalid().intValue();
				id = wsUser.getId().intValue();
				if (globalId == id) {
					try {
						UserVO updatableUser = userServiceLocal.findByGlobalId(wsUser.getId());
						updatableUser = convertToUserVO(wsUser);
						userServiceLocal.updateUserByGlobalId(updatableUser);
						updatedUsers++;
					} catch (Exception e) {
						logger.info("Error:", e);
					}
				}
			}
		}
		logger.info("INFO: Number of updated users:" + updatedUsers);
	}

	private void saveNewUsers(List<UserVO> usersInDB, List<WebUserDTO> usersInWS) {
		Integer newUsers = 0;
		UserVO newUser;
		List<Long> existingIds = new ArrayList<>();
		for (UserVO dbUser : usersInDB) {
			existingIds.add(dbUser.getGlobalid());
		}

		for (WebUserDTO wsUser : usersInWS) {
			if (!existingIds.contains((Long) wsUser.getId())) {
				try {
					newUser = userServiceLocal.save(convertToUserVO(wsUser));
					newUsers++;
				} catch (Exception e) {
					logger.error("Error:", e);
				}
			}
		}
		logger.info("INFO: Number of new users:" + newUsers);
	}

}
