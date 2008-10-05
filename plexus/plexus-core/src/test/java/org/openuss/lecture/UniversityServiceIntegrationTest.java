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
import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate UniversityService class.
 * 
 * @see org.openuss.lecture.UniversityService
 * @author Ron Haus
 * @author Florian Dondorf
 */
@SuppressWarnings( { "unchecked" })
public class UniversityServiceIntegrationTest extends UniversityServiceIntegrationTestBase {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UniversityServiceIntegrationTest.class);

	public void testCreateUniversity() {
		logger.info("----> BEGIN access to create(University) test");

		// Create new UniversityInfo object
		UniversityInfo universityInfo = new UniversityInfo();
		universityInfo.setName(testUtility.unique("testUniversity"));
		universityInfo.setShortcut(testUtility.unique("testU"));
		universityInfo.setOwnerName("Administrator");
		universityInfo.setEnabled(true);
		universityInfo.setDescription("This is a test University");
		universityInfo.setUniversityType(UniversityType.UNIVERSITY);

		// Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();
		
		// Synchronize with Database
		flush();
		
		// Test Security
		testUtility.createUserSecureContext();
		try {
			universityService.createUniversity(universityInfo, owner.getId());
			fail("AccessDeniedException should have been thrown - only for ROLE_ADMIN");
		} catch (AccessDeniedException ade) {
			;
		} finally {
			testUtility.destroySecureContext();
		}

		// Aspect test
		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		Desktop desktop = Desktop.Factory.newInstance();
		desktop.setUser(owner);
		desktopDao.create(desktop);
		Desktop desktopTest = desktopDao.findByUser(owner);
		assertNotNull(desktopTest);
		assertEquals(0, desktopTest.getUniversities().size());
		
		// Create Entity
		testUtility.createAdminSecureContext();
		Long universityId = universityService.createUniversity(universityInfo, owner.getId());
		assertNotNull(universityId);
		
		UniversityDao universityDao = (UniversityDao) this.getApplicationContext().getBean("universityDao");
		University universityTest = universityDao.load(universityId);
		assertEquals(1, universityTest.getDepartments().size());
		assertEquals(1, universityTest.getPeriods().size());
		assertEquals(1, universityTest.getMembership().getMembers().size());

		// Synchronize with Database
		flush();
		
		// Aspect test
		desktopTest = desktopDao.findByUser(owner);
		assertNotNull(desktopTest);
		assertEquals(1, desktopTest.getUniversities().size());
		
		testUtility.destroySecureContext();
		logger.info("----> END access to create(University) test");
	}

	public void testCreatePeriod() {
		logger.info("----> BEGIN access to create(Period) test");

		// Create university
		University university = testUtility.createUniqueUniversityInDB();
		assertEquals(1, university.getPeriods().size());
		flush();

		int sizeBefore = university.getPeriods().size();
		
		// Create Startdate
		Calendar cal = new GregorianCalendar();
		cal.set(2007, 10, 1);
		Date startdate = new Date(cal.getTimeInMillis());

		// Create Enddate
		cal = new GregorianCalendar();
		cal.set(2008, 3, 31);
		Date enddate = new Date(cal.getTimeInMillis());

		// Create Period
		PeriodInfo periodInfo = new PeriodInfo();
		periodInfo.setUniversityId(university.getId());
		periodInfo.setName("WS 07/08");
		periodInfo.setDescription("A new period");
		periodInfo.setStartdate(startdate);
		periodInfo.setEnddate(enddate);

		// Test
		Long periodId = this.getUniversityService().createPeriod(periodInfo);
		assertNotNull(periodId);

		PeriodDao periodDao = (PeriodDao) this.getApplicationContext().getBean("periodDao");
		Period period = periodDao.load(periodId);
		assertNotNull(period);
		assertEquals(periodId, period.getId());
		assertEquals(periodInfo.getName(), period.getName());

		assertEquals(sizeBefore+1, period.getUniversity().getPeriods().size());

		logger.info("----> END access to create(Period) test");
	}

	public void testUpdateUniversity() {
		logger.info("----> BEGIN access to update(University) test");

		// Create a default University
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university.getId());

		// Create new UniversityInfo object
		UniversityInfo universityInfo = new UniversityInfo();
		universityInfo.setId(university.getId());
		universityInfo.setName(testUtility.unique("testUniversity"));
		universityInfo.setShortcut(testUtility.unique("testU"));
		universityInfo.setOwnerName("Administrator");
		universityInfo.setEnabled(true);
		universityInfo.setDescription("This is a test University at " + testUtility.unique("time"));
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
		assertFalse(universityInfo.getUniversityType().getValue().intValue() == university.getUniversityType()
				.getValue().intValue());

		// Synchronize with Database
		flush();

		// Update University
		universityService.update(universityInfo);

		// Check
		assertTrue(universityInfo.getId().longValue() == university.getId().longValue());
		assertTrue(universityInfo.getName().compareTo(university.getName()) == 0);
		assertTrue(universityInfo.getShortcut().compareTo(university.getShortcut()) == 0);
		assertTrue(universityInfo.getDescription().compareTo(university.getDescription()) == 0);
		assertTrue(universityInfo.getUniversityType().getValue().intValue() == university.getUniversityType()
				.getValue().intValue());

		// Synchronize with Database
		flush();

		logger.info("----> END access to update(University) test");
	}

	public void testUpdatePeriod() {
		logger.info("----> BEGIN access to update(Period) test");

		// Create a default Period
		Period period = testUtility.createUniquePeriodInDB();
		assertNotNull(period.getId());

		// Create new PeriodInfo object
		PeriodInfo periodInfo = new PeriodInfo();
		periodInfo.setId(period.getId());
		periodInfo.setUniversityId(period.getUniversity().getId());
		periodInfo.setName(testUtility.unique("testPeriod"));
		periodInfo.setDescription("This is a test Period at " + testUtility.unique("time"));
		periodInfo.setStartdate(new Date(new GregorianCalendar(2008, 4, 1).getTimeInMillis()));
		periodInfo.setEnddate(new Date(new GregorianCalendar(2008, 9, 30).getTimeInMillis()));

		// Check
		assertTrue(periodInfo.getId().longValue() == period.getId().longValue());
		assertFalse(periodInfo.getName().compareTo(period.getName()) == 0);
		assertFalse(periodInfo.getDescription().compareTo(period.getDescription()) == 0);
		assertTrue(periodInfo.getUniversityId().longValue() == period.getUniversity().getId().longValue());
		assertFalse(periodInfo.getStartdate().getTime() == period.getStartdate().getTime());
		assertFalse(periodInfo.getEnddate().getTime() == period.getEnddate().getTime());

		// Synchronize with Database
		flush();

		// Update University
		this.getUniversityService().update(periodInfo);

		// Check
		assertTrue(periodInfo.getId().longValue() == period.getId().longValue());
		assertTrue(periodInfo.getName().compareTo(period.getName()) == 0);
		assertTrue(periodInfo.getDescription().compareTo(period.getDescription()) == 0);
		assertTrue(periodInfo.getUniversityId().longValue() == period.getUniversity().getId().longValue());
		assertTrue(periodInfo.getStartdate().getTime() == period.getStartdate().getTime());
		assertEquals(truncate(periodInfo.getEnddate()), truncate(period.getEnddate()));

		
		// Synchronize with Database
		flush();

		logger.info("----> END access to update(Period) test");
	}
	
	private Date truncate(Date date) {
		return DateUtils.truncate(date, Calendar.DATE);
	}

	public void testRemovePeriod() {
		logger.info("----> BEGIN access to removePeriod test");

		// Create a Period
		University universityTest = testUtility.createUniqueUniversityInDB();
		Period period = testUtility.createUniqueActivePeriodInDB(universityTest);
		assertNotNull(period.getId());

		// Get University
		University university = period.getUniversity();
		assertNotNull(university);
		assertEquals(2, university.getPeriods().size());
		
		// Save PeriodID
		Long periodId = period.getId();

		// Synchronize with Database
		flush();

		// Remove Period
		this.getUniversityService().removePeriod(periodId);

		// Synchronize with Database
		flush();

		// Try to load Period again
		PeriodDao periodDao = (PeriodDao) this.applicationContext.getBean("periodDao");
		Period periodTest = periodDao.load(periodId);
		assertNull(periodTest);
		
		assertEquals(1, university.getPeriods().size());

		// Create an Course (this includes creating of a Period)
		Course course = testUtility.createUniqueCourseInDB();
		Period period2 = course.getPeriod();

		try {
			this.getUniversityService().removePeriod(period2.getId());
			fail("Exception should have been thrown since the Period contains still a Course");
		} catch (UniversityServiceException iae) {
			;
		}

		logger.info("----> END access to removePeriod test");
	}

	public void testRemovePeriodAndCourses() {
		logger.info("----> BEGIN access to removePeriod test");

		// Create a Period
		University universityTest = testUtility.createUniqueUniversityInDB();
		Period period = testUtility.createUniqueActivePeriodInDB(universityTest);
		assertNotNull(period.getId());

		// Save UniversityID
		Long periodId = period.getId();

		// Synchronize with Database
		flush();

		// Remove Period
		this.getUniversityService().removePeriod(periodId);

		// Synchronize with Database
		flush();

		// Try to load Period again
		PeriodDao periodDao = (PeriodDao) this.applicationContext.getBean("periodDao");
		Period periodTest = periodDao.load(periodId);
		assertNull(periodTest);

		logger.info("----> END access to removePeriod test");
	}

	public void testFindUniversity() {
		logger.info("----> BEGIN access to findUniversity test");

		// Create a University
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university.getId());

		// Synchronize with Database
		flush();

		// Find University
		UniversityInfo universityInfo = universityService.findUniversity(university.getId());

		assertEquals(university.getId(), universityInfo.getId());

		logger.info("----> END access to findUniversity test");
	}

	public void testFindPeriod() {
		logger.info("----> BEGIN access to findPeriod test");

		// Create a Period
		University universityTest = testUtility.createUniqueUniversityInDB();
		Period period = testUtility.createUniqueActivePeriodInDB(universityTest);
		assertNotNull(period.getId());

		// Synchronize with Database
		flush();

		// Find University
		PeriodInfo periodInfo = this.getUniversityService().findPeriod(period.getId());

		assertEquals(period.getId(), periodInfo.getId());

		logger.info("----> END access to findPeriod test");
	}

	public void testFindAllUniversities() {
		logger.info("----> BEGIN access to findAllUniversities test");

		// Count universities
		UniversityDao universityDao = (UniversityDao) this.getApplicationContext().getBean("universityDao");
		int countUniversities = universityDao.loadAll().size();
		
		// Create complete Universities
		List<University> universities = new ArrayList<University>();
		for (int i = 0; i < 3; i++) {
			universities.add(testUtility.createUniqueUniversityInDB());
			countUniversities++;
		}

		// Synchronize with Database
		flush();

		// Find all University
		List universityInfos = universityService.findAllUniversities();

		assertEquals(countUniversities, universityInfos.size());

		/*
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
		*/

		logger.info("----> END access to findAllUniversities test");
	}

	public void testFindPeriodsByUniversity() {
		logger.info("----> BEGIN access to findPeriodsByUniversity test");

		// Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		int sizeBefore = university.getPeriods().size();

		// Create Periods with university
		Period period1 = testUtility.createUniqueActivePeriodInDB(university);

		Period period2 = testUtility.createUniqueActivePeriodInDB(university);
		
		Period period3 = testUtility.createUniqueInactivePeriodInDB();
		period3.setUniversity(university);
		university.add(period3);

		PeriodDao periodDao = (PeriodDao) this.getApplicationContext().getBean("periodDao");
		periodDao.update(period1);
		periodDao.update(period2);
		periodDao.update(period3);

		// Test
		List<PeriodInfo> periods = this.getUniversityService().findPeriodsByUniversity(university.getId());
		assertNotNull(periods);
		assertEquals(sizeBefore+3, periods.size());

		logger.info("----> END access to findPeriodsByUniversity test");
	}

	public void testFindPeriodsByUniversityAndActivation() {
		logger.info("----> BEGIN access to findActivePeriodByUniversity test");

		// Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();

		int sizeBefore = university.getPeriods().size();
		
		List<Period> passivePeriods = new ArrayList<Period>();
		for (Period period : university.getPeriods()) {
			if (!period.isActive()) {
				passivePeriods.add(period);
			}
		}
		int passiveSizeBefore = passivePeriods.size();
		
		// Create Periods with university
		Period period1 = testUtility.createUniqueActivePeriodInDB(university);

		Period period2 = testUtility.createUniqueActivePeriodInDB(university);

		Period period3 = testUtility.createUniqueInactivePeriodInDB();
		period3.setUniversity(university);
		university.add(period3);
		
		PeriodDao periodDao = (PeriodDao) this.getApplicationContext().getBean("periodDao");
		periodDao.update(period1);
		periodDao.update(period2);
		periodDao.update(period3);

		// Test
		List<PeriodInfo> periodInfos = this.getUniversityService().findPeriodsByUniversityAndActivation(university.getId(), true);
		assertNotNull(periodInfos);
		assertEquals(sizeBefore+2, periodInfos.size());
		
		periodInfos = this.getUniversityService().findPeriodsByUniversityAndActivation(university.getId(), false);
		assertNotNull(periodInfos);
		assertEquals(passiveSizeBefore+1, periodInfos.size());

		logger.info("----> END access to findActivePeriodByUniversity test");
	}

	public void testFindUniversitiesByUserAndEnabled() {
		logger.info("----> BEGIN access to findUniversitiesByUser test");

		// Create User
		User user = testUtility.createUniqueUserInDB();

		// Create Universities
		University university1 = testUtility.createUniqueUniversityInDB();
		university1.getMembership().getMembers().add(user);
		university1.setOwnerName(user.getName());

		testUtility.createUniqueUniversityInDB();

		University university3 = testUtility.createUniqueUniversityInDB();
		university3.getMembership().getMembers().add(user);
		university3.setOwnerName(user.getName());

		flush();

		// Test
		List universitiesByUser = this.getUniversityService().findUniversitiesByMemberAndEnabled(user.getId(), true);
		assertNotNull(universitiesByUser);
		assertEquals(2, universitiesByUser.size());
		assertEquals(university1.getName(), ((UniversityInfo) universitiesByUser.get(0)).getName());
		assertEquals(university3.getName(), ((UniversityInfo) universitiesByUser.get(1)).getName());

		logger.info("----> END access to findUniversitiesByUser test");
	}

	public void testFindUniversitiesByTypeAndEnabled() {
		logger.info("----> BEGIN access to findUniversitiesByType test");

		// Count univeristies by type and enabled
		List<UniversityInfo> allUniversities = this.getUniversityService().findAllUniversities();
		List<UniversityInfo> enabledUniversities = new ArrayList<UniversityInfo>();
		List<UniversityInfo> enabledCompanies = new ArrayList<UniversityInfo>();
		List<UniversityInfo> disabledUniversities = new ArrayList<UniversityInfo>();
		List<UniversityInfo> disabledCompanies = new ArrayList<UniversityInfo>();
		for (UniversityInfo university : allUniversities) {
			if (university.isEnabled()) {
				if (university.getUniversityType() == UniversityType.UNIVERSITY) {
					enabledUniversities.add(university);
				}
				else if (university.getUniversityType() == UniversityType.COMPANY) {
					enabledCompanies.add(university);
				}
			}
			else {
				if (university.getUniversityType() == UniversityType.UNIVERSITY) {
					disabledUniversities.add(university);
				}
				else if (university.getUniversityType() == UniversityType.COMPANY) {
					disabledCompanies.add(university);
				}
			}
		}
		int enabledUniversitiesCount = enabledUniversities.size();
		int enabledCompaniesiesCount = enabledCompanies.size();
		int disabledCompaniesCount = disabledCompanies.size();
		
		// Create Universities
		University university1 = testUtility.createUniqueUniversityInDB();
		university1.setUniversityType(UniversityType.UNIVERSITY);
		university1.setEnabled(true);
		enabledUniversitiesCount++;

		University university2 = testUtility.createUniqueUniversityInDB();
		university2.setUniversityType(UniversityType.COMPANY);
		university2.setEnabled(true);
		enabledCompaniesiesCount++;

		University university3 = testUtility.createUniqueUniversityInDB();
		university3.setUniversityType(UniversityType.MISC);
		university3.setEnabled(true);

		University university4 = testUtility.createUniqueUniversityInDB();
		university4.setUniversityType(UniversityType.UNIVERSITY);
		university4.setEnabled(false);

		University university5 = testUtility.createUniqueUniversityInDB();
		university5.setUniversityType(UniversityType.COMPANY);
		university5.setEnabled(true);
		enabledCompaniesiesCount++;

		University university6 = testUtility.createUniqueUniversityInDB();
		university6.setUniversityType(UniversityType.COMPANY);
		university6.setEnabled(false);
		disabledCompaniesCount++;

		flush();

		// Test
		List<UniversityInfo> universities = this.getUniversityService().findUniversitiesByTypeAndEnabled(
				UniversityType.UNIVERSITY, true);
		assertNotNull(universities);
		assertEquals(enabledUniversitiesCount, universities.size());

		universities = this.getUniversityService().findUniversitiesByTypeAndEnabled(UniversityType.COMPANY, true);
		assertNotNull(universities);
		assertEquals(enabledCompaniesiesCount, universities.size());

		universities = this.getUniversityService().findUniversitiesByTypeAndEnabled(UniversityType.COMPANY, false);
		assertNotNull(universities);
		assertEquals(disabledCompaniesCount, universities.size());

		logger.info("----> END access to findUniversitiesByType test");
	}
	
	public void testRemoveUniversity() {
		logger.info("----> BEGIN access to removeUniversity test");
		
		// Create University without Institutes, CourseTypes and Courses
		University university = testUtility.createUniqueEmptyUniversityInDB();
		Long universityId = university.getId();
		assertNotNull(universityId);
		
		flush();
		
		// Remove University
		this.getUniversityService().removeUniversity(universityId);
		
		flush();

		// Test
		UniversityDao universityDao = (UniversityDao) this.getApplicationContext().getBean("universityDao");
		University university2 = universityDao.load(universityId);
		assertNull(university2);
		
		logger.info("----> END access to removeUniversity test");
	}

	public void testRemoveCompleteUniversityTree() {
		logger.info("----> BEGIN access to removeCompleteUniversityTree test");
		
		// Create University with Institutes, CourseTypes and Courses
		Course course = testUtility.createUniqueCourseInDB();
		University university = course.getCourseType().getInstitute().getDepartment().getUniversity();
		Long universityId = university.getId();
		assertNotNull(universityId);
		
		flush();

		// Create user secure context
		testUtility.createUserSecureContext();
		try {
			this.getUniversityService().removeCompleteUniversityTree(universityId);
			fail("AccessDeniedException should have been thrown.");
		} catch (AccessDeniedException ade) {
			;
		} finally {
			testUtility.destroySecureContext();
		}
		
		// Create admin secure context
		testUtility.createAdminSecureContext();
		
		flush();
		
		this.getUniversityService().removeCompleteUniversityTree(universityId);
		
		flush();

		// Test
		UniversityDao universityDao = (UniversityDao) this.getApplicationContext().getBean("universityDao");
		University university2 = universityDao.load(universityId);
		assertNull(university2);
		
		testUtility.destroySecureContext();
		logger.info("----> END access to removeCompleteUniversityTree test");
	}
	
//	public void testIsNoneExistingOrganisationShortcutByUniversity () {
//		logger.debug("----> BEGIN access to isNoneExistingOrganisationShortcutByUniversity test <---- ");
//		
//		//Create Secure Context
//		testUtility.createUserSecureContext();
//		
//		// Create Universities
//		University university1 = testUtility.createUniqueUniversityInDB();
//		University university2 = testUtility.createUniqueUniversityInDB();
//		flush();
//		
//		// Test
//		UniversityDao universityDao = (UniversityDao) this.getApplicationContext().getBean("universityDao");
//		Boolean result = this.getUniversityService().isNoneExistingOrganisationShortcutByUniversity(
//				universityDao.toUniversityInfo(university1), university1.getShortcut());
//		assertNotNull(result);
//		assertTrue(result);
//		
//		result = this.getUniversityService().isNoneExistingOrganisationShortcutByUniversity(
//				universityDao.toUniversityInfo(university1), testUtility.unique("uni"));
//		assertNotNull(result);
//		assertTrue(result);
//		
//		result = this.getUniversityService().isNoneExistingOrganisationShortcutByUniversity(
//				universityDao.toUniversityInfo(university1), university2.getShortcut());
//		assertNotNull(result);
//		assertFalse(result);
//		
//		logger.debug("----> END access to isNoneExistingOrganisationShortcutByUniversity test <---- ");
//	}
	
	public void testSetUniversityStatus() {
		logger.info("----> BEGIN access to setUniversityStatus test");

		// Create university
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university);
		assertTrue(university.isEnabled());

		// Synchronize with DB
		flush();

		testUtility.createUserSecureContext();
		try {
			this.getUniversityService().setUniversityStatus(university.getId(), false);
			fail("AccessDeniedException should have been thrown.");
			
		} catch (AccessDeniedException ade) {
			;
		} finally {
			testUtility.destroySecureContext();
		}
		
		testUtility.createAdminSecureContext();
		this.getUniversityService().setUniversityStatus(university.getId(), false);

		// Load university
		UniversityDao universityDao = (UniversityDao) this.getApplicationContext().getBean("universityDao");
		University universityTest = universityDao.load(university.getId());

		assertFalse(universityTest.isEnabled());
		testUtility.destroySecureContext();

		testUtility.createAdminSecureContext();
		this.getUniversityService().setUniversityStatus(university.getId(), true);

		// Load university
		universityDao = (UniversityDao) this.getApplicationContext().getBean("universityDao");
		University universityTest1 = universityDao.load(university.getId());

		assertTrue(universityTest1.isEnabled());
		testUtility.destroySecureContext();

		logger.info("----> END access to setUniversityStatus test");
	}
	
	public void testFindPeriodsByUniversityWithActiveCourses() {
		logger.info("----> BEGIN access to findPeriodsByUniversityWithActiveCourses test");

		// Create Universities
		University university1 = testUtility.createUniqueUniversityInDB();
		University university2 = testUtility.createUniqueUniversityInDB();
		
		// Create Periods
		testUtility.createUniqueActivePeriodInDB(university1);
		assertEquals(2, university1.getPeriods().size());
		
		testUtility.createUniqueActivePeriodInDB(university2);
		assertEquals(2, university2.getPeriods().size());
		
		// Create Departments
		Department department1 = testUtility.createUniqueDepartmentInDB();
		department1.setUniversity(university1);
		Department department2 = testUtility.createUniqueDepartmentInDB();
		department2.setUniversity(university2);
		
		// Create Institutes
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		institute1.setDepartment(department1);
		Institute institute2 = testUtility.createUniqueInstituteInDB();
		institute1.setDepartment(department2);
		
		// Create CourseTypes
		CourseType courseType1 = testUtility.createUniqueCourseTypeInDB();
		institute1.getCourseTypes().add(courseType1);
		CourseType courseType2 = testUtility.createUniqueCourseTypeInDB();
		institute2.getCourseTypes().add(courseType2);
		
		// Create Courses for university1
		Course course1 = testUtility.createUniqueCourseInDB();
		course1.setPeriod(university1.getPeriods().get(0));
		course1.getPeriod().add(course1);
		courseType1.getCourses().add(course1);
		Course course2 = testUtility.createUniqueCourseInDB();
		course2.setPeriod(university1.getPeriods().get(1));
		course2.getPeriod().add(course2);
		courseType1.getCourses().add(course2);
		
		// Test
		List<PeriodInfo> periods = this.getUniversityService().findPeriodsByUniversityWithCourses(university1.getId());
		assertNotNull(periods);
		assertEquals(2, periods.size());
		assertEquals(university1.getPeriods().get(0).getName(), periods.get(1).getName());
		assertEquals(university1.getPeriods().get(1).getName(), periods.get(0).getName());
		
		periods = this.getUniversityService().findPeriodsByUniversityWithCourses(university2.getId());
		assertNotNull(periods);
		assertEquals(0, periods.size());
		
		

		logger.info("----> END access to findPeriodsByUniversityWithActiveCourses test");
	}
	
	public void testFindPeriodsByInstituteWithCoursesOrActive() {
		logger.info("----> BEGIN access to findPeriodsByInstituteWithCoursesOrActive test");

		// Create University
		University university1 = testUtility.createUniqueUniversityInDB();
		testUtility.createUniqueUniversityInDB();
		
		// Create Periods
		testUtility.createUniqueActivePeriodInDB(university1);
		assertEquals(2, university1.getPeriods().size());
		
		Period period2 = testUtility.createUniqueInactivePeriodInDB();
		university1.add(period2);
		assertEquals(3, university1.getPeriods().size());
		
		testUtility.createUniqueActivePeriodInDB(university1);
		assertEquals(4, university1.getPeriods().size());
		
		// Create Departments
		Department department1 = testUtility.createUniqueDepartmentInDB();
		department1.setUniversity(university1);
		Department department2 = testUtility.createUniqueDepartmentInDB();
		department2.setUniversity(university1);
		
		// Create Institute
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		institute1.setDepartment(department1);
		
		// Create CourseType
		CourseType courseType1 = testUtility.createUniqueCourseTypeInDB();
		institute1.getCourseTypes().add(courseType1);	
		
		// Create Courses for university1
		Course course1 = testUtility.createUniqueCourseInDB();
		course1.setPeriod(university1.getPeriods().get(0));
		course1.getPeriod().add(course1);
		courseType1.getCourses().add(course1);
		Course course2 = testUtility.createUniqueCourseInDB();
		course2.setPeriod(university1.getPeriods().get(1));
		course2.getPeriod().add(course2);
		courseType1.getCourses().add(course2);
		
		// Test
		InstituteDao instituteDao = (InstituteDao) this.getApplicationContext().getBean("instituteDao");
		List<PeriodInfo> periods = this.getUniversityService().findPeriodsByInstituteWithCoursesOrActive(
				instituteDao.toInstituteInfo(institute1));
		assertNotNull(periods);
		assertEquals(3, periods.size());
		assertEquals(university1.getPeriods().get(0).getName(), periods.get(2).getName());
		assertEquals(university1.getPeriods().get(1).getName(), periods.get(0).getName());
		

		logger.info("----> END access to findPeriodsByInstituteWithCoursesOrActive test");
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml", 
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-resources.xml",
			"classpath*:applicationContext-aop.xml",
			"classpath*:applicationContext-events.xml",
			"classpath*:testContext.xml", 
			"classpath*:testSecurity.xml", 
			"classpath*:testDataSource.xml"};
	}

}