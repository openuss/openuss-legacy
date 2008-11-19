package org.openuss.aop;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseImpl;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.Period;
import org.openuss.lecture.events.CourseIndexingEventListener;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/** Aspect for the indexing of courses.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 * @author Malte Stockmann
 * 
 * @deprecated 
 * @see CourseIndexingEventListener
 */
public class CourseIndexingAspect {

	private static final Logger logger = Logger.getLogger(CourseIndexingAspect.class);

	private IndexerService indexerService;
	
	private CourseDao courseDao;
	
	private Course course;

	/**
	 * Creates index entry for a course
	 * 
	 * @param courseInfo
	 */
	public void createCourseIndex(CourseInfo courseInfo) {
		logger.info("Starting method createCourseIndex");
		try {
			if (courseInfo.isEnabled() && courseInfo.getAccessType() != AccessType.CLOSED) {
				course = courseDao.courseInfoToEntity(courseInfo);
				indexerService.createIndex(course);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}

	}

	/**
	 * Updates course index entry. If the course is disabled it will be deleted 
	 * from the search index.
	 * 
	 * @param instituteInfo
	 */
	public void updateCourseIndex(CourseInfo courseInfo) {
		logger.info("Starting method updateCourseIndex");
		try {
			if (courseInfo.isEnabled()) {
				course = courseDao.courseInfoToEntity(courseInfo);
				indexerService.updateIndex(course);
			} else {
				logger.info("Deleting CourseIndex");
				course = courseDao.courseInfoToEntity(courseInfo);
				indexerService.deleteIndex(course);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Updates course index entry when a period was changed. All index entries 
	 * of courses in this period are updated. 
	 * 
	 * @param period
	 */
	public void updateCourseIndexOnPeriodUpdate(Period period) {
		logger.info("Starting method updateCourseIndexOnPeriodUpdate(");
		Validate.notNull(period, "Parameter period must not be null");
		for (Course course : period.getCourses()) {
			CourseInfo courseInfo = courseDao.toCourseInfo(course);
			updateCourseIndex(courseInfo);
		}
	}
	
	/**
	 * Updates course index entry when the activation status is changed. 
	 * 
	 * @param courseId
	 * @param status
	 */
	public void updateCourseIndexOnStatusChange(Long courseId, Boolean status) {
		logger.debug("Starting method updateInstituteIndexOnStatusChange");
		try {
			course = courseDao.load(courseId);
			if (course.isEnabled()&& course.getAccessType() != AccessType.CLOSED) {
				logger.debug("method updateInstituteIndexOnStatusChange: updateIndex");
				indexerService.updateIndex(course);
			} else {
				logger.debug("method updateInstituteIndexOnStatusChange: deleteIndex");
				indexerService.deleteIndex(course);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	/**
	 * Deletes course from lecture index.
	 * @param courseId
	 */
	public void deleteCourseIndex(Long courseId) {
		logger.info("Starting method deleteCourseIndex");
		try {
			Course course = new CourseImpl();
			course.setId(courseId);
			indexerService.deleteIndex(course);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	/* getter and setter */
	public IndexerService getIndexerService() {
		return indexerService;
	}

	public void setIndexerService(IndexerService indexerService) {
		this.indexerService = indexerService;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}
}
