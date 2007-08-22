package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/**
 * Aspect for Institute Indexing 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class InstituteIndexingAspect {

	private static final Logger logger = Logger.getLogger(InstituteIndexingAspect.class);

	private IndexerService indexerService;
	
	private InstituteDao instituteDao;
	
	private Institute institute;
	
	/**
	 * Create index entry of an institute if it is enabled. UserId not necessary! Just for AOP, otherwise it is not working.
	 * @param instituteInfo, userId
	 */
	public void createInstituteIndex(InstituteInfo instituteInfo,Long userId) {
		logger.debug("Starting method createInstituteIndex");
		try {
			if (instituteInfo.isEnabled()) {
				logger.debug("method createInstituteIndex: createIndex");
				institute = instituteDao.instituteInfoToEntity(instituteInfo);
				indexerService.createIndex(institute);
			};
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Update institute index. If the institute is disabled it will be deleted from the search index.
	 * @param instituteInfo
	 */
	public void updateInstituteIndex(InstituteInfo instituteInfo) {
		logger.debug("Starting method updateInstituteIndex");
		try {
			if (instituteInfo.isEnabled()) {
				logger.debug("method updateInstituteIndex: updateIndex");
				institute = instituteDao.instituteInfoToEntity(instituteInfo);
				indexerService.updateIndex(institute);
				/*for(Course course:institute.getCourses()) {
					if (course.getAccessType() != AccessType.CLOSED) {
						indexerService.updateIndex(course);
					}
					
				}*/
			} else {
				logger.debug("method updateInstituteIndex: deleteIndex");
				institute = instituteDao.instituteInfoToEntity(instituteInfo);
				indexerService.deleteIndex(institute);
				/*for (Course course:institute.getCourses()) {
					indexerService.deleteIndex(course);
				}*/
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	/**
	 * Delete institute from lecture index.
	 * @param instituteId
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

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}
	
	
}
