package hu.schonherz.java.training.courier.entities;

public enum Payment {
	Bankk�rtya(1L), K�szp�nz(2L), Utalv�ny(3L), SZ�P(4L);

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
