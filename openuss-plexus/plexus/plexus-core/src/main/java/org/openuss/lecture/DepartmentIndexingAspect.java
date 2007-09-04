package org.openuss.lecture;

import org.apache.log4j.Logger;
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
	
	// userId normally not necessary. Just using due to the configuration in the aop spring context.
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

	public void updateDepartmentIndex(DepartmentInfo departmentInfo) {
		logger.info("Starting method updateDepartmentIndex");
		try {
			if (departmentInfo.isEnabled()) {
				logger.debug("method updateDepartmentIndex: updateIndex");
				department = departmentDao.departmentInfoToEntity(departmentInfo);
				indexerService.updateIndex(department);
			} else {
				logger.debug("method updateDepartmentIndex: deleteIndex");
				deleteDepartmentFromIndexCascade(departmentInfo);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	
	/**
	 * Update department index By Id.
	 * @param departmentId, status
	 */
	public void updateDepartmentIndexById(Long departmentId, boolean status) {
		logger.debug("Starting method updateDepartmentIndexById");
		try {
			DepartmentInfo departmentInfo = departmentService.findDepartment(departmentId);
			if (departmentInfo.isEnabled()) {
				logger.debug("method updateDepartmentIndex: updateIndex");
				department = departmentDao.departmentInfoToEntity(departmentInfo);
				indexerService.updateIndex(department);
			} else {
				logger.debug("method updateDepartmentIndex: deleteIndex");
				deleteDepartmentFromIndexCascade(departmentInfo);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	

	public void deleteDepartmentIndex(Long departmentId) {
		logger.info("Starting method deleteDepartmentIndex");
		try {
			logger.info("Deleting DepartmentIndex");
			Department department = Department.Factory.newInstance();
			department.setId(departmentId);
			indexerService.deleteIndex(department);
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
	
	private void deleteDepartmentFromIndexCascade(DepartmentInfo departmentInfo){
		try {
			department = departmentDao.departmentInfoToEntity(departmentInfo);
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
}
