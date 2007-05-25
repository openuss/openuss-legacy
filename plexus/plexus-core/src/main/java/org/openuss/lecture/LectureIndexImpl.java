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
	private EnrollmentDao enrollmentDao;
	
	private Directory directory;
	
	private FacultyIndexer facultyIndexer;
	private EnrollmentIndexer enrollmentIndexer;

	/**
	 * Recreates the full lecture index
	 * @throws Exception
	 */
	public void recreate() throws Exception {
		logger.debug("start to recreate lecture index.");

		indexFaculties();
		indexEnrollments();
		
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

	private void indexEnrollments() {
		logger.debug("indexing enrollments...");
		Collection<Enrollment> enrollments = enrollmentDao.loadAll();
		
		for(Enrollment enrollment: enrollments) {
			enrollmentIndexer.setDomainObject(enrollment);
			enrollmentIndexer.create();
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

	public EnrollmentDao getEnrollmentDao() {
		return enrollmentDao;
	}

	public void setEnrollmentDao(EnrollmentDao enrollmentDao) {
		this.enrollmentDao = enrollmentDao;
	}

	public EnrollmentIndexer getEnrollmentIndexer() {
		return enrollmentIndexer;
	}

	public void setEnrollmentIndexer(EnrollmentIndexer enrollmentIndexer) {
		this.enrollmentIndexer = enrollmentIndexer;
	}

}
