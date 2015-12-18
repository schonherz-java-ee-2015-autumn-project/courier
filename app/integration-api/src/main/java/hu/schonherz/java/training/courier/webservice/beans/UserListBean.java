package hu.schonherz.java.training.courier.webservice.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import hu.schonherz.java.training.courier.service.UserService;
import hu.schonherz.java.training.courier.service.vo.UserVO;
import hu.schonherz.java.training.courier.webservice.CourierWebService;
import hu.schonherz.java.training.courier.webservice.CourierWebServiceImplService;

@ManagedBean(name="userListBean")
@ViewScoped
public class UserListBean implements Serializable {
	private static final Logger logger = Logger.getLogger(UserListBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{userService}")
	private
	UserService userService;
	
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
	
	private List<UserVO> userList;

	public List<UserVO> getUserList() {
		return userList;
	}
	
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
	
	public void setUserList(List<UserVO> userList) {
		this.userList = userList;
	}
	
	
	public void updateUsers() {
		
		try {
			List<UserVO> usersInDb = getUserService().findAll();
			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	public void getUsers() {
		
			URL wsdl = null;
			try {
				wsdl = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			QName qName = new QName(namespaceURI,localPart);
			CourierWebServiceImplService courierWebService = new CourierWebServiceImplService(wsdl, qName);
			CourierWebService serviceImpl = courierWebService.getCourierWebServiceImplPort();
			//System.out.println("Getting user list from webservice, right now we are mocking.");
			logger.info("LOG: Getting users list from webservice, right now we are mocking."); 
			setUserList(serviceImpl.getUsers());
//			writeUsersToConsole();
//			try {
//				setUserList(getUserService().findAll());
//			} catch (Exception e) {
//			
//			}
//			writeUsersToConsole();
	}
	
	public void writeUsersToConsole() {
		for(UserVO user:userList) {
			System.out.println(user.toString());
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
}
