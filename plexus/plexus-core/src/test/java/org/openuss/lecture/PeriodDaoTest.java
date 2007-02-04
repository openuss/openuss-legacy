// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.lecture.PeriodImpl;
import org.openuss.security.SecurityTestUtils;
import org.openuss.security.User;
import org.openuss.security.UserDao;


/**
 * JUnit Test for Spring Hibernate PeriodDao class.
 * @see org.openuss.lecture.PeriodDao
 */
public class PeriodDaoTest extends PeriodDaoTestBase {
	
	private FacultyDao facultyDao;
	private UserDao userDao;
	
	public void testPeriodDaoCreate() {
		User user = SecurityTestUtils.createDefaultUser();
		userDao.create(user);
		
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setName("name");
		faculty.setOwnername("ownername");
		faculty.setOwner(user);
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

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}