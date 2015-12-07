package hu.schonherz.java.training.courier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
