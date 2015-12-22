package hu.schonherz.java.training.courier.webservice;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;
import hu.schonherz.java.training.courier.service.vo.RoleVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@WebService(name = "courierWebService")
public interface CourierWebService {
	
	/**
	 * A felhaszálnálókat adja vissza az adatbázisból amit utána egy UserVO listában dob át a Courier Modul-ra.
	 * UserVO:
	 *  Long id;
		String fullname;
		String username;
		String password;
		Long transporting;
		List<RoleVO> roles; 
		Long globalid;
		Date regdate;
	    Date moddate;
	 */
	@WebMethod(operationName = "getUsersList")
	@WebResult(name = "usersListResponse")
	public List<UserVO> getUsers();
	
	/**
	 * A szabad szállításokat adja vissza egy CargoVO típusú listában.
	 * CargoVO:
	 * Long id;
	   UserVO user;
	   RestaurantVO restaurant;
 	   List<AddressVO> addresses;
	   CargoStatus status;
	   double totalValue;
	   Long globalid;
	 */
	@WebMethod(operationName = "getFreeCargosList")
	@WebResult(name = "freeCargosListResponse")
	public List<CargoVO> getFreeCargos();
	
	/**
	 * Beállítja a szállítás státuszát: CargoStatus(enum): Szabad(1L), Foglalt(2L), Átvéve(3L), Kiszállítva(4L),
	 * Illetve visszadob egy "hibakódot": Ha sikeres volt a mûvelet (az adatbázisba való frissítés/mentés) akkor 0, ha nem akkor 1.
	 * @param cargoStatus
	 * @return
	 */
	
	@WebMethod(operationName="setCargoStatus")
	@WebResult(name="errorCode")
	public Long setCargoStatus(@WebParam(name="cargoStatus") CargoStatus cargoStatus);

}
