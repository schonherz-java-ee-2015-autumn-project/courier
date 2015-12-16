package hu.schonherz.java.training.courier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@SuppressWarnings("unchecked")
	User save(User user);

}
