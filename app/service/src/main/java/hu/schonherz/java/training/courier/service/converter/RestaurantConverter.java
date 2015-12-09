package hu.schonherz.java.training.courier.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.java.training.courier.entities.Restaurant;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;

public class RestaurantConverter {

	static Mapper mapper = new DozerBeanMapper();

	public static RestaurantVO toVo(Restaurant restaurant) {
		if (restaurant == null) {
			return null;
		}
		return mapper.map(restaurant, RestaurantVO.class);
	}

	public static Restaurant toEntity(RestaurantVO restaurantVO) {
		if (restaurantVO == null) {
			return null;
		}
		return mapper.map(restaurantVO, Restaurant.class);
	}

	public static List<RestaurantVO> toVo(List<Restaurant> restaurant) {
		List<RestaurantVO> rv = new ArrayList<>();
		for (Restaurant restaurants : restaurant) {
			rv.add(toVo(restaurants));
		}
		return rv;
	}

	public static List<Restaurant> toEntity(List<RestaurantVO> restaurant) {
		List<Restaurant> rv = new ArrayList<>();
		for (RestaurantVO restaurants : restaurant) {
			rv.add(toEntity(restaurants));
		}
		return rv;
	}
}
