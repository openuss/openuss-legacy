// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateUtils;
import org.openuss.TestUtility;
import org.openuss.security.UserDao;


/**
 * JUnit Test for Spring Hibernate PeriodDao class.
 * @see org.openuss.lecture.PeriodDao
 */
public class PeriodDaoTest extends PeriodDaoTestBase {
	
	private InstituteDao instituteDao;
	private UserDao userDao;
	private TestUtility testUtility;
	
	public void testPeriodDaoCreate() {
		
		//Create university
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		//Create Startdate
		Calendar cal = new GregorianCalendar();
		cal.set(2007, 10, 1);
		Date startdate = new Date(cal.getTimeInMillis());
		
		//Create Enddate
		cal = new GregorianCalendar();
		cal.set(2008, 3, 31);
		Date enddate = new Date(cal.getTimeInMillis());
		
		//Create Period
		Period period = new PeriodImpl();
		period.setUniversity(university);
		period.setName("WS 07/08");
		period.setDescription("A new period");
		period.setStartdate(startdate);
		period.setEnddate(enddate);
		
		assertNull(period.getId());
		periodDao.create(period);
		assertNotNull(period.getId());
	}
	
	public void testEntityToPeriodInfo () {
		
		//Create university
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		//Create Startdate
		Date now = DateUtils.truncate(new Date(), Calendar.DATE);
		Date pastOne = DateUtils.addDays(now, -10);
		Date pastTwo = DateUtils.addDays(now, -2);
		Date futureOne = DateUtils.addDays(now, 10);
		
		Date startdate = pastOne;
		Date enddate = pastTwo;
		
		//Create Period
		Period period = new PeriodImpl();
		period.setUniversity(university);
		period.setName("WS 07/08");
		period.setDescription("A new period");
		period.setStartdate(startdate);
		period.setEnddate(enddate);
		
		//Test with non active period
		PeriodInfo periodInfo = this.getPeriodDao().toPeriodInfo(period);
		assertNotNull(periodInfo);
		assertEquals(period.getId(), periodInfo.getId());
		assertEquals(period.getName(), periodInfo.getName());
		assertEquals(period.getDescription(), periodInfo.getDescription());
		assertEquals(period.getStartdate().getTime(), periodInfo.getStartdate().getTime());
		assertEquals(period.getEnddate().getTime(), periodInfo.getEnddate().getTime());
		assertEquals(period.getUniversity().getId(), periodInfo.getUniversityId());
		assertFalse(periodInfo.isActive());
		
		// Test with active period and courses in list
		
		//Create Startdate
		startdate = pastOne;
		
		//Create Enddate
		enddate = futureOne;
		
		Period activePeriod = testUtility.createUniquePeriodInDB();
		activePeriod.setStartdate(startdate);
		activePeriod.setEnddate(enddate);
		
		// Create Courses
		Course course1 = testUtility.createUniqueCourseInDB();
		activePeriod.getCourses().add(course1);
		Course course2 = testUtility.createUniqueCourseInDB();
		activePeriod.getCourses().add(course2);
		
		periodInfo = this.getPeriodDao().toPeriodInfo(activePeriod);
		assertTrue(periodInfo.isActive());
		
	}
	
	public void testPeriodInfoToEntity () {
		
		//Create university
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		//Create Startdate
		Calendar cal = new GregorianCalendar();
		cal.set(2007, 10, 1);
		Date startdate = new Date(cal.getTimeInMillis());
		
		//Create Enddate
		cal = new GregorianCalendar();
		cal.set(2008, 3, 31);
		Date enddate = new Date(cal.getTimeInMillis());
		
		//Create PeriodInfo
		PeriodInfo periodInfo = new PeriodInfo();
		periodInfo.setUniversityId(university.getId());
		periodInfo.setName("WS 07/08");
		periodInfo.setDescription("A new period");
		periodInfo.setStartdate(startdate);
		periodInfo.setEnddate(enddate);
		
		//Test
		Period period = this.getPeriodDao().periodInfoToEntity(periodInfo);
		assertNotNull(period);
		assertEquals(periodInfo.getId(), period.getId());
		assertEquals(periodInfo.getName(), period.getName());
		assertEquals(periodInfo.getDescription(), period.getDescription());
		assertEquals(truncate(periodInfo.getStartdate()),truncate(period.getStartdate()));
		assertEquals(truncate(periodInfo.getEnddate()), truncate(period.getEnddate()));
		assertEquals(periodInfo.getUniversityId(), period.getUniversity().getId());
	}
	
	private Date truncate(Date date) {
		return DateUtils.truncate(date, Calendar.DATE);
	}
	
	public void testPeriodFactory() {
		Period period = new PeriodImpl();
		assertNotNull(period);
	}
	
	public void testInstituteDaoInjection() {
		assertNotNull(instituteDao);
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
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