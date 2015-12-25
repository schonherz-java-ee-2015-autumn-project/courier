package hu.schonherz.java.training.courier.service.impl;

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
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.CargoServiceRemote;
import hu.schonherz.java.training.courier.service.converter.CargoConverter;
import hu.schonherz.java.training.courier.service.vo.CargoVO;

@Stateless(mappedName="CargoService")
@Local(CargoServiceLocal.class)
@Remote(CargoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class CargoServiceImpl implements CargoServiceLocal,CargoServiceRemote {

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
	public CargoVO save(CargoVO cargo) throws Exception {
		return CargoConverter.toVo(cargoDao.save(CargoConverter.toEntity(cargo)));
	}

	@Override
	public List<CargoVO> findAllByStatus(CargoStatus status) throws Exception {
		return CargoConverter.toVo(cargoDao.findAllByStatus(status));
	}

	@Override
	public void updateCargoStatusById(Long id, String status) throws Exception {
		cargoDao.updateCargoStatusById(id, status);
		
	}
}