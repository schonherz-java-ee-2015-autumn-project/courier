package hu.schonherz.java.training.courier.webservice.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import hu.schonherz.java.training.courier.service.WebServiceClientLocal;
import hu.schonherz.java.training.courier.service.vo.UserVO;
import hu.schonherz.java.training.courier.webservice.CourierWebService;
import hu.schonherz.java.training.courier.webservice.CourierWebServiceImplService;

@ManagedBean(name = "userListBean")
@ViewScoped
public class UserListBean implements Serializable {
	private static final Logger logger = Logger.getLogger(UserListBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	WebServiceClientLocal webServiceClientLocal;

	private Properties wsdlProperties;

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

	private String url;
	private String namespaceURI;
	private String localPart;

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

	public List<UserVO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserVO> userList) {
		this.userList = userList;
	}

	private List<UserVO> userListFromDB;
	private List<UserVO> userListFromWS;
	private List<UserVO> userList;

	@PostConstruct
	public void init() {

		System.out.println("init method()");
		System.out.println(new Date());
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
	}

	

	public void getUsers() {
		System.out.println("getUsers method()");
		URL wsdl = null;
		try {
			wsdl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

//		QName qName = new QName(namespaceURI, localPart);
//
//		CourierWebServiceImplService courierWebService = new CourierWebServiceImplService(wsdl, qName);
//		CourierWebService serviceImpl = courierWebService.getCourierWebServiceImplPort();
//		// System.out.println("Getting user list from webservice, right now we
//		// are mocking.");
//		logger.info("INFO: Getting users list from webservice, right now we are mocking.");
//		System.out.println("Getting users list from WebService...");
//		setUserListFromWS(serviceImpl.getUsers());
//		writeUsersToConsolefromWS();
//		try {
//			setUserListFromDB(getUserService().findAll());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		writeUsersToConsolefromDB();
//		
		setUserList(webServiceClientLocal.getUserList());
	}

	public void writeUsersToConsolefromDB() {

		logger.info("INFO:Writing users to Console from DataBase.");
		for (UserVO user : getUserListFromDB()) {
			System.out.println(user.toString());
		}
		setUserList(getUserListFromDB());
	}

	public void writeUsersToConsolefromWS() {
		logger.info("INFO:Writing users to Console from WS.");
		System.out.println("Writing users to Console from WS.");
		for (UserVO user : getUserListFromWS()) {
			System.out.println(user.toString());

		}
		setUserList(getUserListFromWS());
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}