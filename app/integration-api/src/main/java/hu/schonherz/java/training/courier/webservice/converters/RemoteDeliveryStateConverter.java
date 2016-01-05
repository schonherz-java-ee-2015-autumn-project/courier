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
}
