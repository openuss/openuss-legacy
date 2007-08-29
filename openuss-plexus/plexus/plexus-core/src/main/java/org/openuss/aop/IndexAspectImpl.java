package org.openuss.aop;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.CourseTypeDao;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.Period;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.lecture.UniversityIndexingAspect;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

public class IndexAspectImpl {

	private static final Logger logger = Logger.getLogger(IndexAspectImpl.class);

	private IndexerService indexerService;
	
	private UniversityDao universityDao;
	
	private UniversityService universityService;
	
	private University university;
	
	private DepartmentDao departmentDao;
	
	private DepartmentService departmentService;
	
	private Department department;
	
	private InstituteDao instituteDao;
	
	private Institute institute;
	
	private CourseTypeDao courseTypeDao;
	
	private CourseType courseType;
	
	private CourseDao courseDao;
	
	private Course course;
	
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
				university = universityDao.universityInfoToEntity(universityInfo);
				indexerService.deleteIndex(university);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Update university index By Id.
	 * @param universityId, status
	 */
	public void updateUniversityIndexById(Long universityId, Boolean status) {
		logger.debug("Starting method updateUniversityIndexById");
		try {
			UniversityInfo universityInfo = universityService.findUniversity(universityId);
			if (universityInfo.isEnabled()) {
				logger.debug("method updateUniversityIndex: updateIndex");
				university = universityDao.universityInfoToEntity(universityInfo);
				indexerService.updateIndex(university);
			} else {
				logger.debug("method updateUniversityIndex: deleteIndex");
				university = universityDao.universityInfoToEntity(universityInfo);
				indexerService.deleteIndex(university);
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
			if (departmentInfo.isEnabled()) {
				logger.debug("method updateDepartmentIndex: updateIndex");
				department = departmentDao.departmentInfoToEntity(departmentInfo);
				indexerService.updateIndex(department);
			} else {
				logger.debug("method updateDepartmentIndex: deleteIndex");
				department = departmentDao.departmentInfoToEntity(departmentInfo);
				indexerService.deleteIndex(department);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	
	/**
	 * Update department index By Id.
	 * @param departmentId, status
	 */
	public void updateDepartmentIndexById(Long departmentId, Boolean status) {
		logger.debug("Starting method updateDepartmentIndexById");
		try {
			DepartmentInfo departmentInfo = departmentService.findDepartment(departmentId);
			if (departmentInfo.isEnabled()) {
				logger.debug("method updateDepartmentIndex: updateIndex");
				department = departmentDao.departmentInfoToEntity(departmentInfo);
				indexerService.updateIndex(department);
			} else {
				logger.debug("method updateDepartmentIndex: deleteIndex");
				department = departmentDao.departmentInfoToEntity(departmentInfo);
				indexerService.deleteIndex(department);
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
	
	/**
	 * Create index entry of a course type.
	 * @param courseTypeInfo
	 */
	public void createCourseTypeIndex(CourseTypeInfo courseTypeInfo) {
		logger.debug("Starting method createCourseTypeIndex");
		try {
			logger.debug("method createCourseTypeIndex: createIndex");
			courseType = courseTypeDao.courseTypeInfoToEntity(courseTypeInfo);
			indexerService.createIndex(courseType);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Update course type index.
	 * @param courseTypeInfo
	 */
	public void updateCourseTypeIndex(CourseTypeInfo courseTypeInfo) {
		logger.debug("Starting method updateCourseTypeIndex");
		try {
			logger.debug("method updateCourseTypeIndex: updateIndex");
			courseType = courseTypeDao.courseTypeInfoToEntity(courseTypeInfo);
			indexerService.updateIndex(courseType);
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
	 * Delete course type from lecture index.
	 * @param courseTypeId
	 */
	public void deleteCourseTypeIndex(Long courseTypeId) {
		logger.debug("Starting method deleteCourseTypeIndex");
		try {
			logger.debug("method deleteCourseTypeIndex: deleteIndex");
			CourseType courseType = CourseType.Factory.newInstance();
			courseType.setId(courseTypeId);
			indexerService.deleteIndex(courseType);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	public void updateCourseIndexOnPeriodUpdate(Period period) {
		logger.info("Starting method updateCourseIndexOnPeriodUpdate(");
		Validate.notNull(period, "Parameter period must not be null");
		for (Course course : period.getCourses()) {
			CourseInfo courseInfo = courseDao.toCourseInfo(course);
			updateCourseIndex(courseInfo);
		}
	}

	public void createCourseIndex(CourseInfo courseInfo) {
		logger.info("Starting method createCourseIndex");
		try {
			if (courseInfo.getAccessType() != AccessType.CLOSED) {
				course = courseDao.courseInfoToEntity(courseInfo);
				indexerService.createIndex(course);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}

	}

	public void updateCourseIndex(CourseInfo courseInfo) {
		logger.info("Starting method updateCourseIndex");
		try {
			if (courseInfo.getAccessType() == AccessType.CLOSED) {
				logger.info("Deleting CourseIndex");
				course = courseDao.courseInfoToEntity(courseInfo);
				indexerService.deleteIndex(course);
			} else {
				course = courseDao.courseInfoToEntity(courseInfo);
				indexerService.updateIndex(course);
			}
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	public void deleteCourseIndex(Long courseId) {
		logger.info("Starting method deleteCourseIndex");
		try {
			Course course = Course.Factory.newInstance();
			course.setId(courseId);
			indexerService.deleteIndex(course);
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

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public CourseTypeDao getCourseTypeDao() {
		return courseTypeDao;
	}

	public void setCourseTypeDao(CourseTypeDao courseTypeDao) {
		this.courseTypeDao = courseTypeDao;
	}

	public CourseType getCourseType() {
		return courseType;
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
}
