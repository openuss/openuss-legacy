package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/**
 * Test case for the spring aspect initiating the create, update and delete 
 * process of the university indexing.
 * 
 * @author Kai Stettner
 */
public class UniversityIndexingAspect {
	
	private static final Logger logger = Logger.getLogger(UniversityIndexingAspect.class);

	private IndexerService indexerService;
	
	private UniversityDao universityDao;
	
	private University university;
	
	/**
	 * Create index entry of an university.
	 * @param universityInfo
	 */
	public void createUniversityIndex(UniversityInfo universityInfo, Long userId) {
		logger.debug("Starting method createUniversityIndex");
		try {
			logger.debug("method createUniversityIndex: createIndex");
			university = universityDao.universityInfoToEntity(universityInfo);
			logger.debug("University Entity Id:");
			logger.debug(university.getId());
			indexerService.createIndex(university);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Update university index.
	 * @param universityInfo
	 */
	public void updateUniversityIndex(UniversityInfo universityInfo) {
		logger.debug("Starting method updateUniversityIndex");
		try {
			logger.debug("method updateUniversityIndex: updateIndex");
			university = universityDao.universityInfoToEntity(universityInfo);
			indexerService.updateIndex(university);
			/*for(Course course:institute.getCourses()) {
				if (course.getAccessType() != AccessType.CLOSED) {
					indexerService.updateIndex(course);
				}
				
			}*/
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	/**
	 * Delete university from lecture index.
	 * @param universityId
	 */
	public void deleteUniversityIndex(Long universityId) {
		logger.debug("Starting method deleteUniversityIndex");
		try {
			logger.debug("method deleteUniversityIndex: deleteIndex");
			University university = University.Factory.newInstance();
			university.setId(universityId);
			indexerService.deleteIndex(university);
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

	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}
	

}
