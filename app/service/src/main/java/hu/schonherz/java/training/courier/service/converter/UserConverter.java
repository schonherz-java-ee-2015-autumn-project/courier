package hu.schonherz.java.training.courier.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.java.training.courier.entities.User;
import hu.schonherz.java.training.courier.service.vo.UserVO;

public class UserConverter {

	static Mapper mapper = new DozerBeanMapper();

	public static UserVO toVo(User user) {
		if (user == null) {
			return null;
		}
		return mapper.map(user, UserVO.class);
	}

	public static User toEntity(UserVO userVO) {
		if (userVO == null) {
			return null;
		}
		return mapper.map(userVO, User.class);
	}

	public static List<UserVO> toVo(List<User> user) {
		List<UserVO> rv = new ArrayList<>();
		for (User users : user) {
			rv.add(toVo(users));
		}
		return rv;
	}

	public static List<User> toEntity(List<UserVO> user) {
		List<User> rv = new ArrayList<>();
		for (UserVO users : user) {
			rv.add(toEntity(users));
		}
		return rv;
	}

	public static UserVO toVo(hu.schonherz.java.training.courier.webservice.UserVO userVO) {
		if (userVO == null) {
			return null;
		}

		UserVO localUserVO = new UserVO();
		localUserVO.setId(userVO.getId());
		localUserVO.setFullname(userVO.getFullname());
		localUserVO.setUsername(userVO.getUsername());
		localUserVO.setPassword(userVO.getPassword());
		localUserVO.setGlobalid(userVO.getGlobalid());
		localUserVO.setModdate(userVO.getModdate().toGregorianCalendar().getTime());
		localUserVO.setRegdate(userVO.getRegdate().toGregorianCalendar().getTime());
		localUserVO.setRoles(RoleConverter.toVofromWS(userVO.getRoles()));
		localUserVO.setTransporting(userVO.getTransporting());
		return localUserVO;
	}

	public static List<UserVO> toVoFromWS(List<hu.schonherz.java.training.courier.webservice.UserVO> users) {
		List<UserVO> localUsers = new ArrayList<>();
		for (hu.schonherz.java.training.courier.webservice.UserVO userVO : users) {
			localUsers.add(toVo(userVO));
		}
		return localUsers;

	}

}
