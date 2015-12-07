package hu.schonherz.java.training.courier.service.vo;

import java.io.Serializable;
import java.util.Date;

import hu.schonherz.java.training.courier.entities.User;

public class LogVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private User user;
	private String sessionId;
	private Date loginDate;
	private Date logoutDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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

	@Override
	public String toString() {
		return "LogVO [id=" + id + ", user=" + user + ", sessionId=" + sessionId + ", loginDate=" + loginDate
				+ ", logoutDate=" + logoutDate + "]";
	}
}
