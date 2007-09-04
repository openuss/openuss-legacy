package org.openuss.lecture;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/** Aspect for the indexing of courses.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class CourseIndexingAspect {

	private static final Logger logger = Logger.getLogger(CourseIndexingAspect.class);

	private IndexerService indexerService;
	
	private CourseDao courseDao;
	
	private Course course;

	public void updateCourseIndexOnPeriodUpdate(Period period) {
		logger.info("Starting method updateCourseIndexOnPeriodUpdate(");
		Validate.notNull(period, "Parameter period must not be null");
		for (Course course : period.getCourses()) {
			CourseInfo courseInfo = courseDao.toCourseInfo(course);
			updateCourseIndex(courseInfo);
		}
	}

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

	public void updateCourseIndex(CourseInfo courseInfo) {
		logger.info("Starting method updateCourseIndex");
		try {
			if (courseInfo.isEnabled() && courseInfo.getAccessType() != AccessType.CLOSED) {
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

	public void deleteCourseIndex(Long courseId) {
		logger.info("Starting method deleteCourseIndex");
		try {
			Course course = Course.Factory.newInstance();
			course.setId(courseId);
			indexerService.deleteIndex(course);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

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
