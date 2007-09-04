package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/**
 * Aspect for the indexing of universities.
 * 
 * @author Kai Stettner
 * @author Malte Stockmann
 */
public class UniversityIndexingAspect {
	
	private static final Logger logger = Logger.getLogger(UniversityIndexingAspect.class);

	private IndexerService indexerService;
	
	private UniversityDao universityDao;
	
	private UniversityService universityService;
	
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
			if (universityInfo.isEnabled()) {
				logger.debug("method updateUniversityIndex: updateIndex");
				university = universityDao.universityInfoToEntity(universityInfo);
				indexerService.updateIndex(university);
			} else {
				logger.debug("method updateUniversityIndex: deleteIndex");
				deleteUniversityFromIndexCascade(universityInfo);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Update university index By Id.
	 * @param universityId, status
	 */
	public void updateUniversityIndexById(Long universityId, boolean status) {
		logger.debug("Starting method updateUniversityIndexById");
		try {
			UniversityInfo universityInfo = universityService.findUniversity(universityId);
			if (universityInfo.isEnabled()) {
				logger.debug("method updateUniversityIndex: updateIndex");
				university = universityDao.universityInfoToEntity(universityInfo);
				indexerService.updateIndex(university);
			} else {
				logger.debug("method updateUniversityIndex: deleteIndex");
				deleteUniversityFromIndexCascade(universityInfo);
			}
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

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}
	
	private void deleteUniversityFromIndexCascade(UniversityInfo universityInfo){
		try{
			university = universityDao.universityInfoToEntity(universityInfo);
			indexerService.deleteIndex(university);
			Department department;
			Institute institute;
			Course course;
			// delete university's departments from index
			for(Object departmentTemp : university.getDepartments()){
				department = (Department) departmentTemp;
				indexerService.deleteIndex(department);
				// delete department's institutes from index
				for(Object instituteTemp : department.getInstitutes()) {
					// delete current institute from index
					institute = (Institute) instituteTemp;
					indexerService.deleteIndex(institute);
					// delete institute's courses from index
					for(Object courseTemp : institute.getAllCourses()){
						course = (Course) courseTemp; 
						indexerService.deleteIndex(course);
					}
				}
			}
			
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	

}
