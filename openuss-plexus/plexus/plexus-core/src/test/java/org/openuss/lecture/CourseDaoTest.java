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

	private FacultyDao facultyDao;
	private CourseTypeDao courseTypeDao;
	private PeriodDao periodDao;
	private UserDao userDao;
	private User user;
	
	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}
	
	public FacultyDao getFacultyDao() {
		return facultyDao;
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
	
	public void testFacultyDaoInjection() {
		assertNotNull(facultyDao);
	}
	
	public void testCourseTypeDaoInjection() {
		assertNotNull(courseTypeDao);
	}
	
	public void testPeriodDaoInjection() {
		assertNotNull(periodDao);
	}
	
	public void testCourseUniqueShortcut() {
		String shortcut = testUtility.unique("shortcut");
		// create faculty
		Faculty faculty = createFaculty();
		facultyDao.create(faculty);
		assertNotNull(faculty);
		
		// create courseType
		CourseType courseType = createCourseType(faculty);
		courseType.setFaculty(faculty);
		courseTypeDao.create(courseType);
		assertNotNull(courseTypeDao);
		
		//create period
		Period period = createPeriod();
		period.setFaculty(faculty);
		periodDao.create(period);
		assertNotNull(periodDao);
		
		// create first course
		Course course = new CourseImpl();
		course.setFaculty(faculty);
		course.setCourseType(courseType);
		course.setPeriod(period);
		course.setShortcut(shortcut);
		assertNull(course.getId());
		courseDao.create(course);
		assertNotNull(course.getId());

		commit();
		
		// create secord course
		Course course2 = new CourseImpl();
		course2.setFaculty(faculty);
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
		// create faculty
		Faculty faculty = createFaculty();
		facultyDao.create(faculty);
		assertNotNull(faculty);
		
		// create courseType
		CourseType courseType = createCourseType(faculty);
		courseType.setFaculty(faculty);
		courseTypeDao.create(courseType);
		assertNotNull(courseTypeDao);
		
		//create period
		Period period = createPeriod();
		period.setFaculty(faculty);
		assertNull(period.getId());
		periodDao.create(period);
		assertNotNull(period.getId());
		
		Course course = new CourseImpl();
		course.setFaculty(faculty);
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

	private CourseType createCourseType(Faculty faculty) {
		CourseType courseType = CourseType.Factory.newInstance();
		courseType.setName("name");
		courseType.setShortcut(testUtility.unique("shortcut"));
		courseType.setFaculty(faculty);
		return courseType;
	}

	private Faculty createFaculty() {
		user = testUtility.createDefaultUser();
		userDao.create(user);
				
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setName("CourseDaoTest");
		faculty.setShortcut(testUtility.unique("shortcut"));
		faculty.setOwnername("ownername");
		faculty.setOwner(user);
		faculty.setEmail("email@faculty");
		return faculty;
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