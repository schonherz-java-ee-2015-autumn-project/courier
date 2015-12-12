package hu.schonherz.java.training.courier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.dao.CargoDao;
import hu.schonherz.java.training.courier.service.CargoService;
import hu.schonherz.java.training.courier.service.converter.CargoConverter;
import hu.schonherz.java.training.courier.service.vo.CargoVO;

@Service("cargoService")
@Transactional(propagation = Propagation.REQUIRED)
public class CargoServiceImpl implements CargoService {

	@Autowired
	CargoDao cargoDao;

	@Override
	public List<CargoVO> findAll() throws Exception {
		return CargoConverter.toVo(cargoDao.findAll());
	}

	@Override
	public CargoVO findCargoById(Long cargoId) throws Exception {
		return CargoConverter.toVo(cargoDao.findCargoById(cargoId));
	}
}