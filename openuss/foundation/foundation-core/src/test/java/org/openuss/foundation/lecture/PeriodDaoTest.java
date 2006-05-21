// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;


/**
 * JUnit Test for Spring Hibernate PeriodDao class.
 * @see org.openuss.foundation.lecture.PeriodDao
 */
public class PeriodDaoTest extends PeriodDaoTestBase {
	
	private FacultyDao facultyDao; 
	
	public void testPeriodDaoCreate() {
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setName("name");
		faculty.setOwner("owner");
		faculty.setShortcut("shortcut");
		assertNull(faculty.getId());
		facultyDao.create(faculty);
		assertNotNull(faculty.getId());
		
		Period period = new PeriodImpl();
		period.setFaculty(faculty);
		period.setName(" ");
		assertNull(period.getId());
		periodDao.create(period);
		assertNotNull(period.getId());
	}
	
	public void testPeriodFactory() {
		Period period = Period.Factory.newInstance();
		assertNotNull(period);
	}
	
	public void testFacultyDaoInjection() {
		assertNotNull(facultyDao);
	}

	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}
}