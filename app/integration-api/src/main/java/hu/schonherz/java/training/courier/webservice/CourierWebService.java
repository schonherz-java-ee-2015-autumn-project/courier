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
	 * A felhasz�ln�l�kat adja vissza az adatb�zisb�l amit ut�na egy UserVO list�ban dob �t a Courier Modul-ra.
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
	 * A szabad sz�ll�t�sokat adja vissza egy CargoVO t�pus� list�ban.
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
	 * Be�ll�tja a sz�ll�t�s st�tusz�t: CargoStatus(enum): Szabad(1L), Foglalt(2L), �tv�ve(3L), Kisz�ll�tva(4L),
	 * Illetve visszadob egy "hibak�dot": Ha sikeres volt a m�velet (az adatb�zisba val� friss�t�s/ment�s) akkor 0, ha nem akkor 1.
	 * @param cargoStatus
	 * @return
	 */
	
	@WebMethod(operationName="setCargoStatus")
	@WebResult(name="errorCode")
	public Long setCargoStatus(@WebParam(name="cargoStatus") CargoStatus cargoStatus);

}
