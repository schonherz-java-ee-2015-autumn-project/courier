package hu.schonherz.java.training.courier.service;

import java.util.List;

import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.vo.UserVO;

public interface WebServiceClientLocal {
	public void getUsersFromWebService();

	public void getCargos();
	
	public Long setCargoStatus(Long globalId,CargoStatus cargoStatus);
	
	public List<UserVO> getUserList();
}
