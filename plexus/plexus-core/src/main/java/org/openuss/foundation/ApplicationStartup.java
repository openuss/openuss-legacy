package org.openuss.foundation;

import org.apache.log4j.Logger;

import org.openuss.lecture.LectureIndexImpl;
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
	
	private LectureIndexImpl lectureIndex;

	public void onApplicationEvent(ApplicationEvent event) {
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

	public LectureIndexImpl getLectureIndex() {
		return lectureIndex;
	}

	public void setLectureIndex(LectureIndexImpl recreateLectureIndex) {
		this.lectureIndex = recreateLectureIndex;
	}


}
