package hu.schonherz.java.training.courier.service.scheduler.jobs;

import java.util.Date;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.ExecuteInJTATransaction;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.jobs.ee.ejb.EJBInvokerJob;

import hu.schonherz.java.training.courier.service.webservice.UserWebServiceRemote;

@DisallowConcurrentExecution
@ExecuteInJTATransaction
public class GetUsersViaWebServiceJob extends EJBInvokerJob implements Job {

	final static Logger logger = Logger.getLogger(GetUsersViaWebServiceJob.class);

	UserWebServiceRemote userWebService;

	// max 10-szer
	private final int MAXIMUM_TRIES = 10;
	// 10 percenként újra próbálkozunk
	private final int WAIT_TIME_MILLISEC = 600000;
	private final String UNSUCCESFUL_TRIES = "user_unsuccessful_asks";

	public GetUsersViaWebServiceJob() {
		init();
	}

	private void init() {
		Context context = null;
		try {
			context = new InitialContext();
			userWebService = (UserWebServiceRemote) context.lookup(
					"userWebService#hu.schonherz.java.training.courier.service.webservice.UserWebServiceRemote");
			// "userWebService#hu.schonherz.java.training.courier.service.webservice.UserWebServiceRemote"
			if (userWebService == null) {
				logger.info("ERROR: userWebService is NULL");

			} else {
				logger.info("INFO: userWebService is NOT NULL");
			}
		} catch (NamingException e) {
			logger.info("ERROR:", e);
		} finally {
			try {
				context.close();
			} catch (NamingException e) {
			}
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		int count = 0;
		try {
			count = dataMap.getIntValue(UNSUCCESFUL_TRIES);
		} catch (Exception ex) {
			dataMap.putAsString(UNSUCCESFUL_TRIES, 0);
		}

		if (count >= MAXIMUM_TRIES) {
			JobExecutionException e1 = new JobExecutionException("Could not do the job in the maximum limit.");
			e1.setUnscheduleAllTriggers(true);
			throw e1;
		}

		try {
			userWebService.getUsersFromAdministration();
			logger.info("INFO: GetUsersViaWebServiceJob is called now:" + new Date());
			dataMap.putAsString(UNSUCCESFUL_TRIES, 0);
		} catch (Exception e1) {
			count++;
			logger.info("INFO: GetUsersViaWebServiceJob is called but could not finish its job at:" + new Date()
					+ " count number:" + count);
			dataMap.putAsString(UNSUCCESFUL_TRIES, count);
			JobExecutionException e3 = new JobExecutionException(e1);

			try {
				Thread.sleep(WAIT_TIME_MILLISEC);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}

			e3.setRefireImmediately(true);
			throw e3;
		}

	}

}
