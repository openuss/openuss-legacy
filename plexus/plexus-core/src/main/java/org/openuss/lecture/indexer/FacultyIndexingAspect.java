package org.openuss.lecture.indexer;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.openuss.lecture.Faculty;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/**
 * Aspect for Faculty Indexing 
 * @author Ingo Dueppe
 */
public class FacultyIndexingAspect {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FacultyIndexingAspect.class);

	private IndexerService indexerService;
	
	public IndexerService getIndexerService() {
		return indexerService;
	}

	public void setIndexerService(IndexerService indexerService) {
		this.indexerService = indexerService;
	}

	public void createNewFacultyIndex(Faculty faculty) {
//		Faculty faculty = (Faculty) pjp.getArgs()[0];
		logger.info("aspect: new faculty created - creating new index "+faculty.getId());
		try {
			indexerService.createIndex(faculty);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
}
