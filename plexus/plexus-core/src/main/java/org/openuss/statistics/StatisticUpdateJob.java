package org.openuss.statistics;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * QuartzJob to update the system statistic 
 * 
 * @author Ingo Dueppe
 *
 */
public class StatisticUpdateJob extends QuartzJobBean implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		SystemStatisticDao systemStatisticDao = (SystemStatisticDao) applicationContext.getBean("systemStatisticDao", SystemStatisticDao.class);
		systemStatisticDao.current();
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
