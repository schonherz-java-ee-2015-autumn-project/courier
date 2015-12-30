package hu.schonherz.java.training.courier.service;

import java.util.List;

import hu.schonherz.java.training.courier.service.vo.UserVO;

public interface UserServiceLocal {

	public UserVO findUserByName(String name) throws Exception;

	public List<UserVO> findAll() throws Exception;

	UserVO save(UserVO userVO) throws Exception;
	
	UserVO saveUserById(UserVO userVO) throws Exception;
	
	Integer updateUserByGlobalId(UserVO userVO) throws Exception;

	public UserVO findUserByNameWhereIsRemovedZero(String username);
}