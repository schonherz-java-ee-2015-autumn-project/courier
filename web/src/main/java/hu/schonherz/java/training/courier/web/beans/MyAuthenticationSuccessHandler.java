package hu.schonherz.java.training.courier.web.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import hu.schonherz.java.training.courier.service.LogService;
import hu.schonherz.java.training.courier.service.vo.LogVO;

@Component
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private LogService logService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		SpringUser currentUser = (SpringUser) authentication.getPrincipal();
		LogVO logInfo = currentUser.getLogVO();
		logInfo.setLoginDate(new Date());
		logInfo.setLogoutDate(null);
		LogVO returnedLogVO;
		try {
			returnedLogVO = logService.save(logInfo);
			logInfo.setId(returnedLogVO.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentUser.setLogVO(logInfo);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());

		Authentication newAuth = new UsernamePasswordAuthenticationToken(currentUser, auth.getCredentials(),
				authorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		setDefaultTargetUrl("/secured/index.xhtml");
		super.onAuthenticationSuccess(request, response, authentication);
	}
}