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

	@Override
	public UserVO saveUserById(UserVO user) throws Exception {
		Long id = user.getId();
		UserVO userVO = UserConverter.toVo(userDao.save(UserConverter.toEntity(user)));
		// System.out.println("User with ID:"+userVO.getId()+" should be updated
		// to ID:"+id);
		userDao.updateUserId(userVO.getId(), id);
		userVO = UserConverter.toVo(userDao.findOne(id));
		// System.out.println("Saved user:"+userVO.toString());
		return userVO;
	}

	@Override
	public Integer updateUserByGlobalId(UserVO userVO) throws Exception {
		return userDao.updateUserByGlobalId(userVO.getUsername(), userVO.getFullname(), userVO.getPassword(),new Date(),userVO.getGlobalid());
	}

	@Override
	public UserVO findUserByNameWhereIsRemovedZero(String username) {
		return UserConverter.toVo(userDao.findUserByNameWhereIsRemovedZero(username));
	}
}