package hu.schonherz.java.training.courier.webservice.converters;

import hu.schonherz.administrator.RemoteCargoState;
import hu.schonherz.java.training.courier.entities.CargoStatus;

public class RemoteCargoStateConverter {

	public static CargoStatus toLocalCargoState(RemoteCargoState remoteCargoState) {
		CargoStatus localCargoStatus = null;
		switch (remoteCargoState) {
		case FREE:
			localCargoStatus = CargoStatus.Free;
			break;
		case TAKEN:
			localCargoStatus = CargoStatus.Reserved;
			break;
		case DELIVERING:
			localCargoStatus = CargoStatus.Received;
			break;
		case DELIVERED:
			localCargoStatus = CargoStatus.Delivered;
			break;
		}
		return localCargoStatus;
	}
}
