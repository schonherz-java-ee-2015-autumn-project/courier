package hu.schonherz.java.training.courier.entities;

public enum Payment {
	Card(1L), Cash(2L), Voucher(3L), SZEP(4L);

	public final Long value;

	Payment(final Long value) {
		this.value = value;
	}

	public static Payment getValue(Long value) {
		for (Payment e : Payment.values()) {
			if (e.value == value) {
				return e;
			}
		}
		return null;// not found
	}
}
