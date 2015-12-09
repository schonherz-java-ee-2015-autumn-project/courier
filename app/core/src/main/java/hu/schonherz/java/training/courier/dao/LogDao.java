package hu.schonherz.java.training.courier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Log;

@Repository
public interface LogDao extends JpaRepository<Log, Long> {
	Log findBySessionId(String id);
}
