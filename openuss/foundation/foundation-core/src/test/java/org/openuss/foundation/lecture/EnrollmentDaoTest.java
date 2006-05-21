// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;

import org.springframework.dao.DataAccessException;


/**
 * JUnit Test for Spring Hibernate EnrollmentDao class.
 * @see org.openuss.foundation.lecture.EnrollmentDao
 */
public class EnrollmentDaoTest extends EnrollmentDaoTestBase {
	
	private FacultyDao facultyDao;
	private SubjectDao subjectDao;
	private PeriodDao periodDao;
	
	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}
	
	public FacultyDao getFacultyDao() {
		return facultyDao;
	}
	
	public void setSubjectDao(SubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	
	public SubjectDao getSubjectDao() {
		return subjectDao;
	}
	
	public void setPeriodDao(PeriodDao periodDao) {
		this.periodDao = periodDao;
	}
	
	public PeriodDao getPeriodDao() {
		return periodDao;
	}
	
	public void testFacultyDaoInjection() {
		assertNotNull(facultyDao);
	}
	
	public void testSubjectDaoInjection() {
		assertNotNull(subjectDao);
	}
	
	public void testPeriodDaoInjection() {
		assertNotNull(periodDao);
	}
	
	public void testEnrollmentUniqueShortcut() {
		// create faculty
		Faculty faculty = createFaculty();
		facultyDao.create(faculty);
		assertNotNull(faculty);
		
		// create subject
		Subject subject = createSubject(faculty);
		subject.setFaculty(faculty);
		subjectDao.create(subject);
		assertNotNull(subjectDao);
		
		//create period
		Period period = createPeriod();
		period.setFaculty(faculty);
		periodDao.create(period);
		assertNotNull(periodDao);
		
		// create first enrollment
		Enrollment enrollment = new EnrollmentImpl();
		enrollment.setFaculty(faculty);
		enrollment.setSubject(subject);
		enrollment.setPeriod(period);
		enrollment.setShortcut("shortcut");
		assertNull(enrollment.getId());
		enrollmentDao.create(enrollment);
		assertNotNull(enrollment.getId());

		// create first enrollment
		Enrollment enrollment2 = new EnrollmentImpl();
		enrollment2.setFaculty(faculty);
		enrollment2.setSubject(subject);
		enrollment2.setPeriod(period);
		enrollment2.setShortcut("shortcut");
		assertNull(enrollment2.getId());
		enrollmentDao.create(enrollment2);
		assertNotNull(enrollment2.getId());
		
		try {
			setComplete();
			endTransaction();
		} catch (DataAccessException e) {
			// success - unique key constraint
		}
		
		
	}
	
	public void testEnrollmentDaoCreate() {
		// create faculty
		Faculty faculty = createFaculty();
		facultyDao.create(faculty);
		assertNotNull(faculty);
		
		// create subject
		Subject subject = createSubject(faculty);
		subject.setFaculty(faculty);
		subjectDao.create(subject);
		assertNotNull(subjectDao);
		
		//create period
		Period period = createPeriod();
		period.setFaculty(faculty);
		assertNull(period.getId());
		periodDao.create(period);
		assertNotNull(period.getId());
		
		Enrollment enrollment = new EnrollmentImpl();
		enrollment.setFaculty(faculty);
		enrollment.setSubject(subject);
		enrollment.setPeriod(period);
		enrollment.setShortcut("shortcut");

		assertNull(enrollment.getId());
		enrollmentDao.create(enrollment);
		assertNotNull(enrollment.getId());
	}

	private Period createPeriod() {
		Period period = Period.Factory.newInstance();
		period.setName("name");		
		return period;
	}

	private Subject createSubject(Faculty faculty) {
		Subject subject = Subject.Factory.newInstance();
		subject.setName("name");
		subject.setShortcut("shortcut");
		subject.setFaculty(faculty);
		return subject;
	}

	private Faculty createFaculty() {
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setName("name");
		faculty.setShortcut("shortcut");
		faculty.setOwner("owner");
		return faculty;
	}
	
	
}