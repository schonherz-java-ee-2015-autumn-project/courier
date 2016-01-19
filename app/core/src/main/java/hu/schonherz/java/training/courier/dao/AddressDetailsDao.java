package hu.schonherz.java.training.courier.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.AddressDetails;

@Repository
public interface AddressDetailsDao extends JpaRepository<AddressDetails, Long> {

	@SuppressWarnings("unchecked")
	AddressDetails save(AddressDetails addressDetails);

	@Modifying(clearAutomatically = true)
	@Query(value = "update addressdetails set moddate = :modDate, quantity = :quantity where globalid = :globalId", nativeQuery = true)
	void updateAddressDetailsByGlobalId(@Param("modDate") Date modDate, @Param("quantity") Long quantity,
			@Param("globalId") Long globalId);

}
