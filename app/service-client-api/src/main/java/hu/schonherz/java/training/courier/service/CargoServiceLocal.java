package hu.schonherz.java.training.courier.service;

import java.util.List;


import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.vo.CargoVO;

public interface CargoServiceLocal {
	public List<CargoVO> findCargoesById(Long cargoId) throws Exception;

	public CargoVO findCargoById(Long cargoId) throws Exception;

	public List<CargoVO> findCargoesByUserIdAndStatus(Long userId, CargoStatus cargoStatus) throws Exception;

	public CargoVO save(CargoVO cargo) throws Exception;

	public List<CargoVO> findAllByStatus(CargoStatus cargoStatus) throws Exception;

	void updateCargoStatusById(Long id, String status) throws Exception;
}