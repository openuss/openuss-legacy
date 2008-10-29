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
	
	public void testCourseTypeDaoInjection() {
		assertNotNull(courseTypeDao);
	}
	
	public void testCourseUniqueShortcut() {
		String shortcut = testUtility.unique("shortcut");
		
		// create university
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university);
		
		// create institute
		Institute institute = testUtility.createUniqueInstituteInDB();
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

		flush();
		
		// create second course
		Course course2 = new CourseImpl();
		course2.setCourseType(courseType);
		course2.setPeriod(period);
		course2.setShortcut(shortcut);
		assertNull(course2.getId());
		
		flush();
		
		try {
			courseDao.create(course2);
			flush();			
			// succeed - can create two or more courses with the same shortcut
		} catch (Exception e) {
			fail("Shorcut must still be unique!");
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
	
		//Create Period
		Period period = testUtility.createUniquePeriodInDB();
		
		//Create CourseType
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		
		// Create a complete CourseInfo
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setName(courseType.getName());
		courseInfo.setShortcut(testUtility.unique("testC"));
		courseInfo.setDescription("This is a test Course");
		courseInfo.setAccessType(AccessType.OPEN);
		courseInfo.setCourseTypeId(courseType.getId());
		courseInfo.setCourseTypeDescription(courseType.getDescription());
		courseInfo.setPeriodId(period.getId());
		courseInfo.setPeriodName(period.getName());
		courseInfo.setPassword(testUtility.unique("passwd"));
		
		// Test toEntity
		Course course = this.getCourseDao().courseInfoToEntity(courseInfo);
		
		assertEquals(courseInfo.getId(), course.getId());
		assertEquals(courseInfo.getName(), course.getName());
		assertEquals(courseInfo.getShortcut(), course.getShortcut());
		assertEquals(courseInfo.getDescription(), course.getDescription());
		assertEquals(courseInfo.getAccessType(), course.getAccessType());
		assertEquals(courseInfo.getPassword(), course.getPassword());
		assertEquals(courseInfo.getCourseTypeId(), course.getCourseType().getId());
		assertEquals(courseInfo.getCourseTypeDescription(), course.getCourseType().getDescription());
		assertEquals(courseInfo.getPeriodId(), course.getPeriod().getId());
		assertEquals(courseInfo.getPeriodName(), course.getPeriod().getName());
	}

	private Period createPeriod() {
		Period period = new PeriodImpl();
		period.setName("name");
		period.setStartdate(new Date(new GregorianCalendar(2007, 3, 1).getTimeInMillis()));
		period.setEnddate(new Date(new GregorianCalendar(2007, 9, 31).getTimeInMillis()));
		return period;
	}

	private CourseType createCourseType(Institute institute) {
		CourseType courseType = new CourseTypeImpl();
		courseType.setName("name");
		courseType.setShortcut(testUtility.unique("shortcut"));
		courseType.setInstitute(institute);
		return courseType;
	}

	private Institute createInstitute() {
		user = testUtility.createUniqueUserInDB();
		userDao.create(user);
				
		Institute institute = new InstituteImpl();
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
