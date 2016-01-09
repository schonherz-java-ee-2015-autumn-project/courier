package hu.schonherz.java.training.courier.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Cargo;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.entities.User;

@Repository
public interface CargoDao extends JpaRepository<Cargo, Long> {

	List<Cargo> findCargoesById(Long cargoId);

	Cargo findCargoById(Long cargoId);

	Cargo findCargoByGlobalid(Long globalid);

	List<Cargo> findCargoesByUserIdAndStatus(Long userId, CargoStatus status);

	@Query(value = "Select c From Cargo c WHERE c.user = :user AND c.status = :status AND c.deliveredAt BETWEEN :startDate AND :endDate ORDER BY c.deliveredAt ASC")
	List<Cargo> findCargoesByUserIdAndStatusBetweenDatesOrderedByDeliveryDate(@Param("user") User user,
			@Param("status") CargoStatus status, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@SuppressWarnings("unchecked")
	Cargo save(Cargo cargo);

	List<Cargo> findAllByStatus(CargoStatus status);

	@Modifying(clearAutomatically = true)
	@Query(value = "update cargo set status = ?2, totalDistance = ?3, totalDuration = ?4 where id = ?1", nativeQuery = true)
	void updateCargoStatusById(Long id, String status, Long distance, Long duration);

	@Modifying(clearAutomatically = true)
	@Query(value = "update cargo set status = ?2, deliveredAt = ?3 where id = ?1", nativeQuery = true)
	void updateCargoStatusAndDeliveredAtById(Long id, String status, Date deliveredAt);

	@Query(value = "SELECT SUM(i.price*d.quantity) FROM Cargo c join c.addresses a join a.details d join d.item i WHERE c.user = :user AND date(c.deliveredAt) = date(:actualDate) AND a.payment = :payment AND a.status = 'Delivered'")
	Double findDailyIncomeByPayment(@Param("user") User user, @Param("actualDate") String actualDate,
			@Param("payment") Payment payment);

	@Modifying(clearAutomatically = true)
	@Query(value = "update cargo set status = :cargoStatus, user_id = :userid, moddate = :modDate where globalid = :globalId ", nativeQuery = true)
	void updateCargoByGlobalId(@Param("cargoStatus") CargoStatus cargoStatus, @Param("userid") Long userId,
			@Param("modDate") Date modDate, @Param("globalId") Long globalid);

}
