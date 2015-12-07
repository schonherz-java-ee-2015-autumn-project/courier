package hu.schonherz.java.training.courier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Address;

@Repository
public interface AddressDao extends JpaRepository<Address, Long> {

}
