package org.openuss.lecture.events;

import org.apache.log4j.Logger;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Course;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/** 
 * EventListener on course events for search indexing. 
 * 
 * @author Ingo Dueppe
 */
public class CourseIndexingEventListener implements ApplicationListener {

	private static final Logger logger = Logger.getLogger(CourseIndexingEventListener.class);

	private IndexerService indexerService;
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AbstractCourseEvent) {
			Course course = ((AbstractCourseEvent) event).getCourse();
			if (event instanceof CourseCreatedEvent) {
				createCourseIndex(course);
			} else if (event instanceof CourseUpdateEvent) {
				updateCourseIndex(course);
			} else if (event instanceof CourseRemoveEvent) {
				deleteCourseIndex(course);
			}
		}
	}
	
	/**
	 * Creates index entry for a course
	 * 
	 * @param course
	 */
	private void createCourseIndex(Course course) {
		logger.info("Starting method createCourseIndex");
		try {
			if (course.isEnabled() && course.getAccessType() != AccessType.CLOSED) {
				indexerService.createIndex(course);
			}
		} catch (IndexerApplicationException iae) {
			logger.error(iae.getMessage(), iae);
		}

	}

	/**
	 * Updates course index entry. If the course is disabled it will be deleted 
	 * from the search index.
	 * 
	 * @param instituteInfo
	 */
	private void updateCourseIndex(Course course) {
		logger.info("Starting method updateCourseIndex");
		try {
			if (course.isEnabled()) {
				indexerService.updateIndex(course);
			} else {
				logger.info("Deleting CourseIndex");
				indexerService.deleteIndex(course);
			}
		} catch (IndexerApplicationException iae) {
			logger.error(iae.getMessage(), iae);
		}
	}
	
	/**
	 * Deletes course from lecture index.
	 * @param courseId
	 */
	private void deleteCourseIndex(Course course) {
		logger.info("Starting method deleteCourseIndex");
		try {
			indexerService.deleteIndex(course);
		} catch (IndexerApplicationException iae) {
			logger.error(iae.getMessage(), iae);
		}
	}


	/* getter and setter */
	public IndexerService getIndexerService() {
		return indexerService;
	}

	public void setIndexerService(IndexerService indexerService) {
		this.indexerService = indexerService;
	}

}
