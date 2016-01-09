package hu.schonherz.java.training.courier.webservice.converters;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.Logger;

import hu.schonherz.administrator.RemoteCargoDTO;
import hu.schonherz.java.training.courier.service.RestaurantServiceLocal;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@Startup
@Singleton(name = "remoteCargoConverter")
public class RemoteCargoConverter {
	final static Logger logger = Logger.getLogger(RemoteCargoConverter.class);

	// RemoteCargoDTO
	// protected Long courierId;
	// protected Long id;
	// @XmlElement(nillable = true)
	// protected List<RemoteOrderDTO> orders;
	// protected Long restaurantId;
	// protected RemoteCargoState state;
	public RemoteCargoConverter() {
		// TODO Auto-generated constructor stub
	}

	@EJB
	UserServiceLocal userService;

	@EJB
	RestaurantServiceLocal restaurantService;

	@PostConstruct
	public void init() {
		logger.info("INFO: RemoteCargoConverter Singleton EJB started");
		if (userService == null || restaurantService == null) {
			logger.info("INFO:userService or restaurantService is null.");
		}
	}

	public CargoVO toLocalVO(RemoteCargoDTO remoteCargoDTO) {
		logger.info("INFO: RemoteCargoConverter toLocalVO method asked.");
		UserVO courier = null;
		try {
			logger.info("INFO: userService.findByGlobalId(remoteCargoDTO.getCourierId()) running");
			courier = userService.findByGlobalId(remoteCargoDTO.getCourierId());
			logger.info("INFO: courierName:" + courier.getFullname());
			if (courier == null) {
				logger.info("INFO: courier is still null after userService");
			}
		} catch (Exception e) {
			logger.info("ERROR:", e);
		}

		RestaurantVO restaurant = null;
		try {
			logger.info("INFO: restaurantService.findRestaurantByGlobalid(remoteCargoDTO.getRestaurantId()) running");
			restaurant = restaurantService.findRestaurantByGlobalid(remoteCargoDTO.getRestaurantId());
			logger.info("INFO: restaurantInfo:" + restaurant.getName());
			if (restaurant == null) {
				logger.info("INFO: Restaurant is still null after restaurantService");
			}

		} catch (Exception e) {
			logger.info("ERROR:", e);
		}
		logger.info("INFO: Making localVO");
		CargoVO localVo = new CargoVO();
		localVo.setId((long) 0);
		logger.info("INFO: CargoVO localVO made");
		List<AddressVO> addresses = RemoteOrderConverter.toLocalVo(remoteCargoDTO.getOrders());
		if (addresses == null) {
			logger.info("Addresses are NULL!");
		}
		localVo.setAddresses(addresses);
		logger.info("INFO: address is set in localVO");
		localVo.setGlobalid(remoteCargoDTO.getId());
		logger.info("INFO: globalid is set in localVO");
		localVo.setStatus(RemoteCargoStateConverter.toLocalCargoState(remoteCargoDTO.getState()));
		logger.info("INFO: status is set in localVO");
		localVo.setRestaurant(restaurant);
		logger.info("INFO: restaurant is set in localVO");
		localVo.setUser(courier);
		logger.info("INFO: courier is set in localVO");
		double totalValue = 0;
		for (int i = 0; i < remoteCargoDTO.getOrders().size(); i++) {

			totalValue += remoteCargoDTO.getOrders().get(i).getFullCost();
		}
		localVo.setTotalValue(totalValue);
		localVo.setModdate(new Date());
		logger.info("INFO: Returning localVO!");
		return localVo;

	}
}
