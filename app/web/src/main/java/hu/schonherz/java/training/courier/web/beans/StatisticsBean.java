package hu.schonherz.java.training.courier.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.LogServiceLocal;
import hu.schonherz.java.training.courier.service.vo.ItemVO;
import hu.schonherz.java.training.courier.service.vo.RestaurantVO;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@ManagedBean(name = "statisticsBean")
@ViewScoped
public class StatisticsBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	CargoServiceLocal cargoService;
	@EJB
	LogServiceLocal logService;
	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;
	private Date currentDate;
	private Date startDate;
	private Date endDate;
	
	private CargoReport incomeBetween;
	private CargoReport incomeTotal;
	private CargoReport incomeAverage;
	
	private List<RestaurantVO> restaurantsBetween;
	private List<CargoReport> restaurantReports;
	private List<ItemVO> popularItems;
	
	private Double activeDays;
	
	@PostConstruct
	public void init() {
		currentDate = new Date();
		startDate = currentDate;
		endDate = currentDate;
		incomeBetween = new CargoReport();
		incomeTotal = new CargoReport();
		incomeAverage = new CargoReport();
		setData();
		setDataBetween();
	}
	
	public void setData() {
		UserVO user = getUserSessionBean().getUserVO();
		try {
			incomeTotal.setTotal(getCargoService().findTotalIncomeByUser(user));
			incomeTotal.setCash(getCargoService().findIncomeByUserAndPayment(user, Payment.Cash));
			incomeTotal.setCard(getCargoService().findIncomeByUserAndPayment(user, Payment.Card));
			incomeTotal.setSzep(getCargoService().findIncomeByUserAndPayment(user, Payment.SZEP));
			incomeTotal.setVoucher(getCargoService().findIncomeByUserAndPayment(user, Payment.Voucher));
			incomeTotal.setHour(getLogService().getTotalWorkingHoursByUser(user));
			
			incomeAverage.setTotal(getCargoService().findAverageIncomeByUserId(user));
			incomeAverage.setCash(getCargoService().findAverageIncomeByUserIdAndPayment(user, Payment.Cash));
			incomeAverage.setCard(getCargoService().findAverageIncomeByUserIdAndPayment(user, Payment.Card));
			incomeAverage.setSzep(getCargoService().findAverageIncomeByUserIdAndPayment(user, Payment.SZEP));
			incomeAverage.setVoucher(getCargoService().findAverageIncomeByUserIdAndPayment(user, Payment.Voucher));
			incomeAverage.setHour(getLogService().getAverageWorkingHoursByUser(user));
			
			incomeTotal.setReport(getCargoService().findReportByUser(user));
			incomeAverage.setReport(getCargoService().findAverageReportByUserId(user));
			
			popularItems = getCargoService().findItemsByUserOrderByCount(user);
			List<ItemVO> items = new ArrayList<>();
			for(int i=0; i<5 && i<popularItems.size(); i++) {
				items.add(popularItems.get(i));
			}
			popularItems = items;
			
			activeDays = getLogService().getWorkingDaysByUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDataBetween() {
		UserVO user = getUserSessionBean().getUserVO();
		try {
			incomeBetween.setTotal(getCargoService().findTotalIncomeByUserBetweenDates(user, startDate, endDate));
			incomeBetween.setCash(getCargoService().findIncomeByUserAndPaymentBetweenDates(user, startDate, endDate, Payment.Cash));
			incomeBetween.setCard(getCargoService().findIncomeByUserAndPaymentBetweenDates(user, startDate, endDate, Payment.Card));
			incomeBetween.setSzep(getCargoService().findIncomeByUserAndPaymentBetweenDates(user, startDate, endDate, Payment.SZEP));
			incomeBetween.setVoucher(getCargoService().findIncomeByUserAndPaymentBetweenDates(user, startDate, endDate, Payment.Voucher));
			incomeBetween.setHour(getLogService().getWorkingHoursByUserBetweenDates(user, startDate, endDate));
			
			incomeBetween.setReport(getCargoService().findReportByUserBetweenDates(user, startDate, endDate));

			restaurantsBetween = getCargoService().findRestaurantsByUserBetweenDates(user, startDate, endDate);
			restaurantReports = new ArrayList<>();
			for(RestaurantVO restaurant : restaurantsBetween) {
				CargoReport report = new CargoReport();
				report.setName(restaurant.getName());
				report.setTotal(getCargoService().findTotalIncomeByUserAndRestaurantBetweenDates(user, restaurant, startDate, endDate));
				report.setCash(getCargoService().findIncomeByUserAndRestaurantAndPaymentBetweenDates(user, restaurant, Payment.Cash, startDate, endDate));
				report.setCard(getCargoService().findIncomeByUserAndRestaurantAndPaymentBetweenDates(user, restaurant, Payment.Card, startDate, endDate));
				report.setSzep(getCargoService().findIncomeByUserAndRestaurantAndPaymentBetweenDates(user, restaurant, Payment.SZEP, startDate, endDate));
				report.setVoucher(getCargoService().findIncomeByUserAndRestaurantAndPaymentBetweenDates(user, restaurant, Payment.Voucher, startDate, endDate));
				restaurantReports.add(report);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void submit() {
		setDataBetween();
	}
    
	public CargoServiceLocal getCargoService() {
		return cargoService;
	}

	public void setCargoService(CargoServiceLocal cargoService) {
		this.cargoService = cargoService;
	}

	public LogServiceLocal getLogService() {
		return logService;
	}

	public void setLogService(LogServiceLocal logService) {
		this.logService = logService;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		if(endDate.before(startDate)) endDate = startDate;
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		if(endDate.before(startDate)) startDate = endDate;
		this.endDate = endDate;
	}

	public CargoReport getIncomeBetween() {
		return incomeBetween;
	}

	public void setIncomeBetween(CargoReport incomeBetween) {
		this.incomeBetween = incomeBetween;
	}

	public CargoReport getIncomeTotal() {
		return incomeTotal;
	}

	public void setIncomeTotal(CargoReport incomeTotal) {
		this.incomeTotal = incomeTotal;
	}

	public CargoReport getIncomeAverage() {
		return incomeAverage;
	}

	public void setIncomeAverage(CargoReport incomeAverage) {
		this.incomeAverage = incomeAverage;
	}

	public List<RestaurantVO> getRestaurantsBetween() {
		return restaurantsBetween;
	}

	public void setRestaurantsBetween(List<RestaurantVO> restaurantsBetween) {
		this.restaurantsBetween = restaurantsBetween;
	}

	public List<CargoReport> getRestaurantReports() {
		return restaurantReports;
	}

	public void setRestaurantReports(List<CargoReport> restaurantReports) {
		this.restaurantReports = restaurantReports;
	}

	public List<ItemVO> getPopularItems() {
		return popularItems;
	}

	public void setPopularItems(List<ItemVO> popularItems) {
		this.popularItems = popularItems;
	}

	public Double getActiveDays() {
		return activeDays;
	}

	public void setActiveDays(Double activeDays) {
		this.activeDays = activeDays;
	}
}
