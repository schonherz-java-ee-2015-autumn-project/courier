package hu.schonherz.java.training.courier.service.webservice;

import hu.schonherz.java.training.courier.entities.CargoStatus;

public interface CargoWebServiceLocal {
	void getFreeCargosFromAdministration() throws Exception;
	
	Long setCargoStatus(Long globalId, CargoStatus status) throws Exception;
}