package hu.schonherz.java.training.courier.service.scheduler.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;

import hu.schonherz.java.training.courier.service.UserServiceLocal;
import hu.schonherz.java.training.courier.service.scheduler.jobs.GetUsersViaWebServiceJob;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SchedulerServiceImpl {

	static Logger logger = Logger.getLogger(SchedulerServiceImpl.class);

	private Scheduler scheduler;

	@Inject
	private JobFactory cdiJobFactory;

	@PostConstruct
	public void scheduleJobs() {

		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.setJobFactory(cdiJobFactory);

			JobKey webServiceJobKey = JobKey.jobKey("getUsersViaWebService", "WebService");
			JobDetail webServiceJob = JobBuilder.newJob(GetUsersViaWebServiceJob.class).withIdentity(webServiceJobKey)
					.build();

			TriggerKey webServiceGetUsersTriggerKey = TriggerKey.triggerKey("getUsersViaWebServiceTrigger");
			CronTrigger webServiceGetUsersTrigger = TriggerBuilder.newTrigger()
					.withIdentity(webServiceGetUsersTriggerKey).startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * *?")).build();

			scheduler.start();
			scheduler.scheduleJob(webServiceJob, webServiceGetUsersTrigger);

			printJobsAndTriggers(scheduler);

		} catch (SchedulerException e) {
			logger.info("ERROR:", e);
		}

		logger.info("INFO:Jobs are going to be saved.");

	}

	private void printJobsAndTriggers(Scheduler scheduler) throws SchedulerException {
		logger.info("Quartz Scheduler:" + scheduler.getSchedulerName());
		for (String group : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey> groupEquals(group))) {
				logger.info("Found job identified by " + jobKey);
			}
		}
		for (String group : scheduler.getTriggerGroupNames()) {
			for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.<TriggerKey> groupEquals(group))) {
				logger.info("Found trigger identified by " + triggerKey);
			}
		}
	}

	@PreDestroy
	public void stopJobs() {
		if (scheduler != null) {
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				logger.info("ERROR:", e);
			}
		}
	}
}