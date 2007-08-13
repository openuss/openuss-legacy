package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/** Aspect for the indexing of departments.
 * 
 * @author Kai Stettner
 */
public class DepartmentIndexingAspect {
	
	private static final Logger logger = Logger.getLogger(DepartmentIndexingAspect.class);

	private IndexerService indexerService;
	
	private DepartmentDao departmentDao;
	
	private Department department;
	
	// userId normally not necessary. Just using due to the configuration in the aop spring context.
	public void createDepartmentIndex(DepartmentInfo departmentInfo, Long userId) {
		logger.info("Starting method createDepartmentIndex");
		try {
			logger.info("Creating DepartmentIndex");
			department = departmentDao.departmentInfoToEntity(departmentInfo);
			indexerService.createIndex(department);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	public void updateDepartmentIndex(DepartmentInfo departmentInfo) {
		logger.info("Starting method updateDepartmentIndex");
		try {
			logger.info("Updating DepartmentIndex");
			department = departmentDao.departmentInfoToEntity(departmentInfo);
			indexerService.updateIndex(department);
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
	
}
