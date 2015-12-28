package hu.schonherz.java.training.courier.service;

import hu.schonherz.java.training.courier.entities.CargoStatus;

public interface WebServiceClientRemote {
	public void getUsersFromWebService();

	public void getCargos();

	public Long setCargoStatus(Long globalId,CargoStatus cargoStatus);
	
}
