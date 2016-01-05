package hu.schonherz.java.training.courier.webservice.converters;

import javax.ejb.EJB;

import hu.schonherz.administrator.RemoteCargoDTO;
import hu.schonherz.java.training.courier.entities.Restaurant;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.CargoVO;

public class RemoteCargoConverter {

	// RemoteCargoDTO
	// protected Long courierId;
	// protected Long id;
	// @XmlElement(nillable = true)
	// protected List<RemoteOrderDTO> orders;
	// protected Long restaurantId;
	// protected RemoteCargoState state;

	@EJB
	UserServiceLocal userService;
	
	public CargoVO toLocalVO(RemoteCargoDTO remoteCargoDTO) {
		CargoVO localVo = new CargoVO();
		localVo.setAddresses(RemoteOrderConverter.toLocalVo(remoteCargoDTO.getOrders()));
		localVo.setGlobalid(remoteCargoDTO.getId());
		localVo.setStatus(RemoteCargoStateConverter.toLocalCargoState(remoteCargoDTO.getState()));
		try {
			localVo.setUser(userService.findByGlobalId(remoteCargoDTO.getCourierId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}
