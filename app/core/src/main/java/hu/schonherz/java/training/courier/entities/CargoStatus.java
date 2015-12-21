package hu.schonherz.java.training.courier.entities;

public enum CargoStatus {
	Szabad(1L), Foglalt(2L), Átvéve(3L), Kiszállítva(4L);
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
