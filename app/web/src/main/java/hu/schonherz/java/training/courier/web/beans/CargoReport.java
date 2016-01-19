package hu.schonherz.java.training.courier.web.beans;

import hu.schonherz.java.training.courier.entities.Report;

public class CargoReport {
	
	private String name;
	
	private Double total;
	private Double cash;
	private Double card;
	private Double szep;
	private Double voucher;
	private Double hour;
	
	private Report report;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Double getCard() {
		return card;
	}

	public void setCard(Double card) {
		this.card = card;
	}

	public Double getSzep() {
		return szep;
	}

	public void setSzep(Double szep) {
		this.szep = szep;
	}

	public Double getVoucher() {
		return voucher;
	}

	public void setVoucher(Double voucher) {
		this.voucher = voucher;
	}

	public Double getHour() {
		return hour;
	}

	public void setHour(Double hour) {
		this.hour = hour;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}
}
