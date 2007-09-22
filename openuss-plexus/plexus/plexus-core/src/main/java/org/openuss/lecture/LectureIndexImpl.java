package org.openuss.lecture;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;

/**
 * @author Ingo Dueppe
 */
public class LectureIndexImpl implements LectureIndex{

	private static final Logger logger = Logger.getLogger(LectureIndexImpl.class);
	
	private InstituteDao instituteDao;
	private CourseDao courseDao;
	
	private Directory directory;
	
	private InstituteIndexer instituteIndexer;
	private CourseIndexer courseIndexer;

	/**
	 * Recreates the full lecture index
	 * @throws Exception
	 */
	public void recreate() throws Exception {
		logger.debug("start to recreate lecture index.");

		indexInstitutes();
		indexCourses();
		
		logger.debug("recreated lecture index.");
	}

	private void indexInstitutes() {
		logger.debug("indexing institutes...");
		Collection<Institute> institutes = instituteDao.loadAll();
		
		for(Institute institute: institutes) {
			instituteIndexer.setDomainObject(institute);
			instituteIndexer.create();
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
