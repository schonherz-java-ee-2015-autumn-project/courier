package hu.schonherz.java.training.courier.entities;

public enum CargoStatus {

	Free(1L), Reserved(2L), Received(3L), Delivered(4L);

	private final Long value;

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

	public Long getValue() {
		return value;
	}
}