package hu.schonherz.java.training.courier.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.java.training.courier.dao.AddressDao;
import hu.schonherz.java.training.courier.dao.AddressDetailsDao;
import hu.schonherz.java.training.courier.dao.CargoDao;
import hu.schonherz.java.training.courier.dao.ItemDao;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.CargoServiceRemote;
import hu.schonherz.java.training.courier.service.converter.CargoConverter;
import hu.schonherz.java.training.courier.service.converter.UserConverter;
import hu.schonherz.java.training.courier.service.vo.AddressDetailsVO;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@Stateless(mappedName = "CargoService")
@Local(CargoServiceLocal.class)
@Remote(CargoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class CargoServiceImpl implements CargoServiceLocal, CargoServiceRemote {

	private final static Logger logger = Logger.getLogger(CargoServiceImpl.class);

	@Autowired
	CargoDao cargoDao;

	@Autowired
	AddressDetailsDao addressDetailsDao;

	@Autowired
	ItemDao itemDao;

	@Autowired
	AddressDao addressDao;

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
	public Double findDailyIncomeByPayment(UserVO user, String actualDate, Payment payment) throws Exception {
		return cargoDao.findDailyIncomeByPayment(UserConverter.toEntity(user), actualDate, payment);
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

	@Override
	public void updateCargoByGlobalId(CargoVO cargo) {

		for (AddressVO address : cargo.getAddresses()) {

			for (AddressDetailsVO details : address.getDetails()) {
				itemDao.updateItemByGlobalId(new Date(), details.getItem().getName(), details.getItem().getPrice(),
						details.getGlobalid());

				addressDetailsDao.updateAddressDetailsByGlobalId(new Date(), details.getQuantity(),
						details.getGlobalid());

			}
			logger.info(address.getAddress());
			logger.info(address.getDeadline());
			logger.info(address.getPayment());
			logger.info(address.getStatus());
			logger.info(address.getGlobalid());
			addressDao.updateAddressByGlobalId(address.getAddress(), address.getDeadline(), new Date(),
					address.getPayment().toString(), address.getStatus().toString(), address.getGlobalid());

		}

		cargoDao.updateCargoByGlobalId(cargo.getStatus().toString(), new Date(), cargo.getGlobalid());
	}

	@Override
	public List<CargoVO> findCargosBetweenModdate(Date startDate, Date endDate) throws Exception {
		return CargoConverter.toVo(cargoDao.findCargoesBetweenModdate(startDate, endDate));
	}

	@Override
	public List<CargoVO> findAll() throws Exception {
		return CargoConverter.toVo(cargoDao.findAll());
	}

}