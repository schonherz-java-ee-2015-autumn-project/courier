package hu.schonherz.java.training.courier.web.beans;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.entities.Report;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@ManagedBean(name = "statisticsBean")
@ViewScoped
public class StatisticsBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	CargoServiceLocal cargoService;
	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;
	private Date currentDate;
	private Date startDate;
	private Date endDate;
	
	private Double incomeTotalBetween;
	private Double incomeCashBetween;
	private Double incomeCardBetween;
	private Double incomeSzepBetween;
	private Double incomeVoucherBetween;
	
	private Double incomeTotalTotal;
	private Double incomeCashTotal;
	private Double incomeCardTotal;
	private Double incomeSzepTotal;
	private Double incomeVoucherTotal;
	
	private Double incomeTotalAverage;
	private Double incomeCashAverage;
	private Double incomeCardAverage;
	private Double incomeSzepAverage;
	private Double incomeVoucherAverage;
	
	private Report reportBetween;
	private Report reportTotal;
	private Report reportAverage;
	
	@PostConstruct
	public void init() {
		currentDate = new Date();
		startDate = currentDate;
		endDate = currentDate;
		setData();
		setDataBetween();
	}
	
	public void setData() {
		UserVO user = getUserSessionBean().getUserVO();
		try {
			incomeTotalTotal = getCargoService().findTotalIncomeByUser(user);
			incomeCashTotal = getCargoService().findIncomeByUserAndPayment(user, Payment.Cash);
			incomeCardTotal = getCargoService().findIncomeByUserAndPayment(user, Payment.Card);
			incomeSzepTotal = getCargoService().findIncomeByUserAndPayment(user, Payment.SZEP);
			incomeVoucherTotal = getCargoService().findIncomeByUserAndPayment(user, Payment.Voucher);
			
			incomeTotalAverage = getCargoService().findAverageIncomeByUserId(user);
			incomeCashAverage = getCargoService().findAverageIncomeByUserIdAndPayment(user, Payment.Cash);
			incomeCardAverage = getCargoService().findAverageIncomeByUserIdAndPayment(user, Payment.Card);
			incomeSzepAverage = getCargoService().findAverageIncomeByUserIdAndPayment(user, Payment.SZEP);
			incomeVoucherAverage = getCargoService().findAverageIncomeByUserIdAndPayment(user, Payment.Voucher);
			
			reportTotal = getCargoService().findReportByUser(user);
			reportAverage = getCargoService().findAverageReportByUserId(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDataBetween() {
		UserVO user = getUserSessionBean().getUserVO();
		try {
			incomeTotalBetween = getCargoService().findTotalIncomeByUserBetweenDates(user, startDate, endDate);
			incomeCashBetween = getCargoService().findIncomeByUserAndPaymentBetweenDates(user, startDate, endDate, Payment.Cash);
			incomeCardBetween = getCargoService().findIncomeByUserAndPaymentBetweenDates(user, startDate, endDate, Payment.Card);
			incomeSzepBetween = getCargoService().findIncomeByUserAndPaymentBetweenDates(user, startDate, endDate, Payment.SZEP);
			incomeVoucherBetween = getCargoService().findIncomeByUserAndPaymentBetweenDates(user, startDate, endDate, Payment.Voucher);
			
			reportBetween = getCargoService().findReportByUserBetweenDates(user, startDate, endDate);
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


	public Double getIncomeTotalBetween() {
		return incomeTotalBetween;
	}


	public void setIncomeTotalBetween(Double incomeTotalBetween) {
		this.incomeTotalBetween = incomeTotalBetween;
	}


	public Double getIncomeCashBetween() {
		return incomeCashBetween;
	}


	public void setIncomeCashBetween(Double incomeCashBetween) {
		this.incomeCashBetween = incomeCashBetween;
	}


	public Double getIncomeCardBetween() {
		return incomeCardBetween;
	}


	public void setIncomeCardBetween(Double incomeCardBetween) {
		this.incomeCardBetween = incomeCardBetween;
	}


	public Double getIncomeSzepBetween() {
		return incomeSzepBetween;
	}


	public void setIncomeSzepBetween(Double incomeSzepBetween) {
		this.incomeSzepBetween = incomeSzepBetween;
	}


	public Double getIncomeVoucherBetween() {
		return incomeVoucherBetween;
	}


	public void setIncomeVoucherBetween(Double incomeVoucherBetween) {
		this.incomeVoucherBetween = incomeVoucherBetween;
	}


	public Double getIncomeTotalTotal() {
		return incomeTotalTotal;
	}


	public void setIncomeTotalTotal(Double incomeTotalTotal) {
		this.incomeTotalTotal = incomeTotalTotal;
	}


	public Double getIncomeCashTotal() {
		return incomeCashTotal;
	}


	public void setIncomeCashTotal(Double incomeCashTotal) {
		this.incomeCashTotal = incomeCashTotal;
	}


	public Double getIncomeCardTotal() {
		return incomeCardTotal;
	}


	public void setIncomeCardTotal(Double incomeCardTotal) {
		this.incomeCardTotal = incomeCardTotal;
	}


	public Double getIncomeSzepTotal() {
		return incomeSzepTotal;
	}


	public void setIncomeSzepTotal(Double incomeSzepTotal) {
		this.incomeSzepTotal = incomeSzepTotal;
	}


	public Double getIncomeVoucherTotal() {
		return incomeVoucherTotal;
	}


	public void setIncomeVoucherTotal(Double incomeVoucherTotal) {
		this.incomeVoucherTotal = incomeVoucherTotal;
	}


	public Double getIncomeTotalAverage() {
		return incomeTotalAverage;
	}


	public void setIncomeTotalAverage(Double incomeTotalAverage) {
		this.incomeTotalAverage = incomeTotalAverage;
	}


	public Double getIncomeCashAverage() {
		return incomeCashAverage;
	}


	public void setIncomeCashAverage(Double incomeCashAverage) {
		this.incomeCashAverage = incomeCashAverage;
	}


	public Double getIncomeCardAverage() {
		return incomeCardAverage;
	}


	public void setIncomeCardAverage(Double incomeCardAverage) {
		this.incomeCardAverage = incomeCardAverage;
	}


	public Double getIncomeSzepAverage() {
		return incomeSzepAverage;
	}


	public void setIncomeSzepAverage(Double incomeSzepAverage) {
		this.incomeSzepAverage = incomeSzepAverage;
	}


	public Double getIncomeVoucherAverage() {
		return incomeVoucherAverage;
	}


	public void setIncomeVoucherAverage(Double incomeVoucherAverage) {
		this.incomeVoucherAverage = incomeVoucherAverage;
	}


	public Report getReportBetween() {
		return reportBetween;
	}


	public void setReportBetween(Report reportBetween) {
		this.reportBetween = reportBetween;
	}


	public Report getReportTotal() {
		return reportTotal;
	}


	public void setReportTotal(Report reportTotal) {
		this.reportTotal = reportTotal;
	}


	public Report getReportAverage() {
		return reportAverage;
	}


	public void setReportAverage(Report reportAverage) {
		this.reportAverage = reportAverage;
	}
	
}
