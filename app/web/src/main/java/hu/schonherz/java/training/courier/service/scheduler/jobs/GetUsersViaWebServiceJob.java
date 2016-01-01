package hu.schonherz.java.training.courier.service.scheduler.jobs;

import java.util.Date;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.jobs.ee.ejb.EJBInvokerJob;

import hu.schonherz.java.training.courier.service.webservice.UserWebServiceLocal;

public class GetUsersViaWebServiceJob extends EJBInvokerJob implements Job {

	final static Logger logger = Logger.getLogger(GetUsersViaWebServiceJob.class);

	@EJB
	UserWebServiceLocal userWebServiceLocal;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("INFO: GetUsersViaWebServiceJob is called now:" + new Date());
	}

}
