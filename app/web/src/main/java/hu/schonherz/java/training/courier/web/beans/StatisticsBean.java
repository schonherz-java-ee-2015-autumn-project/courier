package hu.schonherz.java.training.courier.web.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import hu.schonherz.java.training.courier.entities.Payment;
import hu.schonherz.java.training.courier.service.CargoServiceLocal;

@ManagedBean(name = "statisticsBean")
@ViewScoped
public class StatisticsBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	CargoServiceLocal cargoService;
	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;
	private BarChartModel barModel;
	private Calendar calendar;
	private int scale = 10;
	
	@PostConstruct
	public void init() {
		calendar = Calendar.getInstance();
		createBarModel();
	}
     
    private void createBarModel() {
		calendar.add(Calendar.DAY_OF_MONTH, -scale);
        barModel = new BarChartModel();
        ChartSeries series1 = new ChartSeries();
        ChartSeries series2 = new ChartSeries();
        ChartSeries series3 = new ChartSeries();
        ChartSeries series4 = new ChartSeries();
        series1.setLabel("Cash");
        series2.setLabel("Card");
        series3.setLabel("SZEP");
        series4.setLabel("Voucher");
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		for(int i=0;i<scale;i++) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			d = calendar.getTime();
			Double incomeByCash = null;
			Double incomeByCard = null;
			Double incomeBySzep = null;
			Double incomeByVoucher = null;
			try {
				incomeByCash = cargoService.findDailyIncomeByPayment(getUserSessionBean().getUserVO(), dateFormat.format(d), Payment.Cash);
				incomeByCard = cargoService.findDailyIncomeByPayment(getUserSessionBean().getUserVO(), dateFormat.format(d), Payment.Card);
				incomeBySzep = cargoService.findDailyIncomeByPayment(getUserSessionBean().getUserVO(), dateFormat.format(d), Payment.SZEP);
				incomeByVoucher = cargoService.findDailyIncomeByPayment(getUserSessionBean().getUserVO(), dateFormat.format(d), Payment.Voucher);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(incomeByCash == null) incomeByCash = 0.0;
			if(incomeByCard == null) incomeByCard = 0.0;
			if(incomeBySzep == null) incomeBySzep = 0.0;
			if(incomeByVoucher == null) incomeByVoucher = 0.0;
			series1.set(dateFormat.format(d), (Number) incomeByCash);
			series2.set(dateFormat.format(d), (Number) incomeByCard);
			series3.set(dateFormat.format(d), (Number) incomeBySzep);
			series4.set(dateFormat.format(d), (Number) incomeByVoucher);
		}
 
        barModel.addSeries(series1);
        barModel.addSeries(series2);
        barModel.addSeries(series3);
        barModel.addSeries(series4);
         
        barModel.setTitle("Daily Income");
        barModel.setLegendPosition("nw");
        barModel.getAxis(AxisType.Y).setLabel("Income");
        barModel.setStacked(true);
        Axis axis = barModel.getAxis(AxisType.X);
        axis.setTickAngle(-50);
        barModel.getAxes().put(AxisType.X, axis);
    }
    
    public void previousDays() {
		calendar.add(Calendar.DAY_OF_MONTH, -scale);
		createBarModel();
    }
    
    public void nextDays() {
		calendar.add(Calendar.DAY_OF_MONTH, scale);
		createBarModel();
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
	
	public BarChartModel getBarModel() {
        return barModel;
    }

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
}
