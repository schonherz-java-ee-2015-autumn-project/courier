package hu.schonherz.java.training.courier.webservice.converters.test;

import org.junit.Before;
import org.junit.Test;

import hu.schonherz.administrator.RemoteCargoState;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.webservice.converters.RemoteCargoStateConverter;
import junit.framework.Assert;

public class RemoteCargoStateConverterTest {

	RemoteCargoState cargoFree;
	RemoteCargoState cargoTaken;
	RemoteCargoState cargoDelivering;
	RemoteCargoState cargoDelivered;

	@Before
	public void setUp() throws Exception {
		cargoFree = RemoteCargoState.FREE;
		cargoTaken = RemoteCargoState.TAKEN;
		cargoDelivering = RemoteCargoState.DELIVERING;
		cargoDelivered = RemoteCargoState.DELIVERED;
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testRemoteCargoStateConvertertoLocaCargoState() {
		CargoStatus localFree;
		CargoStatus localTaken;
		CargoStatus localDelivering;
		CargoStatus localDelivered;

		localFree = RemoteCargoStateConverter.toLocalCargoState(cargoFree);
		localTaken = RemoteCargoStateConverter.toLocalCargoState(cargoTaken);
		localDelivering = RemoteCargoStateConverter.toLocalCargoState(cargoDelivering);
		localDelivered = RemoteCargoStateConverter.toLocalCargoState(cargoDelivered);

		Assert.assertEquals(CargoStatus.Free, localFree);
		Assert.assertEquals(CargoStatus.Reserved, localTaken);
		Assert.assertEquals(CargoStatus.Received, localDelivering);
		Assert.assertEquals(CargoStatus.Delivered, localDelivered);

	}

}
