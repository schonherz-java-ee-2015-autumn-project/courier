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

	private Date regdate = new Date();
	@Column(columnDefinition = "DATETIME DEFAULT NOW()")
	private Date moddate = new Date();
	@Column(columnDefinition="int default 1",nullable=false)
	private Long regUser = (long) 1;
	@Column(columnDefinition="int default 1",nullable=false)
	private Long modUser = (long) 1;

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regDate) {
		this.regdate = regDate;
	}

	public Date getModdate() {
		return moddate;
	}

	public void setModdate(Date modDate) {
		this.moddate = modDate;
	}

	public Long getRegUser() {
		return regUser;
	}

	public void setRegUser(Long regUser) {
		this.regUser = regUser;
	}

	public Long getModUser() {
		return modUser;
	}

	public void setModUser(Long modUser) {
		this.modUser = modUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
