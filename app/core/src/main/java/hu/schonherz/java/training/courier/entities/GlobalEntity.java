package hu.schonherz.java.training.courier.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class GlobalEntity extends BaseEntity{
	@Column(unique = true)
	private Long globalid;

	public Long getGlobalid() {
		return globalid;
	}

	public void setGlobalid(Long globalid) {
		this.globalid = globalid;
	}
}
