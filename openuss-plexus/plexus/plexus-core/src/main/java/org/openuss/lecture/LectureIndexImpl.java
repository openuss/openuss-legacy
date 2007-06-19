package org.openuss.lecture;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;

/**
 * @author Ingo Dueppe
 */
public class LectureIndexImpl implements LectureIndex{

	private static final Logger logger = Logger.getLogger(LectureIndexImpl.class);
	
	private FacultyDao facultyDao;
	private CourseDao courseDao;
	
	private Directory directory;
	
	private FacultyIndexer facultyIndexer;
	private CourseIndexer courseIndexer;

	/**
	 * Recreates the full lecture index
	 * @throws Exception
	 */
	public void recreate() throws Exception {
		logger.debug("start to recreate lecture index.");

		indexFaculties();
		indexCourses();
		
		logger.debug("recreated lecture index.");
	}

	private void indexFaculties() {
		logger.debug("indexing faculties...");
		Collection<Faculty> faculties = facultyDao.loadAll();
		
		for(Faculty faculty: faculties) {
			facultyIndexer.setDomainObject(faculty);
			facultyIndexer.create();
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

	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}

	public FacultyIndexer getFacultyIndexer() {
		return facultyIndexer;
	}

	public void setFacultyIndexer(FacultyIndexer facultyIndexer) {
		this.facultyIndexer = facultyIndexer;
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
