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

import hu.schonherz.java.training.courier.service.webservice.CargoWebServiceLocal;
import hu.schonherz.java.training.courier.service.webservice.CargoWebServiceRemote;

@DisallowConcurrentExecution
@ExecuteInJTATransaction
public class GetFreeCargosViaWebServiceJob extends EJBInvokerJob implements Job {

	final static Logger logger = Logger.getLogger(GetFreeCargosViaWebServiceJob.class);

	CargoWebServiceRemote cargoWebService;

	private final int MAXIMUM_TRIES = 5;
	// 10 másodpercenként újra próbálkozunk.
	private final int WAIT_TIME_MILLISEC = 10000;
	private final String UNSUCCESFUL_TRIES = "cargo_unsuccessful_asks";

	public GetFreeCargosViaWebServiceJob() {
		init();
	}

	private void init() {

		Context context = null;
		try {

			context = new InitialContext();
			logger.info("INFO: Trying to lookUp cargoWebService!");
			cargoWebService = (CargoWebServiceRemote) context.lookup(
					"cargoWebService#hu.schonherz.java.training.courier.service.webservice.CargoWebServiceRemote");
			if (cargoWebService == null) {
				logger.info("ERROR:CargoWebService is NULL");

			} else {
				logger.info("INFO:CargoWebService is NOT NULL");
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
			cargoWebService.getFreeCargosFromAdministration();
			logger.info("INFO: GetFreeCargosViaWebServiceJob is called now:" + new Date());
			dataMap.putAsString(UNSUCCESFUL_TRIES, 0);
		} catch (Exception e1) {
			count++;
			logger.info("INFO: GetFreeCargosViaWebServiceJob is called but could not finish its job at:" + new Date()
					+ " count number:" + count);
			e1.printStackTrace();
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
