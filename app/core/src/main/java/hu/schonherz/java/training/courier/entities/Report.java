package hu.schonherz.java.training.courier.entities;

import java.math.BigDecimal;

public class Report {
	
	private Double deliveries;
	private Double distance;
	private Double duration;
	
	public Report(Object cargos, Object distance, Object duration) {
		super();
		if(cargos != null) {
			if(cargos.getClass() == BigDecimal.class) this.deliveries = ((BigDecimal) cargos).doubleValue();
			if(cargos.getClass() == Double.class) this.deliveries = (Double) cargos;
			if(cargos.getClass() == Long.class) this.deliveries = (double)(Long) cargos;
		}
		if(distance != null) {
			if(distance.getClass() == BigDecimal.class) this.distance = ((BigDecimal) distance).doubleValue();
			if(distance.getClass() == Double.class) this.distance = (Double) distance;
			if(distance.getClass() == Long.class) this.distance = (double)(Long) distance;
		}
		if(duration != null) {
			if(duration.getClass() == BigDecimal.class) this.duration = ((BigDecimal) duration).doubleValue();
			if(duration.getClass() == Double.class) this.duration = (Double) duration;
			if(duration.getClass() == Long.class) this.duration = (double)(Long) duration;
		}
	}
	
	public Double getDeliveries() {
		return deliveries;
	}
	public void setDeliveries(Double cargos) {
		this.deliveries = cargos;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	
}
