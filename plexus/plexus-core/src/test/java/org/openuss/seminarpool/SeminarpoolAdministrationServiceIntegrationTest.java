// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.sql.Timestamp;

import org.openuss.lecture.AccessType;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Period;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate SeminarpoolAdministrationService class.
 * @see org.openuss.seminarpool.SeminarpoolAdministrationService
 */
public class SeminarpoolAdministrationServiceIntegrationTest extends SeminarpoolAdministrationServiceIntegrationTestBase {
	public void testCreate () {
		logger.debug("----> BEGIN access to create test <---- ");
		
		User user = testUtility.createUniqueUserInDB();
		
		flush();
		
		//Create CourseInfo
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

		
		logger.debug("----> END access to create test <---- ");
	}
}