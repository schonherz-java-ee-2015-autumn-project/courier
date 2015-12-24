package hu.schonherz.java.training.courier.service;

import javax.ejb.Remote;

@Remote
public interface QuartzManagerRemote {
	public void initialize(String cronExpr);
    public void shutdown();
}
