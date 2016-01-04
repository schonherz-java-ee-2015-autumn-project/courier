package hu.schonherz.java.training.courier.service.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.java.training.courier.dao.RestaurantDao;
import hu.schonherz.java.training.courier.service.RestaurantServiceLocal;
import hu.schonherz.java.training.courier.service.RestaurantServiceRemote;
import hu.schonherz.java.training.courier.service.converter.RestaurantConverter;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;

@Stateless(mappedName = "RestaurantService")
@Local(RestaurantServiceLocal.class)
@Remote(RestaurantServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class RestaurantServiceImpl implements RestaurantServiceLocal, RestaurantServiceRemote {
	
	@Autowired
	RestaurantDao restaurantDao;

	@Override
	public RestaurantVO findRestaurantByGlobalid(Long globalid) throws Exception {
		return RestaurantConverter.toVo(restaurantDao.findRestaurantByGlobalid(globalid));
	}
}