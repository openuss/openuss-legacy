package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.lecture.Faculty;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/**
 * Aspect for Faculty Indexing 
 * @author Ingo Dueppe
 */
public class FacultyIndexingAspect {

	private static final Logger logger = Logger.getLogger(FacultyIndexingAspect.class);

	private IndexerService indexerService;
	
	public IndexerService getIndexerService() {
		return indexerService;
	}

	public void setIndexerService(IndexerService indexerService) {
		this.indexerService = indexerService;
	}

	/**
	 * Create index entry of faculty if it is enabled. 
	 * @param faculty
	 */
	public void createFacultyIndex(Faculty faculty) {
		try {
			if (faculty.isEnabled()) {
				indexerService.createIndex(faculty);
			};
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Update faculty index. If the faculty is disabled it will be deleted from the search index.
	 * @param faculty
	 */
	public void updateFacultyIndex(Faculty faculty) {
		try {
			if (faculty.isEnabled()) {
				indexerService.updateIndex(faculty);
			} else {
				indexerService.deleteIndex(faculty);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	/**
	 * Delete faculty from lecture index.
	 * @param faculty
	 */
	public void deleteFacultyIndex(Long facultyId) {
		try {
			Faculty faculty = Faculty.Factory.newInstance();
			faculty.setId(facultyId);
			indexerService.deleteIndex(faculty);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
}
