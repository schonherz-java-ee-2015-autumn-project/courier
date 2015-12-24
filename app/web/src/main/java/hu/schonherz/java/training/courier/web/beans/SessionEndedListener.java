package hu.schonherz.java.training.courier.web.beans;

import java.util.Date;

import javax.ejb.EJB;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import hu.schonherz.java.training.courier.service.LogServiceLocal;
import hu.schonherz.java.training.courier.service.vo.LogVO;

@Component
@EJB(name = "hu.schonherz.service.LogServiceLocal", beanInterface = LogServiceLocal.class)
public class SessionEndedListener implements ApplicationListener<SessionDestroyedEvent> {
	
	@EJB
	LogServiceLocal logService;

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
