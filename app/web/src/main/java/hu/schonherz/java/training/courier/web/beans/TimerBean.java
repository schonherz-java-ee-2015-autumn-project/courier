package hu.schonherz.java.training.courier.web.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import hu.schonherz.java.training.courier.service.LogServiceLocal;
import hu.schonherz.java.training.courier.service.vo.LogVO;

@SessionScoped
@ManagedBean(name = "timerBean")
public class TimerBean {

	@EJB
	LogServiceLocal logService;
	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;
	List<LogVO> logs;
	private Long hours;
	private static final Long workingTime = 28800000L;
	private Long workingPercentage;
	Long duration;

	public Long getWorkingPercentage() {
		return workingPercentage;
	}

	public void setWorkingPercentage(Long workingPercentage) {
		this.workingPercentage = workingPercentage;
	}

	public Long getHours() {
		return hours;
	}

	public void setHours(Long hours) {
		this.hours = hours;
	}

	public Long getMinutes() {
		return minutes;
	}

	public void setMinutes(Long minutes) {
		this.minutes = minutes;
	}

	private Long minutes;

	@PostConstruct
	public void init() {

		try {
			logs = getLogService().getLogsByUserIdFrom(getUserSessionBean().getUserVO().getId(), getMidnight());
			if (logs.isEmpty())
				duration = 0L;
			else
				getLoginDurationFromLog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Date getMidnight() {

		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);

		return now.getTime();
	}

	public void getLoginDurationFromLog() {
		duration = 0L;
		for (LogVO logVO : logs) {
			if (logVO.getLogoutDate() != null)
				duration += logVO.getLogoutDate().getTime() - logVO.getLoginDate().getTime();
//			else
//				duration += new Date().getTime() - logVO.getLoginDate().getTime();
		}

		minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		hours = TimeUnit.MILLISECONDS.toHours(duration);
		workingPercentage = getWorkingTimeInPercentage();
		// long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);

	}

	public void onTimeout() {
		if (minutes == 59) {
			hours++;
			minutes = 1L;

		} else
			minutes++;

		duration += 60000;

		workingPercentage = getWorkingTimeInPercentage();
	}

	public Long getWorkingTimeInPercentage() {

		return (duration * 100) / workingTime;

	}

	public LogServiceLocal getLogService() {
		return logService;
	}

	public void setLogService(LogServiceLocal logService) {
		this.logService = logService;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public List<LogVO> getLogs() {
		return logs;
	}

	public void setLogs(List<LogVO> logs) {
		this.logs = logs;
	}

}
