package org.openuss.lecture;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/**
 * 
 * @author Ingo Dueppe
 */
public class CourseIndexingAspect {

	private static final Logger logger = Logger.getLogger(CourseIndexingAspect.class);

	private IndexerService indexerService;

	private LectureService lectureService;

	public void updateCourseIndexOnPeriodUpdate(Period period) {
		Validate.notNull(period, "Parameter period must not be null");
		for (Course course : period.getCourses()) {
			updateCourseIndex(course);
		}
	}

	public void createCourseIndex(Course course) {
		try {
			if (course.getAccessType() != AccessType.CLOSED) {
				indexerService.createIndex(course);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}

	}

	public void updateCourseIndex(Course course) {
		logger.info("Starting method updateCourseIndex");
		try {
			if (course.getAccessType() == AccessType.CLOSED) {
				logger.info("Deleting CourseIndex");
				indexerService.deleteIndex(course);
			} else {
				indexerService.updateIndex(course);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	public void deleteCourseIndex(Long courseId) {
		try {
			Course course = Course.Factory.newInstance();
			course.setId(courseId);
			indexerService.deleteIndex(course);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public IndexerService getIndexerService() {
		return indexerService;
	}

	public void setIndexerService(IndexerService indexerService) {
		this.indexerService = indexerService;
	}

}
