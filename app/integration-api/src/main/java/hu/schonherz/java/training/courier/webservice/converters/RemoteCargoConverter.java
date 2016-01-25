package hu.schonherz.java.training.courier.webservice.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.administrator.RemoteCargoDTO;
import hu.schonherz.java.training.courier.service.RestaurantServiceLocal;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@Startup
@Singleton(name = "remoteCargoConverter")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RemoteCargoConverter {

	final static Logger logger = Logger.getLogger(RemoteCargoConverter.class);

	public RemoteCargoConverter() {

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

		UserVO courier = null;
		Long courierId = remoteCargoDTO.getCourierId() == null ? 1 : remoteCargoDTO.getCourierId();
		try {
			courier = userService.findByGlobalId(courierId);
			if (courier == null) {
				logger.info("INFO: courier is still null after userService");
				courier = userService.findOne((long) 1);
			}
		} catch (Exception e) {
			logger.info("ERROR:", e);
		}
		RestaurantVO restaurant = null;
		try {
			restaurant = restaurantService.findRestaurantByGlobalid((long) 1);
		} catch (Exception e) {
			logger.info("ERROR:", e);
		}

		CargoVO localVo = new CargoVO();
		localVo.setId((long) 0);
		List<AddressVO> addresses = RemoteOrderConverter.toLocalVo(remoteCargoDTO.getOrders());
		if (addresses == null) {
			logger.info("Addresses are NULL!");
		}
		localVo.setAddresses(addresses);
		localVo.setGlobalid(remoteCargoDTO.getId());
		localVo.setStatus(RemoteCargoStateConverter.toLocalCargoState(remoteCargoDTO.getState()));
		localVo.setRestaurant(restaurant);
		localVo.setUser(courier);
		double totalValue = 0;
		for (int i = 0; i < remoteCargoDTO.getOrders().size(); i++) {

			totalValue += remoteCargoDTO.getOrders().get(i).getFullCost();
		}
		localVo.setTotalValue(totalValue);
		localVo.setModdate(new Date());

		return localVo;
	}

	public List<CargoVO> toLocalVO(List<RemoteCargoDTO> remoteCargos) {
		List<CargoVO> localCargos = new ArrayList<>();
		for (RemoteCargoDTO remoteVO : remoteCargos) {
			localCargos.add(toLocalVO(remoteVO));
		}
		return localCargos;

	}
}
