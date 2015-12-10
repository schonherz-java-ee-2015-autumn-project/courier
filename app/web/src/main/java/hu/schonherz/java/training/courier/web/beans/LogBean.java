package hu.schonherz.java.training.courier.web.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import hu.schonherz.java.training.courier.service.LogService;
import hu.schonherz.java.training.courier.service.vo.LogVO;

@SessionScoped
@ManagedBean(name = "logBean")
public class LogBean {

	@ManagedProperty(value = "#{logService}")
	private LogService logService;

	private List<LogVO> logs;

	@PostConstruct
	public void init() {
		try {
			logs = getLogService().findByUserId(1L);

			System.out.println("fasz " + logs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public List<LogVO> getLogs() {
		return logs;
	}

	public void setLogs(List<LogVO> logs) {
		this.logs = logs;
	}
}
