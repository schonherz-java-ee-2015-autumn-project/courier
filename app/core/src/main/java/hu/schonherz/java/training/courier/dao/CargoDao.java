package hu.schonherz.java.training.courier.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Cargo;
import hu.schonherz.java.training.courier.entities.CargoStatus;

@Repository
public interface CargoDao extends JpaRepository<Cargo, Long> {

	List<Cargo> findCargoesById(Long cargoId);

	Cargo findCargoById(Long cargoId);

	List<Cargo> findCargoesByUserIdAndStatus(Long userId, CargoStatus status);

	@SuppressWarnings("unchecked")
	Cargo save(Cargo cargo);

	List<Cargo> findAllByStatus(CargoStatus status);

	@Modifying(clearAutomatically = true)
	@Query(value = "update cargo set status = ?2, totalDistance = ?3, totalDuration = ?4 where id = ?1", nativeQuery = true)
	void updateCargoStatusById(Long id, String status, Long distance, Long duration);
}
