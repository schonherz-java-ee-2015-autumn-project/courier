package hu.schonherz.java.training.courier.webservice.converters;

import hu.schonherz.administrator.RemotePaymentMethod;
import hu.schonherz.java.training.courier.entities.Payment;

public class RemotePaymentConverter {

	public static Payment toLocalPayment(RemotePaymentMethod remotePayment) {
		Payment localPayment = null;
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
		}
		return localPayment;
	}
}
