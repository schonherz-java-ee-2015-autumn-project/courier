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

	@Query(value = "select * from log where loginDate > ?1", nativeQuery = true)
	List<Log> getLogsFrom(Date date);
}
