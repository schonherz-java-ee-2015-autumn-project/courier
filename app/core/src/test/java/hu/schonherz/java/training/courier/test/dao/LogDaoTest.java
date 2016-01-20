package hu.schonherz.java.training.courier.test.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.dao.LogDao;
import hu.schonherz.java.training.courier.entities.Log;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test-core.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class LogDaoTest {

	@Autowired
	LogDao logDao;

	@Test
	@Transactional
	// @Query(value = "select * from log where loginDate > ?1", nativeQuery =
	// true)
	public void testGetLogsFrom() {
		List<Log> logs = new ArrayList<>();
		Log log = new Log();
		Date testDate = new Date();
		Calendar testCalendar = Calendar.getInstance();
		log.setLoginDate(testDate);
		
		testCalendar.setTime(testDate);
		testCalendar.add(Calendar.HOUR,1);
		testDate = testCalendar.getTime();
		log.setLogoutDate(new Date());
		
		logs.add(log);
		logDao.save(logs);
		
		Date dateToQuery = new Date();
		testCalendar.setTime(dateToQuery);
		testCalendar.add(Calendar.HOUR,-6);
		dateToQuery = testCalendar.getTime();
		
		List<Log> testLogs = logDao.getLogsByUserIdFrom((long) 2, dateToQuery);
		Assert.assertFalse(testLogs.isEmpty());
		
		
	}

}
