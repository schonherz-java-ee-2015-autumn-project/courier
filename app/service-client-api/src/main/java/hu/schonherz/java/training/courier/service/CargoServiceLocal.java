package hu.schonherz.java.training.courier.service;

import java.util.Date;
import java.util.List;

import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

public interface CargoServiceLocal {
	public List<CargoVO> findAll() throws Exception;

	public List<CargoVO> findCargoesById(Long cargoId) throws Exception;

	public List<CargoVO> findCargosBetweenModdate(Date startDate, Date endDate) throws Exception;

	public CargoVO findCargoById(Long cargoId) throws Exception;

	public CargoVO findCargoByGlobalid(Long globalid) throws Exception;

	public List<CargoVO> findCargoesByUserIdAndStatus(Long userId, CargoStatus cargoStatus) throws Exception;

	public CargoVO save(CargoVO cargo) throws Exception;

	public List<CargoVO> findAllByStatus(CargoStatus cargoStatus) throws Exception;

	void updateCargoStatusById(Long id, String status, Long distance, Long duration) throws Exception;

	void updateCargoStatusAndDeliveredAtById(Long id, String status, Date deliveredAt) throws Exception;

	Double findDailyIncomeByPayment(UserVO user, String actualDate, Payment payment) throws Exception;

	List<CargoVO> findCargoesByUserIdAndStatusBetweenDatesOrderedByDeliveryDate(UserVO user, CargoStatus status,
			Date startDate, Date endDate) throws Exception;

	void updateCargoByGlobalId(CargoVO cargo);

}