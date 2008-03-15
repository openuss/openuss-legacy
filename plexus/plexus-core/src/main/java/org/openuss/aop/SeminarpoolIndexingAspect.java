package org.openuss.aop;

import org.apache.log4j.Logger;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;
import org.openuss.seminarpool.Seminarpool;
import org.openuss.seminarpool.SeminarpoolDao;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.seminarpool.SeminarpoolStatus;


/**
 * Aspect for indexing the Seminarpools
 * @author PS-OpenUSS Integrationsteam Bachelor
 *
 */
public class SeminarpoolIndexingAspect {
    
	private static final Logger logger = Logger.getLogger(SeminarpoolIndexingAspect.class);

	private IndexerService indexerService;
	
	private SeminarpoolDao seminarpoolDao;
	
	private Seminarpool seminarpool;

	/**
	 * Creates index entry for a seminarpool
	 * 
	 * @param seminarpoolInfo
	 */
	public void createSeminarpoolIndex(SeminarpoolInfo seminarpoolInfo, Long userId) {
		logger.info("Starting method createSeminarpoolIndex");
		try {
		    seminarpool = seminarpoolDao.seminarpoolInfoToEntity(seminarpoolInfo);
		    indexerService.createIndex(seminarpool);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}

	}

	/**
	 * Updates seminarpoolIndex index entry. If the allocation in the seminarpool is finalized it will be deleted 
	 * from the search index.
	 * 
	 * @param instituteInfo
	 */
	public void updateSeminarpoolIndex(SeminarpoolInfo seminarpoolInfo) {
		logger.info("Starting method updateSeminarpoolIndex");
		try {
			if (!seminarpoolInfo.getSeminarpoolStatus().equals(SeminarpoolStatus.CONFIRMEDPHASE)) {
				seminarpool = seminarpoolDao.seminarpoolInfoToEntity(seminarpoolInfo);
				indexerService.updateIndex(seminarpool);
			} else {
				logger.info("Deleting CourseIndex");
				seminarpool = seminarpoolDao.seminarpoolInfoToEntity(seminarpoolInfo);
				indexerService.deleteIndex(seminarpool);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	
	/**
	 * Deletes course from lecture index.
	 * @param courseId
	 */
	public void deleteSeminarpoolIndex(Long semianrpoolId) {
		logger.info("Starting method deleteCourseIndex");
		try {
			logger.info("Deleting CourseIndex");
			seminarpool = seminarpoolDao.load(semianrpoolId);
			indexerService.deleteIndex(seminarpool);
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

	public SeminarpoolDao getSeminarpoolDao() {
	    return seminarpoolDao;
	}

	public void setSeminarpoolDao(SeminarpoolDao seminarpoolDao) {
	    this.seminarpoolDao = seminarpoolDao;
	}

}
