package hu.schonherz.java.training.courier.webservice.converters.test;

import org.junit.Before;
import org.junit.Test;

import hu.schonherz.administrator.DeliveryStateWeb;
import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.webservice.converters.RemoteCargoStateConverter;
import hu.schonherz.java.training.courier.webservice.converters.RemoteDeliveryStateConverter;
import junit.framework.Assert;

public class RemoteDeliveryStateConverterTest {

	DeliveryStateWeb deliveryDelivered;
	DeliveryStateWeb deliveryFailed;

	@Before
	public void setUp() throws Exception {
		deliveryDelivered = DeliveryStateWeb.DELIVERED;
		deliveryFailed = DeliveryStateWeb.FAILED;
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testRemoteDeliveryStateConverterToLocalState() {
		AddressStatus localDelivered;
		AddressStatus localFailed;
		localDelivered = RemoteDeliveryStateConverter.toLocalState(deliveryDelivered);
		localFailed = RemoteDeliveryStateConverter.toLocalState(deliveryFailed);
		Assert.assertEquals(AddressStatus.Delivered, localDelivered);
		Assert.assertEquals(AddressStatus.Failed, localFailed);

	}

}
