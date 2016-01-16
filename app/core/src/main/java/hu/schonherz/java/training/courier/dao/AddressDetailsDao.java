package hu.schonherz.java.training.courier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Address;
import hu.schonherz.java.training.courier.entities.AddressDetails;

@Repository
public interface AddressDetailsDao extends JpaRepository<AddressDetails, Long> {

	@SuppressWarnings("unchecked")
	AddressDetails save(AddressDetails addressDetails);

}
