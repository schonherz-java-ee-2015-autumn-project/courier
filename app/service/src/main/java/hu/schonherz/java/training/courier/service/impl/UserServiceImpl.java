package hu.schonherz.java.training.courier.service.impl;

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
	UserDao userDao;

	@Override
	public UserVO save(UserVO user) throws Exception {

		return UserConverter.toVo(userDao.save(UserConverter.toEntity(user)));

	}

	@Override
	public UserVO findUserByName(String name) throws Exception {
		User user = userDao.findByUsername(name);
		return UserConverter.toVo(user);
	}

	@Override
	public List<UserVO> findAll() throws Exception {

		return UserConverter.toVo(userDao.findAll());
	}
}