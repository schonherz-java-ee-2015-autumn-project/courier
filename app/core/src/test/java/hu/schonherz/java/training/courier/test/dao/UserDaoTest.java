package hu.schonherz.java.training.courier.test.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.dao.UserDao;
import hu.schonherz.java.training.courier.entities.User;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test-core.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserDaoTest {

	@Autowired
	UserDao userDao;

	@SuppressWarnings("deprecation")
	@Test
	@Transactional
	public void testFindLastModDate() {

		User user = new User();
		Date modDate = new java.util.Date();
		user.setFullname("Mod Date Test User");
		user.setUsername("ModDateTestUser");
		user.setPassword("modDateTestUser");
		user.setModdate(modDate);
		userDao.save(user);
		
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String savedTime = simpleFormat.format(modDate);
		Date testUserModDate = userDao.findLastModDate();
		String testUserModDateString = simpleFormat.format(testUserModDate);
		Assert.assertEquals(savedTime, testUserModDateString);
	}
	
	@Test
	@Transactional
	public void testFindUserByNameWhereIsRemovedZero() {
		User user = new User();
		user.setFullname("Is Removed Test User");
		user.setUsername("IsRemovedTestUser");
		user.setPassword("isRemovedTestUser");
		user.setRemoved(false);
		userDao.save(user);
		
		User testUser = userDao.findUserByNameWhereIsRemovedZero(user.getUsername());
		
		Assert.assertEquals(false, testUser.isRemoved());
	}
	
	@Test
	@Transactional
	public void testFindByGlobalId() {
		User user = new User();
		user.setFullname("Global Id Test User");
		user.setUsername("GlobalIdTestUser");
		user.setPassword("globalIdTestUser");
		user.setGlobalid((long) 1024);
		userDao.save(user);
		
		User testUser = userDao.findByGlobalid(user.getGlobalid());
		
		Assert.assertEquals(user.getGlobalid(), testUser.getGlobalid());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	@Transactional
	public void testUpdateUserByGlobalId() {
		User user = new User();
		user.setFullname("Global Id Test User");
		user.setUsername("GlobalIdTestUser");
		user.setPassword("globalIdTestUser");
		user.setGlobalid((long) 1024);
		user.setModdate(new Date());
		userDao.save(user);
		
		String updatedUsername = "UpdatedGlobalIdTestUser";
		String updatedPassword = "updatedGlobalIdTestUser";
		String updatedFullname = "Updated Global Id Test User";
		Date updatedModDate = new Date(2016,12,31);
		
		userDao.updateUserByGlobalId(updatedUsername, updatedFullname, updatedPassword, updatedModDate, user.getGlobalid());
		
		user = userDao.findByGlobalid(user.getGlobalid());
		Assert.assertEquals(updatedUsername, user.getUsername());
		Assert.assertEquals(updatedPassword, user.getPassword());
		Assert.assertEquals(updatedFullname, user.getFullname());
		Assert.assertEquals(updatedModDate, user.getModdate());
		
	}

}
