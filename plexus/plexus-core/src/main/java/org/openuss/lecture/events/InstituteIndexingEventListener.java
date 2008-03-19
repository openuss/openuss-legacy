package org.openuss.lecture.events;

import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.lecture.Course;
import org.openuss.lecture.Institute;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * EventListener for Institute Indexing 
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 * @author Malte Stockmann
 */
public class InstituteIndexingEventListener implements ApplicationListener {

	private static final Logger logger = Logger.getLogger(InstituteIndexingEventListener.class);

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AbstractInstituteEvent) {
			Institute institute = ((AbstractInstituteEvent)event).getInstitute();
			if (event instanceof InstituteCreatedEvent) {
				createInstituteIndex(institute);
			} else if (event instanceof InstituteUpdatedEvent) {
				updateInstituteIndex(institute);
			} else if (event instanceof InstituteRemoveEvent) {
				deleteInstituteIndex(institute);
			}
		}
	}
	
	private IndexerService indexerService;
	
	/**
	 * Creates index entry for an institute if it is enabled. 
	 * 
	 * @param instituteInfo, userId
	 */
	private void createInstituteIndex(Institute institute) {
		logger.debug("Starting method createInstituteIndex");
		try {
			if (institute.isEnabled()) {
				logger.debug("method createInstituteIndex: createIndex");
				indexerService.createIndex(institute);
			} 
		} catch (IndexerApplicationException iae) {
			logger.error(iae.getMessage(), iae);
		}
	}
	
	/**
	 * Updates institute index. If the institute is disabled it will be deleted 
	 * from the search index.
	 * 
	 * @param institute
	 */
	private void updateInstituteIndex(Institute institute) {
		logger.debug("Starting method updateInstituteIndex");
		try {
			if (institute.isEnabled()) {
				logger.debug("method updateInstituteIndex: updateIndex");
				indexerService.updateIndex(institute);
				updateCoursesOfInstituteOnIndex(institute);
			} else {
				logger.debug("method updateInstituteIndex: deleteIndex");
				deleteInstituteIndex(institute);
			}
		} catch (IndexerApplicationException iae) {
			logger.error(iae.getMessage(), iae);
		}
	}
	
	/**
	 * Deletes institute from index. Also removes all subordinate courses 
	 * from the index! 
	 * 
	 * @param instituteId
	 */
	private void deleteInstituteIndex(Institute institute) {
		logger.debug("Starting method deleteInstituteIndexCascade");
		try {
			deleteCoursesOfInstituteFromIndex(institute);
			indexerService.deleteIndex(institute);
		} catch (IndexerApplicationException iae) {
			logger.error(iae.getMessage(), iae);
		}
	}
	
	/**
	 * Helper method that encapsulates the removal of subordinate courses 
	 * from the index
	 * 
	 * @param institute
	 */
	private void deleteCoursesOfInstituteFromIndex(Institute institute){
		try {
			for (Course course : (List<Course>) institute.getAllCourses()) {
				indexerService.deleteIndex(course);
			}
		} catch (IndexerApplicationException iae) {
			logger.error(iae.getMessage(), iae);
		}
	}
	
	/**
	 * Helper method that encapsulates the update of subordinate courses' index entries 
	 * 
	 * @param institute
	 */
	private void updateCoursesOfInstituteOnIndex(Institute institute){
		try {
			for (Course course : (List<Course>) institute.getAllCourses()) {
				indexerService.updateIndex(course);
			}
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
