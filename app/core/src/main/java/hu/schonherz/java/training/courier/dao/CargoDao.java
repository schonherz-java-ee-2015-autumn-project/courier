package hu.schonherz.java.training.courier.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.entities.Cargo;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Item;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.entities.Report;
import hu.schonherz.java.training.courier.entities.Restaurant;
import hu.schonherz.java.training.courier.entities.User;

@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public interface CargoDao extends JpaRepository<Cargo, Long> {

	List<Cargo> findCargoesById(Long cargoId);

	List<Cargo> findCargoesByModdate(Date moddate);

	Cargo findCargoById(Long cargoId);

	Cargo findCargoByGlobalid(Long globalid);

	List<Cargo> findCargoesByUserIdAndStatus(Long userId, CargoStatus status);

	@Query(value = "Select c From Cargo c WHERE c.user = :user AND c.status = :status AND c.deliveredAt BETWEEN :startDate AND :endDate ORDER BY c.deliveredAt ASC")
	List<Cargo> findCargoesByUserIdAndStatusBetweenDatesOrderedByDeliveryDate(@Param("user") User user,
			@Param("status") CargoStatus status, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "select * from cargo where moddate between :startDate and :endDate", nativeQuery = true)
	List<Cargo> findCargoesBetweenModdate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

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
	@Query(value = "update cargo set status = :cargoStatus, moddate = :modDate where globalid = :globalId ", nativeQuery = true)
	void updateCargoByGlobalId(@Param("cargoStatus") String cargoStatus, @Param("modDate") Date modDate,
			@Param("globalId") Long globalid);

	/**1**/
	@Query(value = "SELECT SUM(i.price*d.quantity) "
			+ "FROM Cargo c join c.addresses a join a.details d join d.item i "
			+ "WHERE c.user = :user "
			+ "AND date(c.deliveredAt) between date(:startDate) AND date(:endDate) "
			+ "AND a.status = 'Delivered'")
	Double findTotalIncomeByUserBetweenDates(@Param("user") User user, 
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	/**2**/
	@Query(value = "SELECT SUM(i.price*d.quantity) "
			+ "FROM Cargo c join c.addresses a join a.details d join d.item i "
			+ "WHERE c.user = :user "
			+ "AND date(c.deliveredAt) between date(:startDate) AND date(:endDate) "
			+ "AND a.payment = :payment AND a.status = 'Delivered'")
	Double findIncomeByUserAndPaymentBetweenDates(@Param("user") User user, 
			@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("payment") Payment payment);
	/**3**/
	@Query(value = "SELECT NEW hu.schonherz.java.training.courier.entities.Report( count(c), sum(c.totalDistance), sum(c.totalDuration) ) "
			+ "FROM Cargo c "
			+ "WHERE c.user = :user "
			+ "AND date(c.deliveredAt) between date(:startDate) AND date(:endDate) "
			+ "AND c.status = 'Delivered'")
	Report findReportByUserBetweenDates(@Param("user") User user, 
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	/**4**/
	@Query(value = "SELECT SUM(i.price*d.quantity) "
			+ "FROM Cargo c join c.addresses a join a.details d join d.item i "
			+ "WHERE c.user = :user "
			+ "AND a.status = 'Delivered'")
	Double findTotalIncomeByUser(@Param("user") User user);
	/**5**/
	@Query(value = "SELECT SUM(i.price*d.quantity) "
			+ "FROM Cargo c join c.addresses a join a.details d join d.item i "
			+ "WHERE c.user = :user "
			+ "AND a.payment = :payment AND a.status = 'Delivered'")
	Double findIncomeByUserAndPayment(@Param("user") User user, 
			@Param("payment") Payment payment);
	/**6**/
	@Query(value = "SELECT NEW hu.schonherz.java.training.courier.entities.Report( count(c), sum(c.totalDistance), sum(c.totalDuration) ) "
			+ "FROM Cargo c "
			+ "WHERE c.user = :user "
			+ "AND c.status = 'Delivered'")
	Report findReportByUser(@Param("user") User user);
	/**7**/
	@Query(value = "select avg(ins.daily_pri) "
			+ "from ( select sum(i.price*ad.quantity) as daily_pri "
			+ "from cargo c "
			+ "inner join cargo_address c_a on c.id = c_a.Cargo_id "
			+ "inner join address a on a.id = c_a.addresses_id "
			+ "inner join address_addressdetails a_ad on a.id = a_ad.Address_id	"
			+ "inner join addressdetails ad on ad.id = a_ad.details_id "
			+ "inner join item i on ad.item_id = i.id "
			+ "where c.user_id = ?1 and a.status = 'Delivered' group by date(c.deliveredAt) ) ins", nativeQuery = true)
	Double findAverageIncomeByUserId(Long userId);
	/**8**/
	@Query(value = "select avg(ins.daily_pri) "
			+ "from ( select sum(i.price*ad.quantity) as daily_pri "
			+ "from cargo c "
			+ "inner join cargo_address c_a on c.id = c_a.Cargo_id "
			+ "inner join address a on a.id = c_a.addresses_id "
			+ "inner join address_addressdetails a_ad on a.id = a_ad.Address_id "
			+ "inner join addressdetails ad on ad.id = a_ad.details_id "
			+ "inner join item i on ad.item_id = i.id "
			+ "where c.user_id = ?1 and a.payment = ?2 "
			+ "and a.status = 'Delivered' group by date(c.deliveredAt) ) ins", nativeQuery = true)
	Double findAverageIncomeByUserIdAndPayment(Long userId, String payment);
	/**9**/
	@Query(value = "select avg(ins.daily_c), avg(ins.daily_dis), avg(ins.daily_dur) "
			+ "from ( "
			+ "select count(*) as daily_c, sum(c.totalDistance) as daily_dis, sum(c.totalDuration) as daily_dur	"
			+ "from cargo c	"
			+ "where c.user_id = ?1 "
			+ "and c.status = 'Delivered' group by date(c.deliveredAt) ) ins", nativeQuery = true)
	List<Object[]> findAverageReportByUserId(Long userId);
	/**10**/
	@Query(value = "SELECT r FROM Cargo c join c.restaurant r "
			+ "WHERE c.user = :user "
			+ "AND date(c.deliveredAt) between date(:startDate) AND date(:endDate) "
			+ "AND c.status = 'Delivered'"
			+ "GROUP BY c.restaurant")
	List<Restaurant> findRestaurantsByUserBetweenDates(@Param("user") User user, 
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	/**11**/
	@Query(value = "SELECT SUM(i.price*d.quantity) "
			+ "FROM Cargo c join c.addresses a join a.details d join d.item i "
			+ "WHERE c.user = :user "
			+ "AND c.restaurant = :restaurant "
			+ "AND date(c.deliveredAt) between date(:startDate) AND date(:endDate) "
			+ "AND a.status = 'Delivered' ")
	Double findTotalIncomeByUserAndRestaurantBetweenDates(@Param("user") User user, 
			@Param("restaurant") Restaurant restaurant,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	/**12**/
	@Query(value = "SELECT SUM(i.price*d.quantity) "
			+ "FROM Cargo c join c.addresses a join a.details d join d.item i "
			+ "WHERE c.user = :user "
			+ "AND a.payment = :payment "
			+ "AND c.restaurant = :restaurant "
			+ "AND date(c.deliveredAt) between date(:startDate) AND date(:endDate) "
			+ "AND a.status = 'Delivered' ")
	Double findIncomeByUserAndRestaurantAndPaymentBetweenDates(@Param("user") User user, 
			@Param("restaurant") Restaurant restaurant, @Param("payment") Payment payment, 
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value ="SELECT i "
			+ "FROM Cargo c join c.addresses a join a.details d join d.item i "
			+ "WHERE c.user = :user "
			+ "AND a.status = 'Delivered' "
			+ "GROUP BY i.id ORDER BY COUNT(i.id) DESC ")
	List<Item> findItemsByUserOrderByCount(@Param("user") User user);
}
