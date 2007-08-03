// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.security.Membership;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate UniversityService class.
 * @see org.openuss.lecture.UniversityService
 * @author Ron Haus
 */
public class UniversityServiceIntegrationTest extends UniversityServiceIntegrationTestBase {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UniversityServiceIntegrationTest.class);
	
	
	public void testCreateUniversity() {
		logger.info("----> BEGIN access to create(University) test");

		//Create new UniversityInfo object
		UniversityInfo universityInfo = new UniversityInfo();
		universityInfo.setName(testUtility.unique("testUniversity"));
		universityInfo.setShortcut(testUtility.unique("testU"));
		universityInfo.setOwnerName("Administrator");
		universityInfo.setEnabled(true);
		universityInfo.setDescription("This is a test University");
		universityInfo.setUniversityType(UniversityType.MISC);
		
		//Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();
		
		//Create Entity
		Long universityId = universityService.create(universityInfo, owner.getId());
		assertNotNull(universityId);
		
		//Synchronize with Database
		flush();

		logger.info("----> END access to create(University) test");
	}
	
	public void testCreatePeriod () {
		logger.info("----> BEGIN access to create(Period) test");
		
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
		PeriodInfo periodInfo = new PeriodInfo();
		periodInfo.setUniversityId(university.getId());
		periodInfo.setName("WS 07/08");
		periodInfo.setDescription("A new period");
		periodInfo.setStartdate(startdate);
		periodInfo.setEnddate(enddate);
		
		//Test
		Long periodId = this.getUniversityService().create(periodInfo);
		assertNotNull(periodId);

		PeriodDao periodDao = (PeriodDao) this.getApplicationContext().getBean("periodDao");
		Period period = periodDao.load(periodId);
		assertNotNull(period);
		assertEquals(periodId, period.getId());
		assertEquals(periodInfo.getName(), period.getName());
		
		assertEquals(1, period.getUniversity().getPeriods().size());
		
		logger.info("----> END access to create(Period) test");
	}
	
	public void testUpdateUniversity() {
		logger.info("----> BEGIN access to update(University) test");

		//Create a default University
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university.getId());
		
		//Create new UniversityInfo object
		UniversityInfo universityInfo = new UniversityInfo();
		universityInfo.setId(university.getId());
		universityInfo.setName(testUtility.unique("testUniversity"));
		universityInfo.setShortcut(testUtility.unique("testU"));
		universityInfo.setOwnerName("Administrator");
		universityInfo.setEnabled(true);
		universityInfo.setDescription("This is a test University at "+testUtility.unique("time"));
		if (university.getUniversityType().compareTo(UniversityType.MISC) == 0) {
			universityInfo.setUniversityType(UniversityType.UNIVERSITY);
		} else {
			universityInfo.setUniversityType(UniversityType.MISC);
		}
		
		// Check
		assertTrue(universityInfo.getId().longValue() == university.getId().longValue());
		assertFalse(universityInfo.getName().compareTo(university.getName()) == 0);
		assertFalse(universityInfo.getShortcut().compareTo(university.getShortcut()) == 0);
		assertFalse(universityInfo.getDescription().compareTo(university.getDescription()) == 0);
		assertFalse(universityInfo.getUniversityType().getValue().intValue() == university.getUniversityType().getValue().intValue());

		//Synchronize with Database
		flush();
		
		//Update University
		universityService.update(universityInfo);

		//Check
		assertTrue(universityInfo.getId().longValue() == university.getId().longValue());
		assertTrue(universityInfo.getName().compareTo(university.getName()) == 0);
		assertTrue(universityInfo.getShortcut().compareTo(university.getShortcut()) == 0);
		assertTrue(universityInfo.getDescription().compareTo(university.getDescription()) == 0);
		assertTrue(universityInfo.getUniversityType().getValue().intValue() == university.getUniversityType().getValue().intValue());
		
		//Synchronize with Database
		flush();
		
		logger.info("----> END access to update(University) test");
	}
	
	public void testUpdatePeriod () {
		logger.info("----> BEGIN access to update(Period) test");
		
		//Create a default Period
		Period period = testUtility.createUniquePeriodInDB();
		assertNotNull(period.getId());
		
		//Create new PeriodInfo object
		PeriodInfo periodInfo = new PeriodInfo();
		periodInfo.setId(period.getId());
		periodInfo.setName(testUtility.unique("testPeriod"));
		periodInfo.setDescription("This is a test Period at "+testUtility.unique("time"));
		periodInfo.setUniversityId(testUtility.createUniqueUniversityInDB().getId());
		periodInfo.setStartdate(new Date(new GregorianCalendar(2008, 4, 1).getTimeInMillis()));
		periodInfo.setEnddate(new Date(new GregorianCalendar(2008, 9, 30).getTimeInMillis()));
		
		// Check
		assertTrue(periodInfo.getId().longValue() == period.getId().longValue());
		assertFalse(periodInfo.getName().compareTo(period.getName()) == 0);
		assertFalse(periodInfo.getDescription().compareTo(period.getDescription()) == 0);
		assertFalse(periodInfo.getUniversityId().longValue() == period.getUniversity().getId().longValue());
		assertFalse(periodInfo.getStartdate().getTime() == period.getStartdate().getTime());
		assertFalse(periodInfo.getEnddate().getTime() == period.getEnddate().getTime());

		//Synchronize with Database
		flush();
		
		//Update University
		this.getUniversityService().update(periodInfo);

		//Check
		assertTrue(periodInfo.getId().longValue() == period.getId().longValue());
		assertTrue(periodInfo.getName().compareTo(period.getName()) == 0);
		assertTrue(periodInfo.getDescription().compareTo(period.getDescription()) == 0);
		assertTrue(periodInfo.getUniversityId().longValue() == period.getUniversity().getId().longValue());
		assertTrue(periodInfo.getStartdate().getTime() == period.getStartdate().getTime());
		assertTrue(periodInfo.getEnddate().getTime() == period.getEnddate().getTime());
		
		//Synchronize with Database
		flush();
		
		logger.info("----> END access to update(Period) test");
	}
	
	public void testRemoveUniversity() {
		logger.info("----> BEGIN access to removeUniversity test");
		
		//Create a University
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university.getId());
		
		//Save UniversityID
		Long id = university.getId();
		
		//Synchronize with Database
		flush();

		//Remove University
		universityService.removeUniversity(id);
		
		//Synchronize with Database
		flush();
		
		//Try to load University again
		UniversityDao universityDao = (UniversityDao) this.applicationContext.getBean("universityDao");
		University university2 = universityDao.load(id);
		assertNull(university2);

		logger.info("----> END access to removeUniversity test");		
	}
	
	public void testRemovePeriod() {
		logger.info("----> BEGIN access to removePeriod test");
		
		//Create a Period
		Period period = testUtility.createUniquePeriodInDB();
		assertNotNull(period.getId());
		
		//Save UniversityID
		Long periodId = period.getId();
		
		//Synchronize with Database
		flush();

		//Remove Period
		this.getUniversityService().removePeriod(periodId);
		
		//Synchronize with Database
		flush();
		
		//Try to load Period again
		PeriodDao periodDao = (PeriodDao) this.applicationContext.getBean("periodDao");
		Period periodTest = periodDao.load(periodId);
		assertNull(periodTest);

		logger.info("----> END access to removePeriod test");		
	}
	
	public void testFindUniversity() {
		logger.info("----> BEGIN access to findUniversity test");
		
		//Create a University
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university.getId());
		
		//Synchronize with Database
		flush();

		//Find University
		UniversityInfo universityInfo = universityService.findUniversity(university.getId());
		
		assertEquals(university.getId(),universityInfo.getId());

		logger.info("----> END access to findUniversity test");		
	}
	
	public void testFindPeriod() {
		logger.info("----> BEGIN access to findPeriod test");
		
		//Create a Period
		Period period = testUtility.createUniquePeriodInDB();
		assertNotNull(period.getId());
		
		//Synchronize with Database
		flush();

		//Find University
		PeriodInfo periodInfo = this.getUniversityService().findPeriod(period.getId());
		
		assertEquals(period.getId(),periodInfo.getId());

		logger.info("----> END access to findPeriod test");		
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testFindAllUniversities() {
		logger.info("----> BEGIN access to findAllUniversities test");
		
		// Create complete Universities
		List<University> universities = new ArrayList<University>();
		for (int i = 0; i < 3; i++) {
			universities.add(testUtility.createUniqueUniversityInDB());
		}
		
		//Synchronize with Database
		flush();

		//Find all University
		List universityInfos = universityService.findAllUniversities();
		
		assertEquals(universities.size(), universityInfos.size());

		Iterator iterator = null;
		UniversityInfo universityInfo = null;
		int count = 0;
		for (University uni : universities) {
			iterator = universityInfos.iterator();
			while (iterator.hasNext()) {
				universityInfo = (UniversityInfo) iterator.next();
				if (universityInfo.getId() == uni.getId()) {
					count++;
				}
			}
		}
		assertEquals(universities.size(), count);
		
		logger.info("----> END access to findAllUniversities test");		
	}
	
	public void testFindPeriodsByUniversity () {
		logger.info("----> BEGIN access to findPeriodsByUniversity test");
		
		//Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		//Create Periods with university
		Period period1 = testUtility.createUniquePeriodInDB();
		period1.setUniversity(university);
		university.getPeriods().add(period1);
		
		Period period2 = testUtility.createUniquePeriodInDB();
		period2.setUniversity(university);
		university.getPeriods().add(period2);
		
		PeriodDao periodDao = (PeriodDao) this.getApplicationContext().getBean("periodDao");
		periodDao.update(period1);
		periodDao.update(period2);
		
		//Test
		List<Period> periods = this.getUniversityService().findPeriodsByUniversity(university.getId());
		assertNotNull(periods);
		assertEquals(2, periods.size());
		assertEquals(period1.getName(), periods.get(0).getName());
		assertEquals(period2.getName(), periods.get(1).getName());
		
		logger.info("----> END access to findPeriodsByUniversity test");
	}
	
	public void testFindActivePeriodByUniversity () {
		logger.info("----> BEGIN access to findActivePeriodByUniversity test");
		
		//Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		//Create Periods with university
		Period period1 = testUtility.createUniquePeriodInDB();
		period1.setUniversity(university);
		period1.setStartdate(new Date(new GregorianCalendar(2004, 10, 1).getTimeInMillis()));
		period1.setEnddate(new Date(new GregorianCalendar(2005, 3, 31).getTimeInMillis()));
		university.getPeriods().add(period1);
		
		Period period2 = testUtility.createUniquePeriodInDB();
		period2.setUniversity(university);
		university.getPeriods().add(period2);
		
		PeriodDao periodDao = (PeriodDao) this.getApplicationContext().getBean("periodDao");
		periodDao.update(period1);
		periodDao.update(period2);
		
		//Test
		PeriodInfo periodInfo = this.getUniversityService().findActivePeriodByUniversity(university.getId());
		assertNotNull(periodInfo);
		assertEquals(period2.getName(), periodInfo.getName());
		
		logger.info("----> END access to findActivePeriodByUniversity test");
	}
}