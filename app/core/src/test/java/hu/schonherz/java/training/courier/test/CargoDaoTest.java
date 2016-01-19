package hu.schonherz.java.training.courier.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import hu.schonherz.java.training.courier.dao.ItemDao;
import hu.schonherz.java.training.courier.dao.LogDao;
import hu.schonherz.java.training.courier.dao.RestaurantDao;
import hu.schonherz.java.training.courier.dao.UserDao;
import hu.schonherz.java.training.courier.entities.Address;
import hu.schonherz.java.training.courier.entities.AddressDetails;
import hu.schonherz.java.training.courier.entities.AddressStatus;
import hu.schonherz.java.training.courier.entities.Cargo;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.entities.Item;
import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.entities.Report;
import hu.schonherz.java.training.courier.entities.Restaurant;
import hu.schonherz.java.training.courier.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test-core.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CargoDaoTest {
	
	String testString = "[test] ";
	
	@Autowired
	CargoDao cargoDao;
	@Autowired
	RestaurantDao restaurantDao;
	@Autowired
	ItemDao itemDao;
	@Autowired
	UserDao userDao;
	@Autowired
	LogDao logDao;
	
	Random rand = new Random();
	
	List<Item> itemList = new ArrayList<>();
	List<Restaurant> restaurantList = new ArrayList<>();
	String[] addressList = {"Debrecen, Zsák utca 3","Debrecen, Veres utca 44","Debrecen, Kishatár utca 6","Debrecen, Csokonai utca 42","Debrecen, Jerikó utca 12","Debrecen, Egyetem sugárút 99","Debrecen, Kartács utca 22","Debrecen, Kartács utca 22","Debrecen, Éden utca 32","Debrecen, Derék utca 12","Debrecen, Zöld utca 26","Debrecen, Kurucz utca 112","Debrecen, Hadházi út 151","Debrecen, Mikszáth Kálmán utca 20","Debrecen, Csákány utca 2"};
	Date actualDate = new Date();
	int scale = 30;
	Long globalId = 89001L;
	CargoStatus cargoStatus = CargoStatus.Delivered;
	AddressStatus addressStatus = AddressStatus.Delivered;
	Long userId = 2L;
	User targetUser = null;
	
	//@Test
	public void testGenerateRandomFreeCargosForDay() {
		targetUser = userDao.findOne(userId);
		cargoStatus = CargoStatus.Free;
		addressStatus = null;
		generateRandomCargosForDay();
	}
	
	//@Test
	public void testGenerateRandomCargosForDay() {
		targetUser = userDao.findOne(userId);
		cargoStatus = CargoStatus.Delivered;
		addressStatus = AddressStatus.Delivered;
		generateRandomCargosForDay();
	}
	
	//@Test
	public void testGenerateRandomCargos() {
		targetUser = userDao.findOne(userId);
		cargoStatus = CargoStatus.Delivered;
		addressStatus = AddressStatus.Delivered;
		generateRandomCargos();
	}
	
	@Test
	public void testQueryTest() {
		targetUser = userDao.findOne(userId);
		/**1**/
		Double d = cargoDao.findTotalIncomeByUserBetweenDates(targetUser, actualDate, actualDate);
		System.out.println("#1 ___ " + d);
		d = cargoDao.findIncomeByUserAndPaymentBetweenDates(targetUser, actualDate, actualDate, Payment.Cash);
		System.out.println("#2 ___ " + d);
		/**3**/
		Report r = cargoDao.findReportByUserBetweenDates(targetUser, actualDate, actualDate);
		System.out.println("#3 ___ " + r.getDeliveries() + " " + r.getDistance() + " " + r.getDuration());
		/**4**/
		d = cargoDao.findTotalIncomeByUser(targetUser);
		System.out.println("#4 ___ " + d);
		/**5**/
		d = cargoDao.findIncomeByUserAndPayment(targetUser, Payment.Cash);
		System.out.println("#5 ___ " + d);
		/**6**/
		r = cargoDao.findReportByUser(targetUser);
		System.out.println("#6 ___ " + r.getDeliveries() + " " + r.getDistance() + " " + r.getDuration());
		/**7**/
		d = cargoDao.findAverageIncomeByUserId(targetUser.getId());
		System.out.println("#7 ___ " + d);
		/**8**/
		d = cargoDao.findAverageIncomeByUserIdAndPayment(targetUser.getId(), Payment.Cash.toString());
		System.out.println("#8 ___ " + d);
		/**9**/
		List<Object[]> list = cargoDao.findAverageReportByUserId(targetUser.getId());
		for(Object[] obj : list) {
			System.out.println("#9 ___ " + obj[0] + " " + obj[1] + " " + obj[2]);
		}
		/**10**/
		List<Restaurant> rLi = cargoDao.findRestaurantsByUserBetweenDates(targetUser, actualDate, actualDate);
		if(rLi != null && rLi.size() != 0) {
			for(Restaurant res : rLi) {
				System.out.println("#10 ___ " + res.getName());
			}
			/**11**/
			d = cargoDao.findTotalIncomeByUserAndRestaurantBetweenDates(targetUser, rLi.get(0), actualDate, actualDate);
			System.out.println("#11 ___ " + d);
			/**12**/
			d = cargoDao.findIncomeByUserAndRestaurantAndPaymentBetweenDates(targetUser, rLi.get(0), Payment.Cash, actualDate, actualDate);
			System.out.println("#12 ___ " + d);
		}
		/**13**/
		List<Item> iLi = cargoDao.findItemsByUserOrderByCount(targetUser);
		if(iLi != null && iLi.size() != 0) {
			for(Item item :iLi) {
				System.out.println("#13 ___ " + item.getName());
			}
		}
		/**14**/
		d = logDao.getWorkingDaysByUser(targetUser.getId());
		System.out.println("#14 ___ " + d);
		/**15**/
		d = logDao.getWorkingHoursByUserBetweenDates(targetUser.getId(), actualDate, actualDate);
		System.out.println("#15 ___ " + d);
		/**16**/
		d = logDao.getTotalWorkingHoursByUser(targetUser.getId());
		System.out.println("#16 ___ " + d);
		/**17**/
		d = logDao.getAverageWorkingHoursByUser(targetUser.getId());
		System.out.println("#17 ___ " + d);
	}
	
	public void generateRandomCargos() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -scale);
		createRestaurantList();
		createItemList();
		
		for(int i=0; i<scale; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			actualDate = calendar.getTime();
			for(int j=0; j<rand.nextInt(3) + 3; j++) {
				Cargo cargo = cCargo(targetUser, restaurantList.get(rand.nextInt(restaurantList.size())),
						getRandomAddreses(), cargoStatus, 
						(long) rand.nextInt(100)+1, (long) rand.nextInt(100)+1,
						actualDate, globalId++);
				saveCargo(cargo);
			}
		}
	}
	
	public void generateRandomCargosForDay() {
		actualDate = new Date();
		createRestaurantList();
		createItemList();
		for(int j=0; j<rand.nextInt(3) + 3; j++) {
			while(cargoDao.findCargoByGlobalid(globalId) != null) {
				globalId++;
			}
			Cargo cargo = cCargo(targetUser, restaurantList.get(rand.nextInt(restaurantList.size())),
					getRandomAddreses(), cargoStatus, 
					(long) rand.nextInt(100)+1, (long) rand.nextInt(100)+1,
					actualDate, globalId++);
			saveCargo(cargo);
		}
		
	}
	
	void createRestaurantList() {
		for(int i=0; i<4; i++) {
			restaurantList.add(cRestaurant("Test Restaurant " + i, "Debrecen, Address " + i, "0011223344" + i, 29200L + i));
		}
	}
	
	void createItemList() {
		for(int i=0;i<30;i++) {
			itemList.add(cItem("Food " + i, (double) rand.nextInt(2000)+1, 19000L + i));
		}
	}
	
	List<Address> getRandomAddreses() {
		List<Address> addresses = new ArrayList<>();
		//Címek száma
		for(int i=0; i<rand.nextInt(4)+1; i++) {
			List<AddressDetails> details = new ArrayList<>();
			
			List<Item> availableItems = new ArrayList<>();
			for(Item it : itemList) {
				availableItems.add(it);
			}
			//Itemek száma
			for(int j=0; j<rand.nextInt(4)+1 && availableItems.size() != 0; j++) {
				Item item = availableItems.get(rand.nextInt(availableItems.size()));
				availableItems.remove(item);
				AddressDetails detail = cDetail(item, (long) (rand.nextInt(4)+1));
				details.add(detail);
			}
			Address address = cAddress(addressList[rand.nextInt(addressList.length)], details, actualDate, 
					Payment.getValue((long) (rand.nextInt(4)+1)), addressStatus);
			addresses.add(address);
		}
		for(Address adr : addresses) {
			System.out.println(adr);
		}
		return addresses;
	}
	
	public void saveCargo(Cargo cargo) {
		/**
		 * Update restaurant if necessary
		 */
		Restaurant restaurantFromDb = restaurantDao.findRestaurantByGlobalid(cargo.getRestaurant().getGlobalid());
		if(restaurantFromDb != null) {
			System.out.print(testString + "The Restaurant is already included in the database");
			if(restaurantFromDb.getModdate().before(cargo.getRestaurant().getModdate())) {
				updateRestaurant(restaurantFromDb, cargo.getRestaurant());
				System.out.print(" but outdated");
			}
			cargo.setRestaurant(restaurantFromDb);
			System.out.println();
		}
		/**
		 * Update an item or save a new item
		 */
		for(Address a : cargo.getAddresses()) {
			for(AddressDetails ad : a.getDetails()) {
				Item itemFromDb = itemDao.findItemByGlobalid(ad.getItem().getGlobalid());
				if(itemFromDb != null) {
					System.out.print(testString + "The Item is already included in the database");
					if(itemFromDb.getModdate().before(ad.getItem().getModdate())) {
						updateItem(itemFromDb, ad.getItem());
						System.out.print(" but outdated");
					}
					ad.setItem(itemFromDb);
					System.out.println();
				} else {
					itemDao.save(ad.getItem());
					System.out.println(testString + "New Item inserted to the database");
				}
			}
		}
		User userFromDb = userDao.findByUsername(cargo.getUser().getUsername());
		cargo.setUser(userFromDb);
		System.out.println(cargo.getUser().getUsername());
		/**
		 * Update cargo if outdated
		 */
		Cargo cargoFromDb = cargoDao.findCargoByGlobalid(cargo.getGlobalid());
		if(cargoFromDb != null && cargoFromDb.getModdate().before(cargo.getModdate())) {
			updateCargo(cargoFromDb, cargo);
			cargo = cargoFromDb;
			System.out.println(testString + "The Cargo is already included in the database but outdated");
		} else {
			System.out.println(testString + "New Cargo inserted to the database");
		}
		cargoDao.save(cargo);
	}
	/**
	 * Updaters
	 */
	void updateItem(Item targetItem, Item sourceItem) {
		targetItem.setModdate(sourceItem.getModdate());
		targetItem.setName(sourceItem.getName());
		targetItem.setPrice(sourceItem.getPrice());
	}
	
	void updateRestaurant(Restaurant targetRestaurant, Restaurant sourceRestaurant) {
		targetRestaurant.setModdate(sourceRestaurant.getModdate());
		targetRestaurant.setAddress(sourceRestaurant.getAddress());
		targetRestaurant.setName(sourceRestaurant.getName());
		targetRestaurant.setPhone(sourceRestaurant.getPhone());
	}
	
	void updateCargo(Cargo targetCargo, Cargo sourceCargo) {
		targetCargo.setModdate(sourceCargo.getModdate());
		targetCargo.setUser(sourceCargo.getUser());
		targetCargo.setRestaurant(sourceCargo.getRestaurant());
		targetCargo.setAddresses(sourceCargo.getAddresses());
		targetCargo.setDeliveredAt(sourceCargo.getDeliveredAt());
		targetCargo.setStatus(sourceCargo.getStatus());
		targetCargo.setTotalDistance(sourceCargo.getTotalDistance());
		targetCargo.setTotalDuration(sourceCargo.getTotalDuration());
	}
	/**
	 * Creators
	 */
	Item cItem(String name, Double price, Long globalid) {
		Item i = new Item();
		i.setName(name);
		i.setPrice(price);
		i.setGlobalid(globalid);
		return i;
	}
	
	AddressDetails cDetail(Item item, Long quantity) {
		AddressDetails ad = new AddressDetails();
		ad.setItem(item);
		ad.setQuantity(quantity);
		return ad;
	}
	
	Address cAddress(String address, List<AddressDetails> details, Date deadline, Payment payment, AddressStatus status) {
		Address a = new Address();
		a.setAddress(address);
		a.setDetails(details);
		a.setDeadline(deadline);
		a.setPayment(payment);
		a.setStatus(status);
		return a;
	}
	
	Restaurant cRestaurant(String name, String address, String phone, Long globalid) {
		Restaurant r = new Restaurant();
		r.setName(name);
		r.setAddress(address);
		r.setPhone(phone);
		r.setGlobalid(globalid);
		return r ;
	}
	
	Cargo cCargo(User user, Restaurant restaurant, List<Address> addresses, CargoStatus status,
			Long totalDistance, Long totalDuration, Date deliveredAt, Long globalid) {
		Cargo c = new Cargo();
		c.setUser(user);
		c.setRestaurant(restaurant);
		c.setAddresses(addresses);
		c.setStatus(status);
		c.setTotalDistance(totalDistance);
		c.setTotalDuration(totalDuration);
		c.setDeliveredAt(deliveredAt);
		c.setGlobalid(globalid);
		return c ;
	}

}
