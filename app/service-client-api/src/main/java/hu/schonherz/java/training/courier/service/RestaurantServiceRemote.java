package hu.schonherz.java.training.courier.service;

import hu.schonherz.java.training.courier.service.vo.RestaurantVO;

public interface RestaurantServiceRemote {
	
	public RestaurantVO findRestaurantByGlobalid(Long globalid) throws Exception;
	
}