package hu.schonherz.java.training.courier.entities;

public enum Payment {
	Bankkártya(1L), Készpénz(2L), Utalvány(3L), SZÉP(4L);

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
