package hu.schonherz.java.training.courier.webservice.beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.UserVO;
import hu.schonherz.java.training.courier.service.webservice.UserWebServiceLocal;

@ManagedBean(name = "userListBean")
@ViewScoped
public class UserListBean implements Serializable {
	private static final Logger logger = Logger.getLogger(UserListBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	UserWebServiceLocal userWebServiceLocal;
	
	@EJB
	UserServiceLocal userServiceLocal;
	private List<UserVO> userList;

	public void getUsers() {
		try {
			userWebServiceLocal.getUsersFromAdministration();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			setUserList(userServiceLocal.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<UserVO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserVO> userList) {
		this.userList = userList;
	}

}
