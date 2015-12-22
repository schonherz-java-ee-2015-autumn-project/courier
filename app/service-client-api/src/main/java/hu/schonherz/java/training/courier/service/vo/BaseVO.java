package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.Date;

public class BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date regdate;
	private Date moddate;

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getModdate() {
		return moddate;
	}

	public void setModdate(Date moddate) {
		this.moddate = moddate;
	}

}
