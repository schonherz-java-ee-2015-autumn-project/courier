package hu.schonherz.java.training.courier.webservice.converters;

import hu.schonherz.administrator.RemotePaymentMethod;
import hu.schonherz.java.training.courier.entities.Payment;

public class RemotePaymentConverter {

	public static Payment toLocalPayment(RemotePaymentMethod remotePayment) {
		Payment localPayment = null;
		if (remotePayment == null) {
			return Payment.Cash;
		}
		switch (remotePayment) {
		case CASH:
			localPayment = Payment.Cash;
			break;
		case CREDIT_CARD:
			localPayment = Payment.Card;
			break;
		case SZEP_CARD:
			localPayment = Payment.SZEP;
			break;
		case VOUCHER:
			localPayment = Payment.Voucher;
			break;
		default:
			localPayment = Payment.Cash;
		}
		return localPayment;
	}

	public static RemotePaymentMethod toRemotePayment(Payment localPayment) {
		RemotePaymentMethod remotePayment = null;
		switch (localPayment) {
		case Cash:
			remotePayment = remotePayment.CASH;
			break;
		case Card:
			remotePayment = remotePayment.CREDIT_CARD;
			break;
		case SZEP:
			remotePayment = remotePayment.SZEP_CARD;
			break;
		case Voucher:
			remotePayment = remotePayment.VOUCHER;
			break;
		}
		return remotePayment;
	}
}
