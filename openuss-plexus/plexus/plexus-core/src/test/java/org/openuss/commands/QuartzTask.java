/**
 * 
 */
package org.openuss.commands;

import org.apache.log4j.Logger;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class QuartzTask implements Runnable {

	private static final Logger logger = Logger.getLogger(QuartzTask.class);
	
	private Scheduler scheduler;
	
	public QuartzTask () {
		ApplicationContext context = new ClassPathXmlApplicationContext(TestQuartzConcurrency.getConfigLocations());
		scheduler = (Scheduler) context.getBean("clusterScheduler");
		try {
			logger.info("Name="+scheduler.getSchedulerName()+ " InstanceId="+scheduler.getSchedulerInstanceId());
		} catch (SchedulerException e) {
			logger.error(e);
		}
	}

	public void run() {
		try {
			scheduler.start();
			while(triggerCount(scheduler)>0);
		} catch (SchedulerException e) {
			logger.error(e);
		}
	}
	
	public static int triggerCount(Scheduler scheduler) {
		int count = 0;
		try {
			count = scheduler.getTriggersOfJob("example_job", Scheduler.DEFAULT_GROUP).length;
		} catch (SchedulerException e) {
			logger.error(e);
			count=0;
		}
		return count;
	}
}