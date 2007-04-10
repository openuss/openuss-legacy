package org.openuss.mail;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.openuss.lecture.Faculty;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DummyQuartzBean extends QuartzJobBean implements Serializable{

	private static final Logger logger = Logger.getLogger(DummyQuartzBean.class);

	private static final long serialVersionUID = 6918186843745375301L;

	public void send() {
		logger.debug("starting dummy send method by quartz.");
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug("executeInternal with fire time"+context.getFireTime());
		try {
			Faculty faculty = (Faculty) context.getJobDetail().getJobDataMap().get("FACULTY");
			logger.debug("job name = "+context.getJobDetail().getName());
			if (faculty == null) {
				logger.debug("no faculty");
			} else {
				logger.debug("found faculty "+faculty+" with id "+faculty.getId());
			}
			
			SchedulerContext ctx = context.getScheduler().getContext();
			for(String key : ctx.getKeys()) {
				logger.debug(" key = "+key+" value = "+ctx.get(key));
				
			}
		} catch (SchedulerException e) {
			logger.error(e);
		}
		
	}
	
}
