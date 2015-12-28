package hu.schonherz.java.training.courier.webservice.impl;

import java.util.List;

import javax.jws.WebService;

import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;
import hu.schonherz.java.training.courier.webservice.CourierWeb;

@WebService(endpointInterface="hu.schonherz.java.training.courier.webservice.CourierWeb")
public class WebServiceImplementation implements CourierWeb {

	@Override
	public List<UserVO> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CargoVO> getFreeCargos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long setCargoStatus(Long globalId, CargoStatus cargoStatus) {
		// TODO Auto-generated method stub
		return null;
	}

}
