package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/**
 * Aspect for Institute Indexing 
 * @author Ingo Dueppe
 */
public class InstituteIndexingAspect {

	private static final Logger logger = Logger.getLogger(InstituteIndexingAspect.class);

	private IndexerService indexerService;
	
	private LectureService lectureService;
	
	/**
	 * Create index entry of institute if it is enabled. 
	 * @param institute
	 */
	public void createInstituteIndex(Institute institute) {
		logger.debug("Starting method createInstituteIndex");
		try {
			if (institute.isEnabled()) {
				logger.debug("method createInstituteIndex: createIndex");
				indexerService.createIndex(institute);
			};
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Update institute index. If the institute is disabled it will be deleted from the search index.
	 * @param institute
	 */
	public void updateInstituteIndex(Institute institute) {
		logger.debug("Starting method updateInstituteIndex");
		try {
			if (institute.isEnabled()) {
				logger.debug("method updateInstituteIndex: updateIndex");
				indexerService.updateIndex(institute);
				for(Course course:institute.getCourses()) {
					if (course.getAccessType() != AccessType.CLOSED) {
						indexerService.updateIndex(course);
					}
					
				}
			} else {
				logger.debug("method updateInstituteIndex: deleteIndex");
				indexerService.deleteIndex(institute);
				for (Course course:institute.getCourses()) {
					indexerService.deleteIndex(course);
				}
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	/**
	 * Delete institute from lecture index.
	 * @param institute
	 */
	public void deleteInstituteIndex(Long instituteId) {
		logger.debug("Starting method deleteInstituteIndex");
		try {
			logger.debug("method deleteInstituteIndex: deleteIndex");
			Institute institute = Institute.Factory.newInstance();
			institute.setId(instituteId);
			indexerService.deleteIndex(institute);
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

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}
}
