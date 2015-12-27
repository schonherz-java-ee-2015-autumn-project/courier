package hu.schonherz.java.training.courier.service;

import java.util.List;

import hu.schonherz.java.training.courier.service.vo.UserVO;

public interface WebServiceClientLocal {
	public void getUsersFromWebService();

	public void getCargos();
	
	public Long setCargoStatus();
	
	public List<UserVO> getUserList();
}
