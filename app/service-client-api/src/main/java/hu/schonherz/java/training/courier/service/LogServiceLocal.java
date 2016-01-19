package hu.schonherz.java.training.courier.service;

import java.util.Date;
import java.util.List;

import hu.schonherz.java.training.courier.service.vo.LogVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

public interface LogServiceLocal {
	LogVO save(LogVO logVO) throws Exception;

	List<LogVO> findByUserId(Long id) throws Exception;

	List<LogVO> getLogsByUserIdFrom(Long userId, Date date) throws Exception;
	
	Double getWorkingDaysByUser(UserVO user);
	
	Double getWorkingHoursByUserBetweenDates(UserVO user, Date startDate, Date endDate);
	
	Double getTotalWorkingHoursByUser(UserVO user);
	
	Double getAverageWorkingHoursByUser(UserVO user);
}