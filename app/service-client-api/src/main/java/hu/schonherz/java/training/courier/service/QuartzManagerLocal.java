package hu.schonherz.java.training.courier.service;

import javax.ejb.Local;

@Local
public interface QuartzManagerLocal {
	public void initialize(String cronExpr);
    public void shutdown();
}