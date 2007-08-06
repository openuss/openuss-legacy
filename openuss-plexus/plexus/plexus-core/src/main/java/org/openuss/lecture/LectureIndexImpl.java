package org.openuss.lecture;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;

/**
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class LectureIndexImpl implements LectureIndex{

	private static final Logger logger = Logger.getLogger(LectureIndexImpl.class);
	
	private UniversityDao universityDao;
	private DepartmentDao departmentDao;
	private InstituteDao instituteDao;
	private CourseTypeDao courseTypeDao;
	private CourseDao courseDao;
	
	private Directory directory;
	
	private UniversityIndexer universityIndexer;
	private DepartmentIndexer departmentIndexer;
	private InstituteIndexer instituteIndexer;
	private CourseTypeIndexer courseTypeIndexer;
	private CourseIndexer courseIndexer;

	/**
	 * Recreates the full lecture index
	 * @throws Exception
	 */
	public void recreate() throws Exception {
		logger.debug("start to recreate lecture index.");

		indexUniversities();
		indexDepartments();
		indexInstitutes();
		indexCourseTypes();
		indexCourses();
		
		logger.debug("recreated lecture index.");
	}
	
	private void indexUniversities() {
		logger.debug("indexing universities...");
		Collection<University> universities = universityDao.loadAll();
		
		for(University university: universities) {
			universityIndexer.setDomainObject(university);
			universityIndexer.create();
		}
	}
	
	private void indexDepartments() {
		logger.debug("indexing departments...");
		Collection<Department> departments = departmentDao.loadAll();
		
		for(Department department: departments) {
			departmentIndexer.setDomainObject(department);
			departmentIndexer.create();
		}
	}

	private void indexInstitutes() {
		logger.debug("indexing institutes...");
		Collection<Institute> institutes = instituteDao.loadAll();
		
		for(Institute institute: institutes) {
			instituteIndexer.setDomainObject(institute);
			instituteIndexer.create();
		}
	}
	
	private void indexCourseTypes() {
		logger.debug("indexing course types...");
		Collection<CourseType> courseTypes = courseTypeDao.loadAll();
		
		for(CourseType courseType: courseTypes) {
			courseTypeIndexer.setDomainObject(courseType);
			courseTypeIndexer.create();
		}
	}

	private void indexCourses() {
		logger.debug("indexing courses...");
		Collection<Course> courses = courseDao.loadAll();
		
		for(Course course: courses) {
			courseIndexer.setDomainObject(course);
			courseIndexer.create();
		}
	}
	
	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}

	public UniversityIndexer getUniversityIndexer() {
		return universityIndexer;
	}

	public void setUniversityIndexer(UniversityIndexer universityIndexer) {
		this.universityIndexer = universityIndexer;
	}
	
	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public DepartmentIndexer getDepartmentIndexer() {
		return departmentIndexer;
	}

	public void setDepartmentIndexer(DepartmentIndexer departmentIndexer) {
		this.departmentIndexer = departmentIndexer;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public InstituteIndexer getInstituteIndexer() {
		return instituteIndexer;
	}

	public void setInstituteIndexer(InstituteIndexer instituteIndexer) {
		this.instituteIndexer = instituteIndexer;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}
	
	public CourseTypeDao getCourseTypeDao() {
		return courseTypeDao;
	}

	public void setCourseTypeDao(CourseTypeDao courseTypeDao) {
		this.courseTypeDao = courseTypeDao;
	}

	public CourseTypeIndexer getCourseTypeIndexer() {
		return courseTypeIndexer;
	}

	public void setCourseTypeIndexer(CourseTypeIndexer courseTypeIndexer) {
		this.courseTypeIndexer = courseTypeIndexer;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public CourseIndexer getCourseIndexer() {
		return courseIndexer;
	}

	public void setCourseIndexer(CourseIndexer courseIndexer) {
		this.courseIndexer = courseIndexer;
	}

}
