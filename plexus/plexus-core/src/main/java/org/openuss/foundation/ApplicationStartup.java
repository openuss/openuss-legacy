package org.openuss.foundation;

import org.apache.log4j.Logger;

import org.openuss.lecture.LectureIndex;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * This bean will initialize openuss plexus application after startup. 
 * 
 * @author Ingo Dueppe
 */
public class ApplicationStartup implements ApplicationListener {

	private static final Logger logger = Logger.getLogger(ApplicationStartup.class);
	
	private LectureIndex lectureIndex;

	public void onApplicationEvent(ApplicationEvent event) {
		logger.info("in onApplicationEvent method");
		if (event instanceof ContextRefreshedEvent) {
			try {
				performInitialization();
			} catch (Exception e) {
				logger.error("initialization failse "+e);
				throw new RuntimeException(e);
			}
		}
	}

	private void performInitialization() throws Exception {
		logger.info("===================> starting to initialize openuss plexus application! <=====================");
		lectureIndex.recreate();
		logger.info("===================> finished to initialize openuss plexus application!  <=====================");
	}

	public LectureIndex getLectureIndex() {
		return lectureIndex;
	}

	public void setLectureIndex(LectureIndex recreateLectureIndex) {
		this.lectureIndex = recreateLectureIndex;
	}


}
