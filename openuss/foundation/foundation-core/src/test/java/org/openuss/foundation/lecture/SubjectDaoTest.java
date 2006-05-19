// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;


/**
 * JUnit Test for Spring Hibernate SubjectDao class.
 * @see org.openuss.foundation.lecture.SubjectDao
 */
public class SubjectDaoTest extends SubjectDaoTestBase {
	
	public FacultyDao facultyDao;
	
	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}
	
	public FacultyDao getFacultyDao() {
		return facultyDao;
	}
	
	public void testFacultyInjection() {
		assertNotNull(facultyDao);
	}
	
	public void testSubjectDaoCreate() {
		// create your faculty
		Faculty faculty = new FacultyImpl();
		faculty.setName("name");
		faculty.setShortcut("shortcut");
		faculty.setOwner("owner");
		facultyDao.create(faculty);
		assertNotNull(faculty.getId());
		
		// create a subject
		Subject subject = new SubjectImpl();
		subject.setFaculty(faculty);
		subject.setName("subject");
		subject.setShortcut("subject");
		assertNull(subject.getId());
		subjectDao.create(subject);
		assertNotNull(subject.getId());
	}
}