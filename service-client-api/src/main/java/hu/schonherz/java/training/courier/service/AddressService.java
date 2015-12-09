package hu.schonherz.java.training.courier.service;

import java.util.List;

import hu.schonherz.java.training.courier.service.vo.AddressVO;

public interface AddressService {
	public List<AddressVO> findAll() throws Exception;
}