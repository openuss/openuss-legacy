// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.TestUtility;
import org.openuss.security.User;
import org.openuss.security.UserDao;
import org.springframework.dao.DataAccessException;


/**
 * JUnit Test for Spring Hibernate CourseDao class.
 * @see org.openuss.lecture.CourseDao
 */
public class CourseDaoTest extends CourseDaoTestBase {
	
	private TestUtility testUtility;

	private InstituteDao instituteDao;
	private CourseTypeDao courseTypeDao;
	private PeriodDao periodDao;
	private UserDao userDao;
	private User user;
	
	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}
	
	public InstituteDao getInstituteDao() {
		return instituteDao;
	}
	
	public void setCourseTypeDao(CourseTypeDao courseTypeDao) {
		this.courseTypeDao = courseTypeDao;
	}
	
	public CourseTypeDao getCourseTypeDao() {
		return courseTypeDao;
	}
	
	public void setPeriodDao(PeriodDao periodDao) {
		this.periodDao = periodDao;
	}
	
	public PeriodDao getPeriodDao() {
		return periodDao;
	}
	
	public void testInstituteDaoInjection() {
		assertNotNull(instituteDao);
	}
	
	public void testCourseTypeDaoInjection() {
		assertNotNull(courseTypeDao);
	}
	
	public void testPeriodDaoInjection() {
		assertNotNull(periodDao);
	}
	
	public void testCourseUniqueShortcut() {
		String shortcut = testUtility.unique("shortcut");
		// create institute
		Institute institute = createInstitute();
		instituteDao.create(institute);
		assertNotNull(institute);
		
		// create courseType
		CourseType courseType = createCourseType(institute);
		courseType.setInstitute(institute);
		courseTypeDao.create(courseType);
		assertNotNull(courseTypeDao);
		
		//create period
		Period period = createPeriod();
		period.setInstitute(institute);
		periodDao.create(period);
		assertNotNull(periodDao);
		
		// create first course
		Course course = new CourseImpl();
		course.setInstitute(institute);
		course.setCourseType(courseType);
		course.setPeriod(period);
		course.setShortcut(shortcut);
		assertNull(course.getId());
		courseDao.create(course);
		assertNotNull(course.getId());

		commit();
		
		// create secord course
		Course course2 = new CourseImpl();
		course2.setInstitute(institute);
		course2.setCourseType(courseType);
		course2.setPeriod(period);
		course2.setShortcut(shortcut);
		assertNull(course2.getId());
		courseDao.create(course2);
		assertNotNull(course2.getId());
		
		try {
			commit();
			fail("Shortcut violation expected!");
		} catch (DataAccessException e) {
			// succeed - cannot create two or more courses with the same shortcut
		}
	}
	
	public void testCourseDaoCreate() {
		// create institute
		Institute institute = createInstitute();
		instituteDao.create(institute);
		assertNotNull(institute);
		
		// create courseType
		CourseType courseType = createCourseType(institute);
		courseType.setInstitute(institute);
		courseTypeDao.create(courseType);
		assertNotNull(courseTypeDao);
		
		//create period
		Period period = createPeriod();
		period.setInstitute(institute);
		assertNull(period.getId());
		periodDao.create(period);
		assertNotNull(period.getId());
		
		Course course = new CourseImpl();
		course.setInstitute(institute);
		course.setCourseType(courseType);
		course.setPeriod(period);
		course.setShortcut("shortcut");

		assertNull(course.getId());
		courseDao.create(course);
		assertNotNull(course.getId());
	}

	private Period createPeriod() {
		Period period = Period.Factory.newInstance();
		period.setName("name");		
		return period;
	}

	private CourseType createCourseType(Institute institute) {
		CourseType courseType = CourseType.Factory.newInstance();
		courseType.setName("name");
		courseType.setShortcut(testUtility.unique("shortcut"));
		courseType.setInstitute(institute);
		return courseType;
	}

	private Institute createInstitute() {
		user = testUtility.createDefaultUser();
		userDao.create(user);
				
		Institute institute = Institute.Factory.newInstance();
		institute.setName("CourseDaoTest");
		institute.setShortcut(testUtility.unique("shortcut"));
		institute.setOwnername("ownername");
		institute.setOwner(user);
		institute.setEmail("email@institute");
		return institute;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	
}