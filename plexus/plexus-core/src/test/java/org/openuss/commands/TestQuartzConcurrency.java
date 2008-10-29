package org.openuss.commands;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestQuartzConcurrency {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(TestQuartzConcurrency.class);

	private static final Date now = DateUtils.addSeconds(new Date(), 5);

	private static Scheduler scheduler;

	public static void main(String[] args) {
		logger.info("initializing legacy registration");

		ApplicationContext context = new ClassPathXmlApplicationContext(getConfigLocations());
		scheduler = (Scheduler) context.getBean("clusterScheduler");
		try {
			scheduler.standby();
			logger.info(prefix()+" define job ");
			JobDetail jobDetail = (JobDetail) context.getBean("exampleJob");
			scheduler.addJob(jobDetail, true);
			logger.info(prefix()+" define triggers");
			for (long i = 0; i < 1; i++) {
				Trigger trigger = new SimpleTrigger("T" + i + "-" + System.currentTimeMillis(),	Scheduler.DEFAULT_GROUP, now);
				trigger.setVolatility(true);
				trigger.setJobName("example_job");
				JobDataMap jdm = new JobDataMap();
				jdm.put("domainId", i);
				trigger.setJobDataMap(jdm);
				scheduler.scheduleJob(trigger);
			}

			logger.info(prefix()+" starting scheduler");
			scheduler.start();
			logger.info(prefix()+" finished ");
			scheduler.shutdown(true);
		} catch (SchedulerException e) {
			logger.error(e);
		}
		logger.info("exit");
	}

	private static String prefix() {
		try {
			return "Scheduler "+scheduler.getSchedulerInstanceId();
		} catch (SchedulerException e) {
			return "Schedure <exception> ";
		}
	}

	protected static String[] getConfigLocations() {
		return new String[] { "classpath*:applicationContext-quartz.xml" };
	}

}
