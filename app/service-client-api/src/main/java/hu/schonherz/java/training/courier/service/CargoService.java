package hu.schonherz.java.training.courier.service;

import java.util.List;

import hu.schonherz.java.training.courier.service.vo.CargoVO;

public interface CargoService {

	public List<CargoVO> findAll() throws Exception;

	public List<CargoVO> findCargoesById(Long cargoId) throws Exception;

	public CargoVO findCargoById(Long cargoId) throws Exception;
}