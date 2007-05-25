package org.openuss.lecture;

import org.apache.log4j.Logger;
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

	public void createFacultyIndex(Faculty faculty) {
		logger.info("aspect: new faculty created - creating new index "+faculty.getId());
		try {
			indexerService.createIndex(faculty);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	public void updateFacultyIndex(Faculty faculty) {
		logger.info("aspect: faculty updated - updating index "+faculty.getId());
		try {
			indexerService.updateIndex(faculty);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	public void deleteFacultyIndex(Faculty faculty) {
		logger.info("aspect: faculty deleted - deleting index "+faculty.getId());
		try {
			indexerService.deleteIndex(faculty);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
}
