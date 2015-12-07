package hu.schonherz.java.training.courier.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Log extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToOne
	private User user;
	@Override
	public String toString() {
		return "Log [user=" + user + ", sessionId=" + sessionId + ", loginDate=" + loginDate + ", logoutDate="
				+ logoutDate + "]";
	}

	private String sessionId;
	private Date loginDate;
	private Date logoutDate;

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

}
