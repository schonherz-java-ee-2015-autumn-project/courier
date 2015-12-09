package hu.schonherz.java.training.courier.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Restaurant;

@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, Long> {
	
	List<Restaurant> findAll();
}
