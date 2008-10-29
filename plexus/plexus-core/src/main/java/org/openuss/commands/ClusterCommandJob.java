package org.openuss.commands;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 
 * Cluster Command QuartzJob to execute a specific cluster command.
 * 
 * @author Ingo Dueppe
 * 
 */
public class ClusterCommandJob extends QuartzJobBean implements ApplicationContextAware {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(ClusterCommandJob.class);

	/** Spring Application Context */
	private ApplicationContext applicationContext;

	/** Command Id to be executed */
	private Long commandId;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (commandId != null) {
			logger.debug("starting command "+commandId);
			ClusterCommand processor = (ClusterCommand) applicationContext.getBean("clusterCommand");
			processor.processCommand(commandId);
			logger.debug("finished command "+commandId);
		} else {
			logger.error("no given command id "+context.getTrigger().getName());
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setCommandId(Long commandId) {
		this.commandId = commandId;
	}

}
