package hu.schonherz.java.training.courier.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Item;

@Repository
public interface ItemDao extends JpaRepository<Item, Long> {

	Item findItemByGlobalid(Long globalid);

	@SuppressWarnings("unchecked")
	Item save(Item item);

	@Modifying(clearAutomatically = true)
	@Query(value = "update item set moddate = :modDate, name = :name, price = :price where globalid = :globalId", nativeQuery = true)
	void updateItemByGlobalId(@Param("modDate") Date moddate, @Param("name") String name, @Param("price") double price,
			@Param("globalId") Long globalId);
}
