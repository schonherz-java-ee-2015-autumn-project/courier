package hu.schonherz.java.training.courier.webservice.converters;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.administrator.RemoteItemQuantityDTO;
import hu.schonherz.administrator.RemoteOrderDTO;
import hu.schonherz.administrator.RemotePaymentMethod;
import hu.schonherz.java.training.courier.service.vo.AddressVO;

public class RemoteOrderConverter {

	// RemoteOrderDTO
	// protected String addressToDeliver;
	// @XmlSchemaType(name = "dateTime")
	// protected XMLGregorianCalendar deadline;
	// protected DeliveryStateWeb deliveryState;
	// protected int fullCost;
	// protected Long id;
	// @XmlElement(nillable = true)
	// protected List<RemoteItemQuantityDTO> items;
	// protected RemotePaymentMethod payment;

	public static AddressVO toLocalVo(RemoteOrderDTO remoteOrderDTO) {
		AddressVO localVo = new AddressVO();
		localVo.setGlobalid(remoteOrderDTO.getId());
		localVo.setDeadline(remoteOrderDTO.getDeadline().toGregorianCalendar().getTime());
		localVo.setAddress(remoteOrderDTO.getAddressToDeliver());
		localVo.setDetails(RemoteItemQuantityConverter.toLocalVo(remoteOrderDTO.getItems()));
		localVo.setTotalValue((double) remoteOrderDTO.getFullCost());
		localVo.setPayment(RemotePaymentConverter.toLocalPayment(remoteOrderDTO.getPayment()));
		localVo.setStatus(RemoteDeliveryStateConverter.toLocalState(remoteOrderDTO.getDeliveryState()));
		return localVo;
	}

	public static List<AddressVO> toLocalVo(List<RemoteOrderDTO> remoteOrdersDTO) {
		List<AddressVO> localAddressVo = new ArrayList<>();
		for (RemoteOrderDTO remoteOrder : remoteOrdersDTO) {
			localAddressVo.add(toLocalVo(remoteOrder));
		}
		return localAddressVo;
	}
}
