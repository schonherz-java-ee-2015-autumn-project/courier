package hu.schonherz.java.training.courier.service.impl;

import java.rmi.RemoteException;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.RemoteHome;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.java.training.courier.service.SchedulerServiceLocal;
import hu.schonherz.java.training.courier.service.SchedulerServiceRemote;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@Stateless(name = "SchedulerService")
@RemoteHome(SchedulerServiceLocal.class)
@Remote(SchedulerServiceRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@EJB(name = "hu.schonherz.service.UserServiceLocal", beanInterface = UserServiceLocal.class)
public class SchedulerServiceImpl implements SessionBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// @Resource(lookup="java:global.ear.service-0.0.1.UserServiceImpl!hu.schonherz.java.training.courier.service.UserServiceLocal")
	@EJB
	UserServiceLocal userService;

	public void getUsersViaWebService() throws RemoteException {
		System.out.println("Getting user from WebService with Quartz");
		List<UserVO> users = null;
		try {
			users = userService.findAll();
		} catch (Exception e) {
			System.out.println("Error while trying to run userService.findAll()...");
			e.printStackTrace();
		}
	}

	public void getFreeCargosViaWebService() throws RemoteException {
		System.out.println("Gettin free cargos from WebService with Quartz");

	}

	@Override
	public void ejbActivate() throws EJBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void ejbPassivate() throws EJBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void ejbRemove() throws EJBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSessionContext(SessionContext arg0) throws EJBException {
		// TODO Auto-generated method stub

	}

}