package hu.schonherz.java.training.courier.web.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.vo.LogVO;
import hu.schonherz.java.training.courier.service.vo.RoleVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@Service("customUserDetailsService")
@EJB(name = "hu.schonherz.service.UserServiceLocal", beanInterface = UserServiceLocal.class)
public class CustomUserDetailsService implements UserDetailsService {

	@EJB(name="UserService")
	UserServiceLocal userService;

	
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		UserVO user;

		try {
			user = userService.findUserByNameWhereIsRemovedZero(username);
			if (user == null) {
				throw new UsernameNotFoundException(username);
			}
			List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());

			return buildUserForAuthentication(user, authorities);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getMessage());
		}

	}

	private SpringUser buildUserForAuthentication(UserVO user, List<GrantedAuthority> authorities) {

		LogVO logVO = new LogVO();
		logVO.setUser(user);
		return new SpringUser(logVO, user.getUsername(), user.getPassword(), true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(List<RoleVO> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		for (RoleVO userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}