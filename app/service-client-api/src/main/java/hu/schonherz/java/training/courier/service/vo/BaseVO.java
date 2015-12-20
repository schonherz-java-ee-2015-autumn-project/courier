package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date regDate;
	private Date modDate;

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		regDate = regDate;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		modDate = modDate;
	}

}
