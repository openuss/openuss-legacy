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
		try {
			if (institute.isEnabled()) {
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
		try {
			if (institute.isEnabled()) {
				indexerService.updateIndex(institute);
				for(Course course:institute.getCourses()) {
					if (course.getAccessType() != AccessType.CLOSED) {
						indexerService.updateIndex(course);
					}
					
				}
			} else {
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
		try {
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
