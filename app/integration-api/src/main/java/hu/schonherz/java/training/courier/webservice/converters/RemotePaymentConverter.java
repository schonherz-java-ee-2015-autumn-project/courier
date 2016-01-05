package hu.schonherz.java.training.courier.webservice.converters;

import hu.schonherz.administrator.DeliveryStateWeb;
import hu.schonherz.administrator.RemotePaymentMethod;
import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.Payment;

public class RemotePaymentConverter {

	public static Payment toLocalPayment(RemotePaymentMethod remotePayment) {
		Payment localPayment = null;
		switch (remotePayment) {
		case CASH:
			localPayment = Payment.Card;
			break;
		case CREDIT_CARD:
			localPayment = Payment.Card;
			break;
		case SZÉP_CARD:
			localPayment = Payment.SZEP;
			break;
		case VOUCHER:
			localPayment = Payment.Voucher;
			break;
		}
		return localPayment;
	}
}
