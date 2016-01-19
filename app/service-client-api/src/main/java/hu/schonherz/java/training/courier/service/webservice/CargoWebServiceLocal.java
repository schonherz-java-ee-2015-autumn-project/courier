package hu.schonherz.java.training.courier.service.webservice;

import java.util.List;

import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.service.vo.CargoVO;

public interface CargoWebServiceLocal {
	void getFreeCargosFromAdministration() throws Exception;

	Long assignUserToCargo(Long userId, Long cargoId) throws Exception;

	Long changeDeliveryState(Long addressId, Long userId, AddressStatus status) throws Exception;

	Long changePaymentState(Long userId, Long addressId, Payment payment) throws Exception;

	Long changeCargoState(Long cargoId, Long userId, CargoStatus cargoStatus) throws Exception;

	List<CargoVO> getCargosListFromWebService();
}