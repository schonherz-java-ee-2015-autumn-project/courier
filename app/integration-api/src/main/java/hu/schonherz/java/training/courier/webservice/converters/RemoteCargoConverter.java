package hu.schonherz.java.training.courier.webservice.converters;

import javax.ejb.EJB;

import org.apache.log4j.Logger;

import hu.schonherz.administrator.RemoteCargoDTO;
import hu.schonherz.java.training.courier.service.RestaurantServiceLocal;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

public class RemoteCargoConverter {
	final static Logger logger = Logger.getLogger(RemoteCargoConverter.class);
	// RemoteCargoDTO
	// protected Long courierId;
	// protected Long id;
	// @XmlElement(nillable = true)
	// protected List<RemoteOrderDTO> orders;
	// protected Long restaurantId;
	// protected RemoteCargoState state;

	@EJB
	UserServiceLocal userService;

	@EJB
	RestaurantServiceLocal restaurantService;

	public CargoVO toLocalVO(RemoteCargoDTO remoteCargoDTO) {
		UserVO courier = null;
		try {
			courier = userService.findByGlobalId(remoteCargoDTO.getCourierId());
		} catch (Exception e) {
			logger.info("ERROR:", e);
		}
		RestaurantVO restaurant = null;
		try {
			restaurant = restaurantService.findRestaurantByGlobalid(remoteCargoDTO.getRestaurantId());
		} catch (Exception e) {
			logger.info("ERROR:", e);
		}
		CargoVO localVo = new CargoVO();
		localVo.setAddresses(RemoteOrderConverter.toLocalVo(remoteCargoDTO.getOrders()));
		localVo.setGlobalid(remoteCargoDTO.getId());
		localVo.setStatus(RemoteCargoStateConverter.toLocalCargoState(remoteCargoDTO.getState()));
		localVo.setRestaurant(restaurant);
		localVo.setUser(courier);
		return localVo;

	}
}
