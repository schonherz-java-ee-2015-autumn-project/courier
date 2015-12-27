package hu.schonherz.java.training.courier.service.impl;

import javax.ejb.Stateless;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.jobs.ee.ejb.EJBInvokerJob;

import hu.schonherz.java.training.courier.service.QuartzManagerLocal;
import hu.schonherz.java.training.courier.service.QuartzManagerRemote;

@Stateless
public class QuartzManagerImpl implements QuartzManagerLocal, QuartzManagerRemote {

	// Itt jön létre a Scheduler(ütemező) amiben a munkánk létrejön
	@Override
	public void initialize(String cronExpr, String methodName) {

		JobDetail job = new JobDetail("Scheduler", EJBInvokerJob.class);
		// Kiszedjük az EJB-nket
		job.getJobDataMap().put("ejb", "java:comp/env/ejb/SchedulerService");
		// Belerakjuk a metódusainkat majd
		job.getJobDataMap().put("method", methodName);
		
		Object[] jdArgs = new Object[0];
		job.getJobDataMap().put("args", jdArgs);
		// idő alapján való triggeléshez
		CronTrigger cronTrigger = new CronTrigger("WebService Scheduler");
		
		try {
			// beállítjuk a trigget
			cronTrigger.setCronExpression(cronExpr);
			// létrejön egy Scheduler ami megkapja az alapértelmezett Schedulert
			// a továbbiakhoz.
			Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
			// itt pedig betesszük a munkánkat a Schedulerbe
			sched.scheduleJob(job, cronTrigger);

		} catch (Exception e) {

		}

	}

	// Itt fog lezárulni a job, amint "végzett" de a mi esetünkben ez most az
	// egész alkalmazás alatt fog menni
	@Override
	public void shutdown() {
		try {
			Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
			sched.shutdown();
		} catch (Exception e) {

		}

	}

}
