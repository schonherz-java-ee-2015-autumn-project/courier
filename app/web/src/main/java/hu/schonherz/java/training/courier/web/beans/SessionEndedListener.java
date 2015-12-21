package hu.schonherz.java.training.courier.web.beans;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import hu.schonherz.java.training.courier.service.LogService;
import hu.schonherz.java.training.courier.service.vo.LogVO;

@Component
public class SessionEndedListener implements ApplicationListener<SessionDestroyedEvent> {
	@Autowired
	private LogService logService;

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		for (SecurityContext securityContext : event.getSecurityContexts()) {
			Authentication authentication = securityContext.getAuthentication();

			SpringUser currentUser = (SpringUser) authentication.getPrincipal();
			LogVO logInfo = currentUser.getLogVO();
			logInfo.setLogoutDate(new Date());
			try {
				logService.save(logInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
