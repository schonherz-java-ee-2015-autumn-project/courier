package hu.schonherz.java.training.courier.web.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import hu.schonherz.java.training.courier.service.LogServiceLocal;
import hu.schonherz.java.training.courier.service.vo.UserVO;

@ManagedBean(name = "chartBean")
@ViewScoped
public class ChartBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	CargoServiceLocal cargoService;
	@EJB
	LogServiceLocal logService;
	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;
	private String oneMenu;
	private BarChartModel barModel;
	private Calendar calendar;
	private int scale = 7;
	
	@PostConstruct
	public void init() {
		calendar = Calendar.getInstance();
		oneMenu = "income";
		createBarModel();
	}
     
    private void createBarModel() {
    	UserVO user = getUserSessionBean().getUserVO();
		calendar.add(Calendar.DAY_OF_MONTH, -scale);
        barModel = new BarChartModel();
        List<ChartSeries> seriesList = new ArrayList<>();
        
        DateFormat dateFormat = new SimpleDateFormat("MMM d");
		Date day;
        
        if(oneMenu.equals("income")) {
            for(int i = 1; i<5; i++) {
            	ChartSeries series = new ChartSeries();
            	series.setLabel(Payment.getValue( (long) i ).toString());
            	seriesList.add(series);
            }
        } else {
        	ChartSeries series = new ChartSeries();
        	seriesList.add(series);
        }
        
		for(int i=0;i<scale;i++) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			day = calendar.getTime();
			
			for(ChartSeries series : seriesList) {
				Double value = null;
				try {
					if(oneMenu.equals("income")) {
						value = cargoService.findIncomeByUserAndPaymentBetweenDates(user, 
								day, day, Payment.getValue( (long) seriesList.indexOf(series) + 1L ) );
					}
					if(oneMenu.equals("distance")) {
						value = cargoService.findReportByUserBetweenDates(user, day, day).getDistance();
					}
					if(oneMenu.equals("duration")) {
						value = cargoService.findReportByUserBetweenDates(user, day, day).getDuration();
					}
					if(oneMenu.equals("deliveries")) {
						value = cargoService.findReportByUserBetweenDates(user, day, day).getDeliveries();
					}
					if(oneMenu.equals("hours")) {
						value = logService.getWorkingHoursByUserBetweenDates(user, day, day);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(value == null) value = 0.0;
				series.set(dateFormat.format(day), (Number) value);
			}
		}
		for(ChartSeries series : seriesList) {
			barModel.addSeries(series);
		}
         
		if(oneMenu.equals("income")) {
			barModel.setLegendPosition("nw");
			barModel.setStacked(true);
        }
        Axis axis = barModel.getAxis(AxisType.X);
        axis.setTickAngle(-25);
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
    public void selectChart() {
    	createBarModel();
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

	public String getOneMenu() {
		return oneMenu;
	}

	public void setOneMenu(String oneMenu) {
		this.oneMenu = oneMenu;
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
