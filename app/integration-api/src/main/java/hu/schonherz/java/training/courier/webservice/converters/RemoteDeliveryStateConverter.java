package hu.schonherz.java.training.courier.webservice.converters;

import hu.schonherz.administrator.DeliveryStateWeb;
import hu.schonherz.java.training.courier.entities.AddressStatus;

public class RemoteDeliveryStateConverter {

	public static AddressStatus toLocalState(DeliveryStateWeb remoteState) {
		AddressStatus localStatus = null;
		switch (remoteState) {
		case DELIVERED:
			localStatus = AddressStatus.Delivered;
			break;
		case FAILED:
			localStatus = AddressStatus.Failed;
			break;
		}
		return localStatus;
	}

	public static DeliveryStateWeb toRemoteState(AddressStatus localStatus) {
		DeliveryStateWeb remoteState = null;
		switch (localStatus) {
		case Delivered:
			remoteState = DeliveryStateWeb.DELIVERED;
			break;
		case Failed:
			remoteState = DeliveryStateWeb.FAILED;
			break;
		}
		return remoteState;
	}
}
