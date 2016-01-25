package hu.schonherz.java.training.courier.webservice.converters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import hu.schonherz.administrator.RemoteOrderDTO;
import hu.schonherz.java.training.courier.service.vo.AddressVO;

public class RemoteOrderConverter {
	final static Logger logger = Logger.getLogger(RemoteOrderConverter.class);

	@SuppressWarnings("deprecation")
	public static AddressVO toLocalVo(RemoteOrderDTO remoteOrderDTO) {

		Date deadline = new Date();
		Calendar calendar = new GregorianCalendar(remoteOrderDTO.getDeadline().getYear(),
				remoteOrderDTO.getDeadline().getMonth()-1, remoteOrderDTO.getDeadline().getDay(),
				remoteOrderDTO.getDeadline().getHour(), remoteOrderDTO.getDeadline().getMinute(), 0);
		deadline = calendar.getTime();
		AddressVO localVo = new AddressVO();
		localVo.setGlobalid(remoteOrderDTO.getId());
		localVo.setDeadline(deadline);

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
