package hu.schonherz.java.training.courier.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.java.training.courier.dao.CargoDao;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.entities.Report;
import hu.schonherz.java.training.courier.entities.Restaurant;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.CargoServiceRemote;
import hu.schonherz.java.training.courier.service.converter.CargoConverter;
import hu.schonherz.java.training.courier.service.converter.RestaurantConverter;
import hu.schonherz.java.training.courier.service.converter.UserConverter;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@Stateless(mappedName = "CargoService")
@Local(CargoServiceLocal.class)
@Remote(CargoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class CargoServiceImpl implements CargoServiceLocal, CargoServiceRemote {

	@Autowired
	CargoDao cargoDao;

	@Override
	public List<CargoVO> findCargoesByUserIdAndStatus(Long userId, CargoStatus status) throws Exception {
		return CargoConverter.toVo(cargoDao.findCargoesByUserIdAndStatus(userId, status));
	}

	@Override
	public List<CargoVO> findCargoesById(Long cargoId) throws Exception {
		return CargoConverter.toVo(cargoDao.findCargoesById(cargoId));
	}

	@Override
	public CargoVO findCargoById(Long cargoId) throws Exception {
		return CargoConverter.toVo(cargoDao.findCargoById(cargoId));
	}

	@Override
	public CargoVO findCargoByGlobalid(Long globalid) throws Exception {
		return CargoConverter.toVo(cargoDao.findCargoByGlobalid(globalid));
	}

	@Override
	public CargoVO save(CargoVO cargo) throws Exception {
		return CargoConverter.toVo(cargoDao.save(CargoConverter.toEntity(cargo)));
	}

	@Override
	public List<CargoVO> findAllByStatus(CargoStatus status) throws Exception {
		return CargoConverter.toVo(cargoDao.findAllByStatus(status));
	}

	@Override
	public void updateCargoStatusById(Long id, String status, Long distance, Long duration) throws Exception {
		cargoDao.updateCargoStatusById(id, status, distance, duration);

	}

	@Override
	public List<CargoVO> findCargoesByUserIdAndStatusBetweenDatesOrderedByDeliveryDate(UserVO user, CargoStatus status,
			Date startDate, Date endDate) throws Exception {
		return CargoConverter.toVo(cargoDao.findCargoesByUserIdAndStatusBetweenDatesOrderedByDeliveryDate(
				UserConverter.toEntity(user), status, startDate, endDate));
	}

	@Override
	public void updateCargoStatusAndDeliveredAtById(Long id, String status, Date deliveredAt) throws Exception {
		cargoDao.updateCargoStatusAndDeliveredAtById(id, status, deliveredAt);

	}
//1
	@Override
	public Double findIncomeByUserAndPaymentBetweenDates(UserVO user, Date startDate, Date endDate, 
			Payment payment) throws Exception{
		return cargoDao.findIncomeByUserAndPaymentBetweenDates(UserConverter.toEntity(user), startDate, endDate, payment);
	}
//2
	@Override
	public Double findTotalIncomeByUserBetweenDates(UserVO user, Date startDate, Date endDate) throws Exception {
		return cargoDao.findTotalIncomeByUserBetweenDates(UserConverter.toEntity(user), startDate, endDate);
	}
//3
	@Override
	public Report findReportByUserBetweenDates(UserVO user, Date startDate, Date endDate) throws Exception {
		return cargoDao.findReportByUserBetweenDates(UserConverter.toEntity(user), startDate, endDate);
	}
//4
	@Override
	public Double findTotalIncomeByUser(UserVO user) throws Exception {
		return cargoDao.findTotalIncomeByUser(UserConverter.toEntity(user));
	}
//5
	@Override
	public Double findIncomeByUserAndPayment(UserVO user, Payment payment) throws Exception {
		return cargoDao.findIncomeByUserAndPayment(UserConverter.toEntity(user), payment);
	}
//6
	@Override
	public Report findReportByUser(UserVO user) throws Exception {
		return cargoDao.findReportByUser(UserConverter.toEntity(user));
	}
//7
	@Override
	public Double findAverageIncomeByUserId(UserVO user) throws Exception {
		return cargoDao.findAverageIncomeByUserId(user.getId());
	}
//8
	@Override
	public Double findAverageIncomeByUserIdAndPayment(UserVO user, Payment payment) throws Exception {
		return cargoDao.findAverageIncomeByUserIdAndPayment(user.getId(), payment.toString());
	}
//9
	@Override
	public Report findAverageReportByUserId(UserVO user) throws Exception {
		List<Object[]> list = cargoDao.findAverageReportByUserId(user.getId());
		Object[] obj = list.get(0);
		return new Report(obj[0], obj[1], obj[2]);
	}
//10
	@Override
	public List<Restaurant> findRestaurantsByUserBetweenDates(UserVO user, Date startDate, Date endDate)
			throws Exception {
		return cargoDao.findRestaurantsByUserBetweenDates(UserConverter.toEntity(user), startDate, endDate);
	}
//11
	@Override
	public Double findTotalIncomeByUserAndRestaurantBetweenDates(UserVO user, RestaurantVO restaurant, 
			Date startDate, Date endDate) throws Exception {
		return cargoDao.findTotalIncomeByUserAndRestaurantBetweenDates(UserConverter.toEntity(user), 
				RestaurantConverter.toEntity(restaurant), startDate, endDate);
	}
//12
	@Override
	public Double findIncomeByUserAndRestaurantAndPaymentBetweenDates(UserVO user, RestaurantVO restaurant,
			Payment payment, Date startDate, Date endDate) throws Exception {
		return cargoDao.findIncomeByUserAndRestaurantAndPaymentBetweenDates(UserConverter.toEntity(user), 
				RestaurantConverter.toEntity(restaurant), payment, startDate, endDate);
	}
}