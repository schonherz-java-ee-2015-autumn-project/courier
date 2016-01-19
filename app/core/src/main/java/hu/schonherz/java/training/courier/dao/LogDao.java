package hu.schonherz.java.training.courier.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Log;

@Repository
public interface LogDao extends JpaRepository<Log, Long> {
	List<Log> findByUserId(Long id);

	@Query(value = "select * from log where user_id = ?1 and loginDate > ?2", nativeQuery = true)
	List<Log> getLogsByUserIdFrom(Long userId, Date date);
	
	@Query(value = "select count(*) from "
			+ "(select date(loginDate) from log where user_id = ?1  group by date(loginDate)) ins", nativeQuery = true)
	Double getWorkingDaysByUser(Long userId);
	
	@Query(value = "select (sum(timestampdiff(second,loginDate,logoutDate)) / 3600) "
			+ "from log where user_id = ?1 "
			+ "and date(loginDate) between date(?2) and date(?3)", nativeQuery = true)
	Double getWorkingHoursByUserBetweenDates(Long UserId, Date startDate, Date endDate);
	
	@Query(value = "select (sum(timestampdiff(second,loginDate,logoutDate)) / 3600) "
			+ "from log where user_id = ?1 ", nativeQuery = true)
	Double getTotalWorkingHoursByUser(Long UserId);
	
	@Query(value = "select avg(ins.w_h) "
			+ "from (select (sum(timestampdiff(second,loginDate,logoutDate)) / 3600) as w_h "
			+ "from log where user_id = ?1 group by date(loginDate)) ins", nativeQuery = true)
	Double getAverageWorkingHoursByUser(Long UserId);
}
