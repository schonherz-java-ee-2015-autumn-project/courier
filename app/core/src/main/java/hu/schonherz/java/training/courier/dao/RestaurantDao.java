package hu.schonherz.java.training.courier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Restaurant;

@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, Long> {

	Restaurant findRestaurantByGlobalid(Long globalid);

}
