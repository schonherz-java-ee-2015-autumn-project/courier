package hu.schonherz.java.training.courier.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.dao.CargoDao;
import hu.schonherz.java.training.courier.dao.UserDao;
import hu.schonherz.java.training.courier.entities.Cargo;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.User;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test-core.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CargoDaoTest {

	@Autowired
	CargoDao cargoDao;

	@Autowired
	UserDao userDao;

	@SuppressWarnings("deprecation")
	@Test
	@Transactional
	public void testFindAllByStatus() {
		List<Cargo> cargos = new ArrayList<>();

		int countNumber = 16;
		for (int i = 0; i < countNumber; i++) {
			Cargo cargo = new Cargo();
			switch (i % 4) {
			case 0:
				cargo.setStatus(CargoStatus.Free);
				break;
			case 1:
				cargo.setStatus(CargoStatus.Reserved);
				break;
			case 2:
				cargo.setStatus(CargoStatus.Received);
				break;
			case 3:
				cargo.setStatus(CargoStatus.Delivered);
				break;
			}
			cargos.add(cargo);
		}

		cargoDao.save(cargos);
		List<Cargo> requestedFreeCargos = cargoDao.findAllByStatus(CargoStatus.Free);
		List<Cargo> requestedReservedCargos = cargoDao.findAllByStatus(CargoStatus.Reserved);
		List<Cargo> requestedReceivedCargos = cargoDao.findAllByStatus(CargoStatus.Received);
		List<Cargo> requestedDeliveredCargos = cargoDao.findAllByStatus(CargoStatus.Delivered);

		Assert.assertFalse(requestedFreeCargos.isEmpty());
		Assert.assertFalse(requestedReservedCargos.isEmpty());
		Assert.assertFalse(requestedReceivedCargos.isEmpty());
		Assert.assertFalse(requestedDeliveredCargos.isEmpty());
	}

	@SuppressWarnings({ "deprecation" })
	@Test
	@Transactional
	public void findCargoesByUserIdAndStatus() {
		List<Cargo> freeCargos = new ArrayList<>();
		List<Cargo> reservedCargos = new ArrayList<>();
		List<Cargo> deliveredCargos = new ArrayList<>();
		List<Cargo> receivedCargos = new ArrayList<>();

		User user = new User();
		user.setUsername("Cargo Test User");
		userDao.save(user);

		Cargo cargo = new Cargo();
		cargo.setUser(user);
		cargo.setStatus(CargoStatus.Free);
		freeCargos.add(cargo);
		cargoDao.save(freeCargos);
		List<Cargo> requestedFreeCargos = cargoDao.findCargoesByUserIdAndStatus(user.getId(), CargoStatus.Free);

		cargo = new Cargo();
		cargo.setUser(user);
		cargo.setStatus(CargoStatus.Reserved);
		reservedCargos.add(cargo);
		cargoDao.save(reservedCargos);
		List<Cargo> requestedReservedCargos = cargoDao.findCargoesByUserIdAndStatus(user.getId(), CargoStatus.Reserved);

		cargo = new Cargo();
		cargo.setUser(user);
		cargo.setStatus(CargoStatus.Received);
		receivedCargos.add(cargo);
		cargoDao.save(receivedCargos);
		List<Cargo> requestedReceivedCargos = cargoDao.findCargoesByUserIdAndStatus(user.getId(), CargoStatus.Received);

		cargo = new Cargo();
		cargo.setUser(user);
		cargo.setStatus(CargoStatus.Delivered);
		deliveredCargos.add(cargo);
		cargoDao.save(deliveredCargos);
		List<Cargo> requestedDeliveredCargos = cargoDao.findCargoesByUserIdAndStatus(user.getId(),
				CargoStatus.Delivered);

		Assert.assertFalse(requestedFreeCargos.isEmpty());
		Assert.assertFalse(requestedReservedCargos.isEmpty());
		Assert.assertFalse(requestedReceivedCargos.isEmpty());
		Assert.assertFalse(requestedDeliveredCargos.isEmpty());
	}

	@SuppressWarnings("deprecation")
	@Test
	@Transactional
	public void testUpdateCargoStatusById() {
		User user = new User();
		user.setUsername("Cargo Test User");
		userDao.save(user);

		Cargo cargo = new Cargo();
		cargo.setUser(user);
		cargo.setStatus(CargoStatus.Free);
		cargo = cargoDao.save(cargo);
		Long updateDistance = (long) 100;
		Long updateDuration = (long) 256;
		cargoDao.updateCargoStatusById(cargo.getId(), CargoStatus.Received.toString(), updateDistance, updateDuration);

		Cargo requestedCargo = cargoDao.findCargoById(cargo.getId());

		Assert.assertEquals(CargoStatus.Received, requestedCargo.getStatus());
		Assert.assertEquals(updateDistance, requestedCargo.getTotalDistance());
		Assert.assertEquals(updateDuration, requestedCargo.getTotalDuration());
	}

}
