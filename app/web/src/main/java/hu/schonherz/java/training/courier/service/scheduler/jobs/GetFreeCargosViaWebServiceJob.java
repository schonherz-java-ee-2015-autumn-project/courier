package hu.schonherz.java.training.courier.service.scheduler.jobs;

import java.util.Date;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.jobs.ee.ejb.EJBInvokerJob;

import hu.schonherz.java.training.courier.service.webservice.CargoWebServiceLocal;

public class GetFreeCargosViaWebServiceJob extends EJBInvokerJob implements Job {

	final static Logger logger = Logger.getLogger(GetFreeCargosViaWebServiceJob.class);

	@EJB
	CargoWebServiceLocal cargoWebServiceLocal;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("INFO: GetFreeCargosViaWebServiceJob is called now:" + new Date());
	}

}
