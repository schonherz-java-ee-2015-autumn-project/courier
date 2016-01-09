package hu.schonherz.java.training.courier.webservice.converters.test;

import org.junit.Before;
import org.junit.Test;

import hu.schonherz.administrator.RemotePaymentMethod;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.webservice.converters.RemotePaymentConverter;
import junit.framework.Assert;

public class RemotePaymentConverterTest {

	RemotePaymentMethod paymentCash;
	RemotePaymentMethod paymentCard;
	RemotePaymentMethod paymentSZEP;
	RemotePaymentMethod paymentVoucher;
	@Before
	public void setUp() throws Exception {
		paymentCash = RemotePaymentMethod.CASH;
		paymentCard = RemotePaymentMethod.CREDIT_CARD;
		paymentSZEP = RemotePaymentMethod.SZEP_CARD;
		paymentVoucher = RemotePaymentMethod.VOUCHER;
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testRemotePaymentConvertertoLocalPayment() {
		Payment localCash;
		Payment localCard;
		Payment localSZEP;
		Payment localVoucher;
		
		localCash = RemotePaymentConverter.toLocalPayment(paymentCash);
		localCard = RemotePaymentConverter.toLocalPayment(paymentCard);
		localSZEP = RemotePaymentConverter.toLocalPayment(paymentSZEP);
		localVoucher = RemotePaymentConverter.toLocalPayment(paymentVoucher);
		
		Assert.assertEquals(Payment.Cash,localCash);
		Assert.assertEquals(Payment.Card, localCard);
		Assert.assertEquals(Payment.SZEP, localSZEP);
		Assert.assertEquals(Payment.Voucher, localVoucher);

	}

}
