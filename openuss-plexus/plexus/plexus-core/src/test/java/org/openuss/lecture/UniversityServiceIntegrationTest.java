// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		universityInfo.setDescription("This is a test University");
		universityInfo.setUniversityType(UniversityType.MISC);
		
		//Create a User as Owner
		User owner = testUtility.createUserInDB();
		
		//Create Entity
		Long universityId = universityService.create(universityInfo, owner.getId());
		assertNotNull(universityId);
		
		//Synchronize with Database
		flush();

		logger.info("----> END access to create(University) test");
	}
	
	public void testUpdateUniversity() {
		logger.info("----> BEGIN access to update(University) test");

		//Create a default University
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		assertNotNull(university.getId());
		
		//Create new UniversityInfo object
		UniversityInfo universityInfo = new UniversityInfo();
		universityInfo.setId(university.getId());
		universityInfo.setName(testUtility.unique("testUniversity"));
		universityInfo.setShortcut(testUtility.unique("testU"));
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
	
	public void testRemoveUniversity() {
		logger.info("----> BEGIN access to removeUniversity test");
		
		//Create a University
		University university = testUtility.createDefaultUniversityWithDefaultUser();
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
	
}