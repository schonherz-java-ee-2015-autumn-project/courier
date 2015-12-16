package hu.schonherz.java.training.courier.entities;

public enum AddressStatus {
	Folyamatban(1L), Kisz�ll�tva(2L), Sikertelen(3L);
	public final Long value;

	AddressStatus(final Long value) {
		this.value = value;
	}

	public static AddressStatus getValue(Long value) {
		for (AddressStatus e : AddressStatus.values()) {
			if (e.value == value) {
				return e;
			}
		}
		return null;// not found
	}
}
