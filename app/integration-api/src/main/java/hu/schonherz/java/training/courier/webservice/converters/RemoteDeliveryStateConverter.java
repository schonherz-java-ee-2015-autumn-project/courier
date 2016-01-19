package hu.schonherz.java.training.courier.webservice.converters;

import org.apache.log4j.Logger;

import hu.schonherz.administrator.DeliveryStateWeb;
import hu.schonherz.java.training.courier.entities.AddressStatus;

public class RemoteDeliveryStateConverter {
	final static Logger logger = Logger.getLogger(RemoteDeliveryStateConverter.class);

	public static AddressStatus toLocalState(DeliveryStateWeb remoteState) {
		AddressStatus localStatus = null;

		switch (remoteState) {
		case IN_PROGRESS:
			localStatus = AddressStatus.In_progress;
			break;
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
		case In_progress:
			remoteState = DeliveryStateWeb.IN_PROGRESS;
			break;
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
