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

	public static RemoteCargoState toRemoteCargoState(CargoStatus localCargoStatus) {
		RemoteCargoState remoteCargoState = null;
		switch (localCargoStatus) {
		case Free:
			remoteCargoState = RemoteCargoState.FREE;
			break;
		case Reserved:
			remoteCargoState = RemoteCargoState.TAKEN;
			break;
		case Received:
			remoteCargoState = RemoteCargoState.DELIVERING;
			break;
		case Delivered:
			remoteCargoState = RemoteCargoState.DELIVERED;
			break;
		}
		return remoteCargoState;
	}
}
