package hu.schonherz.java.training.courier.entities;

public enum CargoStatus {
	Szabad(1L), Foglalt(2L), �tv�ve(3L), Folyamatban(4L), Kisz�ll�tva(5L);
	public final Long value;

	CargoStatus(final Long value) {
		this.value = value;
	}

	public static CargoStatus getValue(Long value) {
		for (CargoStatus e : CargoStatus.values()) {
			if (e.value == value) {
				return e;
			}
		}
		return null;// not found
	}
}
