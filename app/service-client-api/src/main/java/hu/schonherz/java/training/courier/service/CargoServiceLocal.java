package hu.schonherz.java.training.courier.service;

import java.util.Date;
import java.util.List;

import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.entities.Report;
import hu.schonherz.java.training.courier.entities.Restaurant;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;
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

	List<CargoVO> findCargoesByUserIdAndStatusBetweenDatesOrderedByDeliveryDate(UserVO user, CargoStatus status,
			Date startDate, Date endDate) throws Exception;

	void updateCargoByGlobalId(CargoVO cargo);

	// 1
	Double findTotalIncomeByUserBetweenDates(UserVO user, Date startDate, Date endDate) throws Exception;

	// 2
	Double findIncomeByUserAndPaymentBetweenDates(UserVO user, Date startDate, Date endDate, Payment payment)
			throws Exception;

	// 3
	Report findReportByUserBetweenDates(UserVO user, Date startDate, Date endDate) throws Exception;

	// 4
	Double findTotalIncomeByUser(UserVO user) throws Exception;

	// 5
	Double findIncomeByUserAndPayment(UserVO user, Payment payment) throws Exception;

	// 6
	Report findReportByUser(UserVO user) throws Exception;

	// 7
	Double findAverageIncomeByUserId(UserVO user) throws Exception;

	// 8
	Double findAverageIncomeByUserIdAndPayment(UserVO user, Payment payment) throws Exception;

	// 9
	Report findAverageReportByUserId(UserVO user) throws Exception;

	// 10
	List<RestaurantVO> findRestaurantsByUserBetweenDates(UserVO user, Date startDate, Date endDate) throws Exception;

	// 11
	Double findTotalIncomeByUserAndRestaurantBetweenDates(UserVO user, RestaurantVO restaurant, Date startDate,
			Date endDate) throws Exception;

	// 12
	Double findIncomeByUserAndRestaurantAndPaymentBetweenDates(UserVO user, RestaurantVO restaurant, Payment payment,
			Date startDate, Date endDate) throws Exception;

	List<ItemVO> findItemsByUserOrderByCount(UserVO user) throws Exception;
}