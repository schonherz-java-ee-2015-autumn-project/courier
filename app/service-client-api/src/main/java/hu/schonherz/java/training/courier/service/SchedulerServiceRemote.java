package hu.schonherz.java.training.courier.service;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface SchedulerServiceRemote extends EJBObject{
	void getUsersViaWebService() throws RemoteException;

	void getFreeCargosViaWebService() throws RemoteException;

}
