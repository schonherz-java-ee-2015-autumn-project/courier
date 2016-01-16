package hu.schonherz.java.training.courier.webservice.converters;

import org.apache.log4j.Logger;

import hu.schonherz.administrator.DeliveryStateWeb;
import hu.schonherz.java.training.courier.entities.AddressStatus;

public class RemoteDeliveryStateConverter {
	final static Logger logger = Logger.getLogger(RemoteDeliveryStateConverter.class);

	public static AddressStatus toLocalState(DeliveryStateWeb remoteState) {
		AddressStatus localStatus = null;
		logger.info("INFO: In RemoteDeliveryStateConverter");
		switch (remoteState) {
		case DELIVERED:
			localStatus = AddressStatus.Delivered;
			break;
		case FAILED:
			localStatus = AddressStatus.Failed;
			break;
		}
		logger.info("INFO: In RemoteDeliveryStateConverter localstatus is set");
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
