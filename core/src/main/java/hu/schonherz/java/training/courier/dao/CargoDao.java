package hu.schonherz.java.training.courier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Cargo;

@Repository
public interface CargoDao extends JpaRepository<Cargo, Long> {
	
}
