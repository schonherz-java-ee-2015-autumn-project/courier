package hu.schonherz.java.training.courier.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Address;

@Repository
public interface AddressDao extends JpaRepository<Address, Long> {

	Address findAddressById(Long cargoId);

	@SuppressWarnings("unchecked")
	Address save(Address address);

	@Modifying(clearAutomatically = true)
	@Query(value = "update address set address = :address, deadline = :deadline, moddate = :modDate, payment = :payment, status = :status where globalid = :globalId ", nativeQuery = true)
	void updateAddressByGlobalId(@Param("address") String address, @Param("deadline") Date deadline,
			@Param("modDate") Date moddate, @Param("payment") String payment, @Param("status") String status,
			@Param("globalId") Long globalId);

}
