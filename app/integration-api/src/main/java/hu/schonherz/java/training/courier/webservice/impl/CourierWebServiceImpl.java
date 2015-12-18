package hu.schonherz.java.training.courier.webservice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;
import hu.schonherz.java.training.courier.webservice.CourierWebService;
@WebService(endpointInterface="hu.schonherz.java.training.courier.webservice.CourierWebService")
public class CourierWebServiceImpl implements CourierWebService {

	@Override
	public List<UserVO> getUsers() {
		List<UserVO> users = new ArrayList<>();
		return users;
	}

	@Override
	public List<CargoVO> getFreeCargos() {
		List<CargoVO> cargos = new ArrayList<>();
		return cargos;
	}

}
