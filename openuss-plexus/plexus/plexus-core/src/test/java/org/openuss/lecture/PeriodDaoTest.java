// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
		
		//Create Period
		Period period = Period.Factory.newInstance();
		period.setUniversity(university);
		period.setName("WS 07/08");
		period.setDescription("A new period");
		period.setStartdate(startdate);
		period.setEnddate(enddate);
		
		//Test
		PeriodInfo periodInfo = this.getPeriodDao().toPeriodInfo(period);
		assertNotNull(periodInfo);
		assertEquals(period.getId(), periodInfo.getId());
		assertEquals(period.getName(), periodInfo.getName());
		assertEquals(period.getDescription(), periodInfo.getDescription());
		assertEquals(period.getStartdate().getTime(), periodInfo.getStartdate().getTime());
		assertEquals(period.getEnddate().getTime(), periodInfo.getEnddate().getTime());
		assertEquals(period.getUniversity().getId(), periodInfo.getUniversityId());
	}
	
	public void testEntityToPeriodInfo () {
		
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
		assertEquals(periodInfo.getStartdate().getTime(), period.getStartdate().getTime());
		assertEquals(periodInfo.getEnddate().getTime(), period.getEnddate().getTime());
		assertEquals(periodInfo.getUniversityId(), period.getUniversity().getId());
	}
	
	public void testPeriodFactory() {
		Period period = Period.Factory.newInstance();
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