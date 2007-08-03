// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Date;
import java.util.GregorianCalendar;

import org.openuss.TestUtility;
import org.openuss.security.Membership;
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
		
		// create university
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university);
		
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
		Period period = testUtility.createUniquePeriodInDB();
		assertNotNull(period);
		
		// create first course
		Course course = new CourseImpl();
		course.setCourseType(courseType);
		course.setPeriod(period);
		course.setShortcut(shortcut);
		assertNull(course.getId());
		courseDao.create(course);
		assertNotNull(course.getId());

		commit();
		
		// create second course
		Course course2 = new CourseImpl();
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
		
		// create university
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university);
		
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
		period.setUniversity(university);
		assertNull(period.getId());
		periodDao.create(period);
		assertNotNull(period.getId());
		
		Course course = new CourseImpl();
		course.setCourseType(courseType);
		course.setPeriod(period);
		course.setShortcut("shortcut");

		assertNull(course.getId());
		courseDao.create(course);
		assertNotNull(course.getId());
	}
	
	public void testCourseToCourseInfo () {
		
		// Create Course Entity
		Course courseEntity = testUtility.createUniqueCourseInDB();
		assertNotNull(courseEntity);
		
		// Test
		CourseInfo courseInfo = this.getCourseDao().toCourseInfo(courseEntity);
		assertNotNull(courseInfo);
		assertEquals(courseEntity.getName(), courseInfo.getName());
		assertEquals(courseEntity.getDescription(), courseInfo.getDescription());
		assertEquals(courseEntity.getAccessType(), courseInfo.getAccessType());
		assertEquals(courseEntity.getCourseType().getId(), courseInfo.getCourseTypeId());
		assertEquals(courseEntity.getCourseType().getDescription(), courseInfo.getCourseTypeDescription());
		assertEquals(courseEntity.getPassword(), courseInfo.getPassword());
		assertEquals(courseEntity.getPeriod().getId(), courseInfo.getPeriodId());
		assertEquals(courseEntity.getPeriod().getName(), courseInfo.getPeriodName());
		assertEquals(courseEntity.getShortcut(), courseInfo.getShortcut());
	}
	
	public void testCourseInfoToEntity () {
	
		// Create a complete Course
		Course course = Course.Factory.newInstance();
		course.setShortcut(testUtility.unique("testC"));
		course.setDescription("This is a test Course");
		course.setAccessType(AccessType.OPEN);
		course.setCourseType(testUtility.createUniqueCourseTypeInDB());
		course.setPeriod(testUtility.createUniquePeriodInDB());
		course.setPassword(testUtility.unique("passwd"));
		
		courseDao.create(course);
		assertNotNull(course.getId());
		
		// Create the corresponding ValueObject
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setId(course.getId());
		courseInfo.setName(course.getName());
		courseInfo.setDescription(course.getDescription());
		courseInfo.setAccessType(course.getAccessType());
		courseInfo.setCourseTypeId(course.getCourseType().getId());
		courseInfo.setCourseTypeDescription(course.getCourseType().getDescription());
		courseInfo.setPeriodId(course.getPeriod().getId());
		courseInfo.setPeriodName(course.getPeriod().getName());
		courseInfo.setPassword(course.getPassword());
		
		// Test toEntity
		Course course2 = courseDao.courseInfoToEntity(courseInfo);
		
		assertEquals(course2.getId(), courseInfo.getId());
		assertEquals(course2.getName(), courseInfo.getName());
		assertEquals(course2.getShortcut(), courseInfo.getShortcut());
		assertEquals(course2.getDescription(), courseInfo.getDescription());
		assertEquals(course2.getAccessType(), courseInfo.getAccessType());
		assertEquals(course2.getPassword(), courseInfo.getPassword());
		assertEquals(course2.getCourseType().getId(), courseInfo.getCourseTypeId());
		assertEquals(course2.getCourseType().getDescription(), courseInfo.getCourseTypeDescription());
		assertEquals(course2.getPeriod().getId(), courseInfo.getPeriodId());
		assertEquals(course2.getPeriod().getName(), courseInfo.getPeriodName());
	}

	private Period createPeriod() {
		Period period = Period.Factory.newInstance();
		period.setName("name");
		period.setStartdate(new Date(new GregorianCalendar(2007, 3, 1).getTimeInMillis()));
		period.setEnddate(new Date(new GregorianCalendar(2007, 9, 31).getTimeInMillis()));
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
		institute.setOwnerName("ownername");
		institute.setEnabled(true);
		institute.setMembership(Membership.Factory.newInstance());
		//institute.set(user);
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