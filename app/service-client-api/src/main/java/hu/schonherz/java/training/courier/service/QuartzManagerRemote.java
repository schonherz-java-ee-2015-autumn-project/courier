package hu.schonherz.java.training.courier.service;

import javax.ejb.Remote;

@Remote
public interface QuartzManagerRemote {
	public void initialize(String cronExpr,String methodName);
    public void shutdown();
}
