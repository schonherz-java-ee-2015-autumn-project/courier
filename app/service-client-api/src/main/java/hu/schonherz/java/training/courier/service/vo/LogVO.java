package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.Date;

public class LogVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private UserVO user;
	private Date loginDate;
	private Date logoutDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "LogVO [id=" + id + ", user=" + user + ", loginDate=" + loginDate + ", logoutDate=" + logoutDate + "]";
	}

}
