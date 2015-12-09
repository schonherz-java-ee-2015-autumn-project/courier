package hu.schonherz.java.training.courier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.dao.RestaurantDao;
import hu.schonherz.java.training.courier.service.RestaurantService;
import hu.schonherz.java.training.courier.service.converter.RestaurantConverter;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;

@Service("restaurantService")
@Transactional(propagation = Propagation.REQUIRED)
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	RestaurantDao restaurantDao;

	@Override
	public List<RestaurantVO> findAll() throws Exception {

		return RestaurantConverter.toVo(restaurantDao.findAll());
	}
}