package hu.schonherz.java.training.courier.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@WebService(name = "courierWebService")
public interface CourierWebService {

	@WebMethod(operationName = "getUsersList")
	@WebResult(name = "usersListResponse")
	public List<UserVO> getUsers();

	@WebMethod(operationName = "getFreeCargosList")
	@WebResult(name = "freeCargosListResponse")
	public List<CargoVO> getFreeCargos();

}
