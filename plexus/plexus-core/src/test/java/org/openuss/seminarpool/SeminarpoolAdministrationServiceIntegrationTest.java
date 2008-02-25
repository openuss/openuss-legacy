// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.sql.Timestamp;

import org.openuss.TestUtility;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseServiceException;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Period;
import org.openuss.lecture.University;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate SeminarpoolAdministrationService class.
 * @see org.openuss.seminarpool.SeminarpoolAdministrationService
 */
public class SeminarpoolAdministrationServiceIntegrationTest extends SeminarpoolAdministrationServiceIntegrationTestBase {
	

	
	public void testCreateSeminarpool () {
		logger.debug("----> BEGIN access to create test <---- ");
		
		User user = testUtility.createUniqueUserInDB();
		University university= testUtility.createUniqueUniversityInDB();
		
		flush();
		
		//Create SeminarpoolInfo
		SeminarpoolInfo seminarpoolInfo = new SeminarpoolInfo();
		seminarpoolInfo.setName(testUtility.unique("name"));
		seminarpoolInfo.setDescription(testUtility.unique("description"));
		seminarpoolInfo.setMaxSeminarAllocations(1);
		seminarpoolInfo.setPassword(testUtility.unique("passwort"));
		seminarpoolInfo.setPriorities(3);
		seminarpoolInfo.setRegistrationStartTime(new Timestamp(123456L));
		seminarpoolInfo.setRegistrationEndTime(new Timestamp(123456L));
		seminarpoolInfo.setSeminarpoolStatus(SeminarpoolStatus.REVIEWPHASE);
		seminarpoolInfo.setShortcut(testUtility.unique("shortcut"));
		seminarpoolInfo.setUniversityId(university.getId());
		
		
		//Test
		Long seminarpoolId = this.getSeminarpoolAdministrationService().createSeminarpool(seminarpoolInfo, user.getId());
		assertNotNull(seminarpoolId);
		
		SeminarpoolDao seminarpoolDao = (SeminarpoolDao) this.getApplicationContext().getBean("seminarpoolDao");
		Seminarpool seminarpoolTest = seminarpoolDao.load(seminarpoolId);
		assertNotNull(seminarpoolTest);
		assertEquals(seminarpoolId, seminarpoolTest.getId());
		assertEquals(seminarpoolInfo.getDescription(), seminarpoolTest.getDescription());
		assertEquals(seminarpoolInfo.getShortcut(), seminarpoolTest.getShortcut());
		assertEquals(seminarpoolInfo.getPassword(), seminarpoolTest.getPassword());
		assertEquals(seminarpoolInfo.getName(), seminarpoolTest.getName());
		assertEquals(seminarpoolInfo.getMaxSeminarAllocations(), seminarpoolTest.getMaxSeminarAllocations());
		assertEquals(seminarpoolInfo.getPriorities(), seminarpoolTest.getPriorities());
		assertEquals(seminarpoolInfo.getSeminarpoolStatus(), seminarpoolTest.getSeminarpoolStatus());
		assertEquals(seminarpoolInfo.getRegistrationStartTime(), seminarpoolTest.getRegistrationStartTime());
		assertEquals(seminarpoolInfo.getRegistrationEndTime(), seminarpoolTest.getRegistrationEndTime());
		assertEquals(seminarpoolInfo.getUniversityId(), university.getId());

		
		logger.debug("----> END access to create test <---- ");
	}
	
	public void testUpdateCourse () {
		logger.debug("----> BEGIN access to updateSeminarpool test <---- ");
		
		
		
		
		University university= testUtility.createUniqueUniversityInDB();
		
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		assertNotNull(seminarpool);
		
		
		flush();
		
		//Create SeminarpoolInfo
		SeminarpoolInfo seminarpoolInfo = new SeminarpoolInfo();
		seminarpoolInfo.setId(seminarpool.getId());
		seminarpoolInfo.setName(testUtility.unique("name"));
		seminarpoolInfo.setDescription(testUtility.unique("description"));
		seminarpoolInfo.setMaxSeminarAllocations(2);
		seminarpoolInfo.setPassword(testUtility.unique("passwort"));
		seminarpoolInfo.setPriorities(4);
		seminarpoolInfo.setRegistrationStartTime(new Timestamp(123456L));
		seminarpoolInfo.setRegistrationEndTime(new Timestamp(123456L));
		seminarpoolInfo.setSeminarpoolStatus(SeminarpoolStatus.REVIEWPHASE);
		seminarpoolInfo.setShortcut(testUtility.unique("shortcut"));
		seminarpoolInfo.setUniversityId(university.getId());
		
		// Test
		this.getSeminarpoolAdministrationService().updateSeminarpool(seminarpoolInfo);
		
		// Load Course
		SeminarpoolDao seminarpoolDao = (SeminarpoolDao) this.getApplicationContext().getBean("seminarpoolDao");
		Seminarpool seminarpoolTest = seminarpoolDao.load(seminarpoolInfo.getId());
		assertNotNull(seminarpoolTest);
		assertEquals(seminarpoolInfo.getDescription(), seminarpoolTest.getDescription());
		assertEquals(seminarpoolInfo.getShortcut(), seminarpoolTest.getShortcut());
		assertEquals(seminarpoolInfo.getPassword(), seminarpoolTest.getPassword());
		assertEquals(seminarpoolInfo.getName(), seminarpoolTest.getName());
		assertEquals(seminarpoolInfo.getMaxSeminarAllocations(), seminarpoolTest.getMaxSeminarAllocations());
		assertEquals(seminarpoolInfo.getPriorities(), seminarpoolTest.getPriorities());
		assertEquals(seminarpoolInfo.getSeminarpoolStatus(), seminarpoolTest.getSeminarpoolStatus());
		assertEquals(seminarpoolInfo.getRegistrationStartTime(), seminarpoolTest.getRegistrationStartTime());
		assertEquals(seminarpoolInfo.getRegistrationEndTime(), seminarpoolTest.getRegistrationEndTime());
		assertEquals(seminarpoolInfo.getUniversityId(), university.getId());
		
		
		
		logger.debug("----> END access to updateSeminarpool test <---- ");
	}
	
	public void testRemoveSeminarpool () {
		logger.debug("----> BEGIN access to removeCourse test <---- ");
		
		
		//Create Seminarpools
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
	

		
		//Synchronize with DB
		flush();
		
		//Test
		SeminarpoolDao seminarpoolDao = (SeminarpoolDao) this.getApplicationContext().getBean("seminarpoolDao");
		Seminarpool seminarpollTest = seminarpoolDao.load(seminarpool.getId());
		assertNotNull(seminarpollTest);
		
		this.getSeminarpoolAdministrationService().removeSeminarpool(seminarpool.getId());
		seminarpollTest = seminarpoolDao.load(seminarpool.getId());
		assertNull(seminarpollTest);
	
		
		logger.debug("----> END access to removeCourse test <---- ");
	}
	
}