/**
 * 
 */
package org.openuss.framework.tests;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.UnableToInterruptJobException;
import org.quartz.spi.JobFactory;

/**
 * @author Ingo Dueppe
 * 
 */
public class SchedulerMock implements Scheduler {

	/**
	 */
	public SchedulerMock() {}

	/**
	 * {@inheritDoc}
	 */
	public void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers)	throws SchedulerException {}

	/**
	 * {@inheritDoc}
	 */
	public void addGlobalJobListener(JobListener jobListener) throws SchedulerException {}

	/**
	 * {@inheritDoc}
	 */
	public void addGlobalTriggerListener(TriggerListener triggerListener) throws SchedulerException {}

	/**
	 * {@inheritDoc}
	 */
	public void addJob(JobDetail jobDetail, boolean replace) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void addJobListener(JobListener jobListener) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void addSchedulerListener(SchedulerListener schedulerListener) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void addTriggerListener(TriggerListener triggerListener) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteCalendar(String calName) throws SchedulerException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteJob(String jobName, String groupName) throws SchedulerException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Calendar getCalendar(String calName) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getCalendarNames() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public SchedulerContext getContext() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List getCurrentlyExecutingJobs() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List getGlobalJobListeners() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List getGlobalTriggerListeners() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public JobDetail getJobDetail(String jobName, String jobGroup) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getJobGroupNames() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public JobListener getJobListener(String name) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Set getJobListenerNames() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getJobNames(String groupName) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public SchedulerMetaData getMetaData() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Set getPausedTriggerGroups() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSchedulerInstanceId() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List getSchedulerListeners() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSchedulerName() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Trigger getTrigger(String triggerName, String triggerGroup) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getTriggerGroupNames() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public TriggerListener getTriggerListener(String name) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Set getTriggerListenerNames() throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getTriggerNames(String groupName) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getTriggerState(String triggerName, String triggerGroup) throws SchedulerException {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public Trigger[] getTriggersOfJob(String jobName, String groupName) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean interrupt(String jobName, String groupName) throws UnableToInterruptJobException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isInStandbyMode() throws SchedulerException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isPaused() throws SchedulerException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isShutdown() throws SchedulerException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void pause() throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void pauseAll() throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void pauseJob(String jobName, String groupName) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void pauseJobGroup(String groupName) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void pauseTrigger(String triggerName, String groupName) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void pauseTriggerGroup(String groupName) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean removeGlobalJobListener(JobListener jobListener) throws SchedulerException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean removeGlobalTriggerListener(TriggerListener triggerListener) throws SchedulerException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean removeJobListener(String name) throws SchedulerException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean removeSchedulerListener(SchedulerListener schedulerListener) throws SchedulerException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean removeTriggerListener(String name) throws SchedulerException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date rescheduleJob(String triggerName, String groupName, Trigger newTrigger) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void resumeAll() throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void resumeJob(String jobName, String groupName) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void resumeJobGroup(String groupName) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void resumeTrigger(String triggerName, String groupName) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void resumeTriggerGroup(String groupName) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public Date scheduleJob(Trigger trigger) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setJobFactory(JobFactory factory) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void standby() throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void start() throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void triggerJob(String jobName, String groupName) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void triggerJob(String jobName, String groupName, JobDataMap data) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void triggerJobWithVolatileTrigger(String jobName, String groupName) throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void triggerJobWithVolatileTrigger(String jobName, String groupName, JobDataMap data)
			throws SchedulerException {
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean unscheduleJob(String triggerName, String groupName) throws SchedulerException {
		return false;
	}

}
