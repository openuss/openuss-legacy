package org.openuss.commands;

import org.apache.log4j.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ExampleJob extends QuartzJobBean implements ApplicationContextAware {
	private static final Logger logger = Logger.getLogger(ExampleJob.class);
	
	private int domainId;
	
	private String name;
	
	private ApplicationContext applicationContext;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("----------------------------------------------------------------------");
		logger.info("application context = "+applicationContext.toString());
		try {
			logger.info("scheduler "+context.getScheduler().getSchedulerInstanceId()+" name="+name+" domain id = "+domainId);
		} catch (SchedulerException e) {
			logger.error(e);
		}
		throw new JobExecutionException("break");
	}

	public int getDomainId() {
		return domainId;
	}

	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
