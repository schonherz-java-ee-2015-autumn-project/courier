package hu.schonherz.java.training.courier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@SuppressWarnings("unchecked")
	User save(User user);
	
	@Modifying(clearAutomatically = true)
	@Query(value="update user set id = ?2 where id = ?1",nativeQuery=true)
	void updateUserId(Long existingId, Long newId);

}
