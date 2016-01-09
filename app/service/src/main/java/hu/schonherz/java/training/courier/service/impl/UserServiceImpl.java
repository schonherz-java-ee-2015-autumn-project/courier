package hu.schonherz.java.training.courier.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.java.training.courier.dao.UserDao;
import hu.schonherz.java.training.courier.entities.User;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.UserServiceRemote;
import hu.schonherz.java.training.courier.service.converter.UserConverter;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@Stateless(mappedName = "UserService")
@Local(UserServiceLocal.class)
@Remote(UserServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class UserServiceImpl implements UserServiceLocal, UserServiceRemote {

	@Autowired
	private
	UserDao userDao;

	@Override
	public UserVO save(UserVO user) throws Exception {
		return UserConverter.toVo(getUserDao().save(UserConverter.toEntity(user)));
	}

	@Override
	public UserVO findUserByName(String name) throws Exception {
		User user = getUserDao().findByUsername(name);
		return UserConverter.toVo(user);
	}

	@Override
	public List<UserVO> findAll() throws Exception {
		return UserConverter.toVo(getUserDao().findAll());
	}

	@Override
	public UserVO saveUserById(UserVO user) throws Exception {
		Long id = user.getId();
		UserVO userVO = UserConverter.toVo(getUserDao().save(UserConverter.toEntity(user)));
		getUserDao().updateUserId(userVO.getId(), id);
		userVO = UserConverter.toVo(getUserDao().findOne(id));
		// System.out.println("Saved user:"+userVO.toString());
		return userVO;
	}

	@Override
	public Integer updateUserByGlobalId(UserVO userVO) throws Exception {
		return getUserDao().updateUserByGlobalId(userVO.getUsername(), userVO.getFullname(), userVO.getPassword(),new Date(),userVO.getGlobalid());
	}

	@Override
	public UserVO findUserByNameWhereIsRemovedZero(String username) throws Exception {
		return UserConverter.toVo(getUserDao().findUserByNameWhereIsRemovedZero(username));
	}

	@Override
	public UserVO findByGlobalId(Long globalId) throws Exception {
		return UserConverter.toVo(getUserDao().findByGlobalid(globalId));
	}
	
	public UserVO findOne(Long id) throws Exception {
		return UserConverter.toVo(getUserDao().findOne(id));
	}

	@Override
	public Date findLastModDate() throws Exception {
		return getUserDao().findLastModDate();
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}