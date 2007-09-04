package org.openuss.lecture;

import java.util.List;

import org.apache.log4j.Logger;
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
	
	private CourseService courseService;
	
	private InstituteDao instituteDao;
	
	private CourseDao courseDao;
	
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
			}
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
				/*
				Course course;
				for(Object courseTemp : institute.getAllCourses()) {
					course = (Course) courseTemp;
					if (course.getAccessType() != AccessType.CLOSED) {
						indexerService.updateIndex(course);
					}
				}
				*/
			} else {
				logger.debug("method updateInstituteIndex: deleteIndex");
				deleteInstituteFromIndexCascade(instituteInfo);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Update institute index by ID.
	 * @param institiuteId, status
	 */
	public void updateInstituteIndexById(Long institiuteId, Boolean status) {
		logger.debug("Starting method updateInstituteIndexById");
		try {
			InstituteInfo instituteInfo = instituteService.findInstitute(institiuteId);
			if (instituteInfo.isEnabled()) {
				logger.debug("method updateInstituteIndexById: updateIndex");
				institute = instituteDao.instituteInfoToEntity(instituteInfo);
				indexerService.updateIndex(institute);
			} else {
				logger.debug("method updateInstituteIndexById: deleteIndex");
				deleteInstituteFromIndexCascade(instituteInfo);
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
	
	private void deleteInstituteFromIndexCascade(InstituteInfo instituteInfo){
		try{
			institute = instituteDao.instituteInfoToEntity(instituteInfo);
			indexerService.deleteIndex(institute);
			List allCourses = courseService.findAllCoursesByInstitute(instituteInfo.getId());
			CourseInfo courseInfo;
			for (Object courseTemp : allCourses) {
				courseInfo = (CourseInfo) courseTemp;
				indexerService.deleteIndex(courseDao.courseInfoToEntity(courseInfo));
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}
	
}
