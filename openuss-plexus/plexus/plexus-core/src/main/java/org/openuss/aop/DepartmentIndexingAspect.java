package org.openuss.aop;

import org.apache.log4j.Logger;
import org.openuss.lecture.Course;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.DepartmentImpl;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.Institute;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/** 
 * Aspect for the indexing of departments.
 * 
 * @author Kai Stettner
 * @author Malte Stockmann
 */
public class DepartmentIndexingAspect {
	
	private static final Logger logger = Logger.getLogger(DepartmentIndexingAspect.class);

	private IndexerService indexerService;
	
	private DepartmentDao departmentDao;
	
	private DepartmentService departmentService;
	
	private Department department;
	 
	/**
	 * Creates index entry for a department
	 * 
	 * ATTENTION: userId normally not necessary. Just used due to the 
	 * configuration in the AOP spring context. 
	 * 
	 * @param departmentInfo
	 * @param userId
	 */
	public void createDepartmentIndex(DepartmentInfo departmentInfo, Long userId) {
		logger.info("Starting method createDepartmentIndex");
		try {
			if(departmentInfo.isEnabled()){
				logger.info("Creating DepartmentIndex");
				department = departmentDao.departmentInfoToEntity(departmentInfo);
				indexerService.createIndex(department);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Updates department index entry
	 * 
	 * @param departmentInfo
	 */
	public void updateDepartmentIndex(DepartmentInfo departmentInfo) {
		logger.info("Starting method updateDepartmentIndex");
		try {
			department = departmentDao.departmentInfoToEntity(departmentInfo);
			if (departmentInfo.isEnabled()) {
				logger.debug("method updateDepartmentIndex: updateIndex");
				indexerService.updateIndex(department);
			} else {
				logger.debug("method updateDepartmentIndex: deleteIndex");
				deleteDepartmentFromIndexCascade(department);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	
	/**
	 * Updates department index entry when the activation status is changed. When 
	 * the new state is "disabled", all subordinate institutes and courses are 
	 * removed from the index! 
	 * 
	 * @param departmentId
	 * @param status
	 */
	public void updateDepartmentIndexOnStatusChange(Long departmentId, boolean status) {
		logger.debug("Starting method updateDepartmentIndexOnStatusChange");
		try {
			department = departmentDao.load(departmentId);
			if (department.isEnabled()) {
				logger.debug("method updateDepartmentIndexOnStatusChange: updateIndex");
				indexerService.updateIndex(department);
			} else {
				logger.debug("method updateDepartmentIndexOnStatusChange: deleteIndex");
				deleteDepartmentFromIndexCascade(department);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Deletes department from index
	 * 
	 * @param departmentId
	 */
	public void deleteDepartmentIndex(Long departmentId) {
		logger.info("Starting method deleteDepartmentIndex");
		try {
			logger.info("Deleting DepartmentIndex");
			Department department = new DepartmentImpl();
			department.setId(departmentId);
			indexerService.deleteIndex(department);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Deletes department from index. Also removes all subordinate institutes 
	 * and courses from the index! 
	 * 
	 * @param departmentId
	 */
	public void deleteDepartmentIndexCascade(Long departmentId) {
		logger.debug("Starting method deleteDepartmentIndexCascade");
		try {
			department = departmentDao.load(departmentId);
			deleteDepartmentFromIndexCascade(department);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Helper method that encapsulates the removal of subordinate institutes 
	 * and courses from the index
	 * 
	 * @param department
	 */
	private void deleteDepartmentFromIndexCascade(Department department){
		try {
			indexerService.deleteIndex(department);
			Institute institute;
			Course course;
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

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
}
