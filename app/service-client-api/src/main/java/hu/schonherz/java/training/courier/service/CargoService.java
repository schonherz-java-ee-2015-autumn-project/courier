package hu.schonherz.java.training.courier.service;

import java.util.List;

import hu.schonherz.java.training.courier.service.vo.CargoVO;

public interface CargoService {
	public List<CargoVO> findCargoesById(Long cargoId) throws Exception;

	public CargoVO findCargoById(Long cargoId) throws Exception;

	public List<CargoVO> findCargoesByUserIdAndStatus(Long userId, Long status) throws Exception;

	public CargoVO save(CargoVO cargo) throws Exception;

	public List<CargoVO> findAllByStatus(Long status) throws Exception;

}