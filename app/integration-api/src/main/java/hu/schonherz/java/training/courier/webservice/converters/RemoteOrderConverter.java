package hu.schonherz.java.training.courier.webservice.converters;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import hu.schonherz.administrator.RemoteItemQuantityDTO;
import hu.schonherz.administrator.RemoteOrderDTO;
import hu.schonherz.administrator.RemotePaymentMethod;
import hu.schonherz.java.training.courier.service.vo.AddressVO;

public class RemoteOrderConverter {
	final static Logger logger = Logger.getLogger(RemoteOrderConverter.class);
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
//		logger.info("INFO: toLocalVo int RemoteOrderConverter");
		AddressVO localVo = new AddressVO();
//		logger.info("INFO: localVo is made");
		localVo.setGlobalid(remoteOrderDTO.getId());
//		logger.info("INFO: globalId is set");
		// localVo.setDeadline(remoteOrderDTO.getDeadline().toGregorianCalendar().getTime());
		// logger.info("INFO: deadLine is set");
		localVo.setAddress(remoteOrderDTO.getAddressToDeliver());
//		logger.info("INFO: address is set");
		localVo.setDetails(RemoteItemQuantityConverter.toLocalVo(remoteOrderDTO.getItems()));
//		logger.info("INFO: details are set");
		localVo.setTotalValue((double) remoteOrderDTO.getFullCost());
//		logger.info("INFO: total value is set");
//		logger.info("INFO: trying to set payment" + remoteOrderDTO.getPayment().toString());
		localVo.setPayment(RemotePaymentConverter.toLocalPayment(remoteOrderDTO.getPayment()));
//		logger.info("INFO: payment is set");
		// logger.info("INFO: trying to set status" +
		// remoteOrderDTO.getDeliveryState().toString());
		localVo.setStatus(RemoteDeliveryStateConverter.toLocalState(remoteOrderDTO.getDeliveryState()));
//		logger.info("INFO: status is set");
//		logger.info("INFO: toLocalVo is done");
		return localVo;
	}

	public static List<AddressVO> toLocalVo(List<RemoteOrderDTO> remoteOrdersDTO) {
//		logger.info("INFO: toLocalVo in RemoteOrderConverter is called now trying to convert it to localVo");
		int count = 0;
		List<AddressVO> localAddressVo = new ArrayList<>();
		for (RemoteOrderDTO remoteOrder : remoteOrdersDTO) {
			localAddressVo.add(toLocalVo(remoteOrder));
//			logger.info("INFO:" + (++count) + " was converted to local");
		}
//		logger.info("INFO: remoteOrderDTO was succesfully converted!");
		return localAddressVo;
	}
}
