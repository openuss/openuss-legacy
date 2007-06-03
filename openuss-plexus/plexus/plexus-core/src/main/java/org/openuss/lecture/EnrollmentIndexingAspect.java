package org.openuss.lecture;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/**
 * 
 * @author Ingo Dueppe
 */
public class EnrollmentIndexingAspect {

	private static final Logger logger = Logger.getLogger(EnrollmentIndexingAspect.class);

	private IndexerService indexerService;

	private LectureService lectureService;

	public void updateEnrollmentIndexOnPeriodUpdate(Period period) {
		Validate.notNull(period, "Parameter period must not be null");
		for (Enrollment enrollment : period.getEnrollments()) {
			updateEnrollmentIndex(enrollment);
		}
	}

	public void createEnrollmentIndex(Enrollment enrollment) {
		try {
			if (enrollment.getAccessType() != AccessType.CLOSED) {
				indexerService.createIndex(enrollment);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}

	}

	public void updateEnrollmentIndex(Enrollment enrollment) {
		try {
			if (enrollment.getAccessType() == AccessType.CLOSED) {
				indexerService.deleteIndex(enrollment);
			} else {
				indexerService.updateIndex(enrollment);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	public void deleteEnrollmentIndex(Long enrollmentId) {
		try {
			Enrollment enrollment = Enrollment.Factory.newInstance();
			enrollment.setId(enrollmentId);
			indexerService.deleteIndex(enrollment);
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
