package hu.schonherz.java.training.courier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.training.courier.entities.Supplier;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Long> {

	
}
