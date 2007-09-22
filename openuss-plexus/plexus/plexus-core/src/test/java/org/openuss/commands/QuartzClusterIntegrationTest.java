// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.commands;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * JUnit Test for Spring Hibernate CommandService class.
 * 
 * @see org.openuss.commands.CommandService
 */
public class QuartzClusterIntegrationTest extends AbstractDependencyInjectionSpringContextTests {

	private Scheduler clusterScheduler;
	private JobDetail exampleJob;
	private static final Date now = new Date();

	public void testOnceCommands() {
		Trigger trigger30 = new SimpleTrigger("T/"+RandomStringUtils.randomAlphabetic(5), Scheduler.DEFAULT_GROUP, DateUtils.addSeconds(now, 30));
		Trigger trigger15 = new SimpleTrigger("T/"+RandomStringUtils.randomAlphabetic(5), Scheduler.DEFAULT_GROUP, DateUtils.addSeconds(now, 15));
		try {
			String name = "JD/"+RandomStringUtils.randomAlphabetic(5);
			JobDetail detail = new JobDetail(name,Scheduler.DEFAULT_GROUP,ExampleJob.class, false, true, false );
			clusterScheduler.scheduleJob(detail, trigger30);
			
			name = "JD/"+RandomStringUtils.randomAlphabetic(5);
			exampleJob.setName(name);
			clusterScheduler.scheduleJob(exampleJob, trigger15);

			Trigger trigger = new SimpleTrigger("T/"+RandomStringUtils.randomAlphabetic(5), Scheduler.DEFAULT_GROUP, DateUtils.addSeconds(now, 15));
			
			JobDataMap jdm = new JobDataMap();
			jdm.put("name", "Retrigger a job");
			jdm.put("domainId", 15L);
			
			trigger.setJobDataMap(jdm);
			trigger.setJobName(name);
			trigger.setJobGroup("DEFAULT");
			clusterScheduler.scheduleJob(trigger);
		} catch (SchedulerException e) {
			logger.error(e);
		}
		logger.debug("ende");
	}

	protected String[] getConfigLocations() {
		return new String[] {"classpath*:applicationContext-quartz.xml"};
	}

	public Scheduler getClusterScheduler() {
		return clusterScheduler;
	}

	public void setClusterScheduler(Scheduler clusterScheduler) {
		this.clusterScheduler = clusterScheduler;
	}

	public JobDetail getExampleJob() {
		return exampleJob;
	}

	public void setExampleJob(JobDetail exampleJob) {
		this.exampleJob = exampleJob;
	}
}