package hu.schonherz.java.training.courier.service;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface SchedulerServiceLocal extends EJBHome{
	public SchedulerServiceRemote create() throws RemoteException, CreateException;	
}
