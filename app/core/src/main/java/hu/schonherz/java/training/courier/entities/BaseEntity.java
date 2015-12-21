package hu.schonherz.java.training.courier.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date RegDate = new Date();
	@Column(columnDefinition = "DATETIME DEFAULT NOW()")
	private Date ModDate = new Date();
	@Column(columnDefinition="int default 1",nullable=false)
	private Long RegUser = (long) 1;
	@Column(columnDefinition="int default 1",nullable=false)
	private Long ModUser = (long) 1;

	public Date getRegDate() {
		return RegDate;
	}

	public void setRegDate(Date regDate) {
		RegDate = regDate;
	}

	public Date getModDate() {
		return ModDate;
	}

	public void setModDate(Date modDate) {
		ModDate = modDate;
	}

	public Long getRegUser() {
		return RegUser;
	}

	public void setRegUser(Long regUser) {
		RegUser = regUser;
	}

	public Long getModUser() {
		return ModUser;
	}

	public void setModUser(Long modUser) {
		ModUser = modUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
