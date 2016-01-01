package hu.schonherz.java.training.courier.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@SuppressWarnings("unchecked")
	User save(User user);

	@Modifying(clearAutomatically = true)
	@Query(value = "update user set id = ?2 where id = ?1", nativeQuery = true)
	void updateUserId(Long existingId, Long newId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update user set username = :userN, fullname = :fullN, password = :passW, moddate = :modD where globalid = :globalId", nativeQuery = true)
	Integer updateUserByGlobalId(@Param("userN") String username, @Param("fullN") String fullname,
			@Param("passW") String password, @Param("modD") Date moddate, @Param("globalId") Long globalid);

	@Query(value = "SELECT * from user where username = :name and removed = 0", nativeQuery = true)
	User findUserByNameWhereIsRemovedZero(@Param("name") String name);

	@Query(value = "SELECT MAX(moddate) FROM user", nativeQuery = true)
	Date findLastModDate();
	
	User findByGlobalid(Long globalId);

}
