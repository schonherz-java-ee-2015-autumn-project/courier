package hu.schonherz.java.training.courier.service;

import java.util.List;

import hu.schonherz.java.training.courier.service.vo.RestaurantVO;

public interface RestaurantService {

	public List<RestaurantVO> findAll() throws Exception;
}