package hu.schonherz.java.training.courier.webservice.converters.test;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import hu.schonherz.administrator.DeliveryStateWeb;
import hu.schonherz.administrator.RemoteItemDTO;
import hu.schonherz.administrator.RemoteItemQuantityDTO;
import hu.schonherz.administrator.RemoteOrderDTO;
import hu.schonherz.administrator.RemotePaymentMethod;
import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.webservice.converters.RemoteOrderConverter;
import junit.framework.Assert;

public class RemoteOrderConverterTest {

	RemoteOrderDTO remoteOrder;
	RemoteItemQuantityDTO itemQuantity;
	RemoteItemDTO item;

	@Before
	public void setUp() throws Exception {
		GregorianCalendar gregorianDate = new GregorianCalendar();
		gregorianDate.setTime(new Date());
		XMLGregorianCalendar deadlineGregorianXML = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(gregorianDate);

		remoteOrder = new RemoteOrderDTO();
		remoteOrder.setAddressToDeliver("Debrecen Doberó út 2");
		remoteOrder.setDeadline(deadlineGregorianXML);
		remoteOrder.setDeliveryState(DeliveryStateWeb.DELIVERED);
		remoteOrder.setFullCost(1500);
		remoteOrder.setId((long) 1);
		remoteOrder.setPayment(RemotePaymentMethod.CASH);

		for (int i = 0; i < 5; i++) {
			item = new RemoteItemDTO();
			item.setId((long) i);
			item.setName("Csirke" + i);
			item.setPrice(100 * (i + 1));

			itemQuantity = new RemoteItemQuantityDTO();
			itemQuantity.setId((long) i);
			itemQuantity.setItemDTO(item);
			itemQuantity.setQuantity(i + 1);
			remoteOrder.getItems().add(itemQuantity);
		}

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testRemoteOrderConverterToLocalVo() {
		AddressVO localVo = new AddressVO();
		localVo = RemoteOrderConverter.toLocalVo(remoteOrder);
		Assert.assertEquals(remoteOrder.getAddressToDeliver(), localVo.getAddress());
		Assert.assertEquals(remoteOrder.getDeadline().toGregorianCalendar().getTime(), localVo.getDeadline());
		Assert.assertEquals(remoteOrder.getFullCost(), localVo.getTotalValue().intValue());
		Assert.assertEquals(AddressStatus.Delivered, localVo.getStatus());
		Assert.assertEquals(Payment.Cash, localVo.getPayment());
		for (int i = 0; i < 5; i++) {
			Assert.assertEquals(remoteOrder.getItems().get(i).getId(), localVo.getDetails().get(i).getGlobalid());
			Assert.assertEquals(remoteOrder.getItems().get(i).getQuantity().intValue(),
					localVo.getDetails().get(i).getQuantity().intValue());
			Assert.assertEquals(remoteOrder.getItems().get(i).getItemDTO().getId(),
					localVo.getDetails().get(i).getItem().getGlobalid());
			Assert.assertEquals(remoteOrder.getItems().get(i).getItemDTO().getPrice(),
					localVo.getDetails().get(i).getItem().getPrice());
			Assert.assertEquals(remoteOrder.getItems().get(i).getItemDTO().getName(),
					localVo.getDetails().get(i).getItem().getName());
		}
	}

}
