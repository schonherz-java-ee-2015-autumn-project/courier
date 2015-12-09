package hu.schonherz.java.training.courier.web.beans;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import hu.schonherz.java.training.courier.service.vo.LogVO;

public class SpringUser extends User {

	public SpringUser(LogVO logVO, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.logVO = logVO;
	}

	private static final long serialVersionUID = 1L;
	private LogVO logVO;

	public LogVO getLogVO() {
		return logVO;
	}

	public void setLogVO(LogVO logVO) {
		this.logVO = logVO;
	}

}
