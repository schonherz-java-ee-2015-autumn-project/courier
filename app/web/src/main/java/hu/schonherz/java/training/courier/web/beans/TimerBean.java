package hu.schonherz.java.training.courier.web.beans;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import hu.schonherz.java.training.courier.service.LogServiceLocal;
import hu.schonherz.java.training.courier.service.vo.LogVO;

@SessionScoped
@ManagedBean(name = "timerBean")
public class TimerBean {

	@EJB
	LogServiceLocal logService;
	List<LogVO> logs;
	private Long hours;

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
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		try {
			logs = getLogService().getLogsFrom(now.getTime());
			getLoginDurationFromLog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getLoginDurationFromLog() {
		long duration = 0L;
		for (LogVO logVO : logs) {
			if (logVO.getLogoutDate() != null)
				duration += logVO.getLogoutDate().getTime() - logVO.getLoginDate().getTime();
		}

		minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		hours = TimeUnit.MILLISECONDS.toHours(duration);

		// long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);

	}

	public void onTimeout() {
		if (minutes == 59) {
			hours++;
			minutes = 1L;

		} else
			minutes++;
	}

	public LogServiceLocal getLogService() {
		return logService;
	}

	public void setLogService(LogServiceLocal logService) {
		this.logService = logService;
	}

	public List<LogVO> getLogs() {
		return logs;
	}

	public void setLogs(List<LogVO> logs) {
		this.logs = logs;
	}

}
