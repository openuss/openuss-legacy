package org.openuss.aop;

import org.apache.log4j.Logger;
import org.openuss.lecture.Application;
import org.openuss.lecture.ApplicationDao;
import org.openuss.lecture.Course;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/**
 * Aspect for Institute Indexing 
 * @author Ingo Dueppe
 * @author Kai Stettner
 * @author Malte Stockmann
 */
public class InstituteIndexingAspect {

	private static final Logger logger = Logger.getLogger(InstituteIndexingAspect.class);

	private IndexerService indexerService;
	
	private InstituteService instituteService;
	
	private InstituteDao instituteDao;
	
	private Institute institute;
	
	private ApplicationDao applicationDao;
	

	/**
	 * Creates index entry for an institute if it is enabled. 
	 * 
	 * ATTENTION: UserId not necessary! Just for AOP, otherwise it is not working.
	 * 
	 * @param instituteInfo, userId
	 */
	public void createInstituteIndex(InstituteInfo instituteInfo, Long userId) {
		logger.debug("Starting method createInstituteIndex");
		try {
			if (instituteInfo.isEnabled()) {
				logger.debug("method createInstituteIndex: createIndex");
				institute = instituteDao.instituteInfoToEntity(instituteInfo);
				indexerService.createIndex(institute);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Updates institute index. If the institute is disabled it will be deleted 
	 * from the search index.
	 * 
	 * @param instituteInfo
	 */
	public void updateInstituteIndex(InstituteInfo instituteInfo) {
		logger.debug("Starting method updateInstituteIndex");
		try {
			institute = instituteDao.instituteInfoToEntity(instituteInfo);
			if (instituteInfo.isEnabled()) {
				logger.debug("method updateInstituteIndex: updateIndex");
				indexerService.updateIndex(institute);
//				Course course;
//				for(Object courseTemp : institute.getAllCourses()) {
//					if (courseTemp instanceof CourseImpl){
//						course = (Course) courseTemp;
//						if (course.getAccessType() != AccessType.CLOSED) {
//							indexerService.updateIndex(course);
//						}
//					}
//				}
			} else {
				logger.debug("method updateInstituteIndex: deleteIndex");
				deleteInstituteFromIndexCascade(institute);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Updates institute index entry when the activation status is changed. When 
	 * the new state is "disabled", all subordinate courses are removed from the index! 
	 * 
	 * @param institiuteId
	 * @param status
	 */
	public void updateInstituteIndexOnStatusChange(Long institiuteId, Boolean status) {
		logger.debug("Starting method updateInstituteIndexOnStatusChange");
		try {
			institute = instituteDao.load(institiuteId);
			if (institute.isEnabled()) {
				logger.debug("method updateInstituteIndexOnStatusChange: updateIndex");
				indexerService.updateIndex(institute);
			} else {
				logger.debug("method updateInstituteIndexOnStatusChange: deleteIndex");
				deleteInstituteFromIndexCascade(institute);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Updates institute index entries an institute's department is changed. 
	 * Index entries for subordinate courses are updated as well. 
	 * 
	 * @param applicationId
	 */
	public void updateInstituteIndexOnDepartmentApplication(Object applicationIdObject, Long instituteId, Long departmentId, Long userId) {
		logger.debug("Starting method updateInstituteIndexOnDepartmentApplication");
		if ((applicationIdObject != null) && (applicationIdObject instanceof Long)) {
			Long applicationId = (Long) applicationIdObject;
			try {
				Application application = applicationDao.load(applicationId);
				if (application.isConfirmed()) {
					logger.debug("method updateInstituteIndexOnDepartmentApplication: updateIndex");
					updateInstituteIndexEntriesCascade(application.getInstitute());
				} 
			} catch (Exception e) {
				logger.error(e);
			}
		} else {
			logger.debug("invalid applicationId - aborted!");
		}
	}
	
	/**
	 * Updates institute index entries an institute's department is changed. 
	 * Index entries for subordinate courses are updated as well. 
	 * 
	 * @param applicationId ID of the application which was accepted
	 * @param userId ID of the user who accepted the application (not necessary 
	 * for application logic; just present because due to AOP definition)
	 */
	public void updateInstituteIndexOnDepartmentApplicationApproval(Long applicationId, Long userId) {
		logger.debug("Starting method updateInstituteIndexOnDepartmentApplicationApproval");

		try {
			Application application = applicationDao.load(applicationId);
			if (application.isConfirmed()) {
				logger.debug("method updateInstituteIndexOnDepartmentApplicationApproval: updateIndex");
				updateInstituteIndexEntriesCascade(application.getInstitute());
			} 
		} catch (Exception e) {
			logger.error(e);
		}
	}
	

	/**
	 * Deletes institute from lecture index.
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
	
	/**
	 * Deletes institute from index. Also removes all subordinate courses 
	 * from the index! 
	 * 
	 * @param instituteId
	 */
	public void deleteInstituteIndexCascade(Long instituteId) {
		logger.debug("Starting method deleteInstituteIndexCascade");
		try {
			institute = instituteDao.load(instituteId);
			deleteInstituteFromIndexCascade(institute);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Helper method that encapsulates the removal of subordinate courses 
	 * from the index
	 * 
	 * @param institute
	 */
	private void deleteInstituteFromIndexCascade(Institute institute){
		try {
			indexerService.deleteIndex(institute);
			Course course;
			for (Object courseTemp : institute.getAllCourses()) {
				course = (Course) courseTemp;
				indexerService.deleteIndex(course);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Helper method that encapsulates the update of subordinate courses' index entries 
	 * 
	 * @param institute
	 */
	private void updateInstituteIndexEntriesCascade(Institute institute){
		try {
			indexerService.updateIndex(institute);
			Course course;
			for (Object courseTemp : institute.getAllCourses()) {
				course = (Course) courseTemp;
				indexerService.updateIndex(course);
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

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}
	
	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}
}
