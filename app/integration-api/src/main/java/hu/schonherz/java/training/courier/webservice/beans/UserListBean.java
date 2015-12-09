package hu.schonherz.java.training.courier.webservice.beans;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.namespace.QName;

import hu.schonherz.java.training.courier.service.vo.UserVO;
import hu.schonherz.java.training.courier.webservice.CourierWebService;
import hu.schonherz.java.training.courier.webservice.CourierWebServiceImplService;
import hu.schonherz.java.training.courier.webservice.impl.CourierWebServiceImpl;

@ManagedBean(name="userListBean")
@ViewScoped
public class UserListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<UserVO> userList;

	public List<UserVO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserVO> userList) {
		this.userList = userList;
	}
	
	public void getUsers() {
		
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
			System.out.println("Getting user list from webservice, right now we are mocking.");
			setUserList(serviceImpl.getUsers());
	}
	
}
