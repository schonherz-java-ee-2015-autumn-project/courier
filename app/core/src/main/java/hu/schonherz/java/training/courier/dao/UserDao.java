package hu.schonherz.java.training.courier.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@SuppressWarnings("unchecked")
	User save(User user);

	@Query(value = "SELECT * from user where username = :name and removed = 0", nativeQuery = true)
	User findUserByNameWhereIsRemovedZero(@Param("name") String name);

	@Query(value = "select moddate from user where id = 1 ", nativeQuery = true)
	Date findLastModDate();
}
