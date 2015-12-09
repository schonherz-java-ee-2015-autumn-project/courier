package hu.schonherz.java.training.courier.service;

import java.util.List;

import hu.schonherz.java.training.courier.service.vo.SupplierVO;

public interface SupplierService {
	public List<SupplierVO> findAll() throws Exception;
}