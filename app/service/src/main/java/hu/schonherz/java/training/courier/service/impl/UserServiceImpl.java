package hu.schonherz.java.training.courier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.dao.UserDao;
import hu.schonherz.java.training.courier.entities.User;
import hu.schonherz.java.training.courier.service.UserService;
import hu.schonherz.java.training.courier.service.converter.UserConverter;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@Service("userService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

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
		//System.out.println("User with ID:"+userVO.getId()+" should be updated to ID:"+id);
		userDao.updateUserId(userVO.getId(), id);
		userVO = UserConverter.toVo(userDao.findOne(id));
		//System.out.println("Saved user:"+userVO.toString());
		return userVO;
	}
}