package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date RegDate;
	private Date ModDate;

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

}
