package org.openuss.aop;

import org.apache.log4j.Logger;
import org.openuss.lecture.Course;
import org.openuss.lecture.Department;
import org.openuss.lecture.Institute;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.lecture.UniversityImpl;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
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
	 * Creates index entry for an university
	 * 
	 * @param universityInfo
	 */
	public void createUniversityIndex(UniversityInfo universityInfo, Long userId) {
		logger.debug("Starting method createUniversityIndex");
		try {
			if(universityInfo.isEnabled()){
				logger.debug("method createUniversityIndex: createIndex");
				university = universityDao.universityInfoToEntity(universityInfo);
				indexerService.createIndex(university);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Updates university index entry
	 * 
	 * @param universityInfo
	 */
	public void updateUniversityIndex(UniversityInfo universityInfo) {
		logger.debug("Starting method updateUniversityIndex");
		try {
			university = universityDao.universityInfoToEntity(universityInfo);
			if (universityInfo.isEnabled()) {
				logger.debug("method updateUniversityIndex: updateIndex");
				indexerService.updateIndex(university);
			} else {
				logger.debug("method updateUniversityIndex: deleteIndex");
				deleteUniversityFromIndexCascade(university);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Updates university index entry when the activation status is changed. When 
	 * the new state is "disabled", all subordinate departments, institutes and 
	 * courses are removed from the index! 
	 * 
	 * @param universityId
	 * @param status
	 */
	public void updateUniversityIndexOnStatusChange(Long universityId, boolean status) {
		logger.debug("Starting method updateUniversityIndexOnStatusChange");
		try {
			university = universityDao.load(universityId);
			if (university.isEnabled()) {
				logger.debug("method updateUniversityIndexOnStatusChange: updateIndex");
				indexerService.updateIndex(university);
			} else {
				logger.debug("method updateUniversityIndexOnStatusChange: deleteIndex");
				deleteUniversityFromIndexCascade(university);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	/**
	 * Deletes university from index
	 * 
	 * @param universityId
	 */
	public void deleteUniversityIndex(Long universityId) {
		logger.debug("Starting method deleteUniversityIndex");
		try {
			logger.debug("method deleteUniversityIndex: deleteIndex");
			University university = new UniversityImpl();
			university.setId(universityId);
			indexerService.deleteIndex(university);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Deletes university from index. Also removes all subordinate departments, 
	 * institutes and courses from the index! 
	 * 
	 * @param universityId
	 */
	public void deleteUniversityIndexCascade(Long universityId) {
		logger.debug("Starting method deleteUniversityIndexCascade");
		try {
			university = universityDao.load(universityId);
			deleteUniversityFromIndexCascade(university);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Helper method that encapsulates the removal of subordinate departments, 
	 * institutes and courses from the index
	 * 
	 * @param university
	 */
	private void deleteUniversityFromIndexCascade(University university){
		try {
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
	
	/* getter and setter */
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
	
}
