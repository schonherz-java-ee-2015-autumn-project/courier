package hu.schonherz.java.training.courier.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import hu.schonherz.java.training.courier.dao.UserDao;
import hu.schonherz.java.training.courier.entities.User;
import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.converter.UserConverter;
import hu.schonherz.java.training.courier.service.impl.UserServiceImpl;
import hu.schonherz.java.training.courier.service.vo.UserVO;
import junit.framework.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {

	private UserServiceImpl userService;

	private UserDao mockUserDao;


	@Before
	public void setUp() throws Exception {

		mockUserDao = EasyMock.createStrictMock(UserDao.class);

		userService = new UserServiceImpl();
		userService.setUserDao(mockUserDao);

	}



	

	@Test
	public void testFindOne() {

		// Elkészítünk egy új User-t
		User user = new User();
		user.setId((long) 64);
		user.setFullname("Mocked Test User");

		// Elkészítjük a mockot
		EasyMock.expect(mockUserDao.findOne(user.getId())).andReturn(user);
		EasyMock.replay(mockUserDao);

		// itt teszteljük a szolgáltatásunk metódusát
		UserVO result = null;
		try {
			result = userService.findOne((long) 64);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(result);
		EasyMock.verify(mockUserDao);

	}

	@Test
	public void testFindUserByName() {
		User user = new User();
		user.setId((long) 1);
		user.setFullname("Mocked User");
		user.setUsername("MockedUserName");
		user.setPassword("mockedPassword");

		EasyMock.expect(mockUserDao.findByUsername(user.getUsername())).andReturn(user);
		EasyMock.replay(mockUserDao);

		UserVO userVO = null;
		try {
			userVO = userService.findUserByName("MockedUserName");
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(userVO);
		EasyMock.verify(mockUserDao);
	}
	
//	@Test
	public void testUpdateUserByGlobalId() {
		User user = new User();
		user.setFullname("Mocked Global Id Test User");
		user.setUsername("Mocked GlobalIdTestUser");
		user.setPassword("mockdedGlobalIdTestUser");
		user.setGlobalid((long) 1024);
		user.setModdate(new Date());

		String updatedUsername = "MockedUpdatedGlobalIdTestUser";
		String updatedPassword = "mockedUpdatedGlobalIdTestUser";
		String updatedFullname = "Mocked Updated Global Id Test User";
		@SuppressWarnings("deprecation")
		Date updatedModDate = new Date(2016, 12, 31);

		mockUserDao.updateUserByGlobalId(updatedUsername, updatedFullname, updatedPassword, updatedModDate,
				(long) 1024);
		EasyMock.expectLastCall().andReturn(new Integer(1));

		EasyMock.replay(mockUserDao);

		UserVO userVO = new UserVO();
		userVO.setFullname(updatedFullname);
		userVO.setPassword(updatedPassword);
		userVO.setUsername(updatedUsername);
		userVO.setGlobalid((long) 1024);

		try {
			Integer status = userService.updateUserByGlobalId(userVO);
			System.out.println(status);
			assertEquals(new Integer(0), status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// assertEquals(updatedUsername, userVO.getUsername());
		// assertEquals(updatedPassword, userVO.getPassword());
		// assertEquals(updatedFullname, userVO.getFullname());
		// assertEquals(updatedModDate, userVO.getModdate());

		EasyMock.verify(mockUserDao);

	}

	@SuppressWarnings("unused")
//	@Test
	public void testFindUserByNameWhereIsRemovedZero() {
		// not working :(
		User user = new User();
		user.setFullname("Mocked Non-Removed Id Test User");
		user.setUsername("Mocked Non-RemovedIdTestUser");
		user.setPassword("mockdedNon-RemovedIdTestUser");
		user.setGlobalid((long) 1024);
		user.setModdate(new Date());
		user.setRemoved(false);
		UserVO userVO = UserConverter.toVo(user);


	}

}
