// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
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
		University university = testUtility.createUniqueUniversityInDB();
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
	
	@SuppressWarnings( { "unchecked" })
	public void testFindAllUniversities() {
		logger.info("----> BEGIN access to findAllUniversities test");
		
		// Create complete Universities
		List<University> universities = new ArrayList<University>();
		University university = null;
		Membership membership = null;
		UniversityDao universityDao = (UniversityDao) this.getApplicationContext().getBean("universityDao");
		for (int i = 0; i < 3; i++) {
			university = University.Factory.newInstance();
			university.setName(testUtility.unique("testUniversity"+i));
			university.setShortcut(testUtility.unique("testU"+i));
			university.setDescription("This is a test University"+i);
			membership = Membership.Factory.newInstance();
			membership.getMembers().add(testUtility.createUserInDB());
			university.setMembership(membership);
			universityDao.create(university);
			assertNotNull(university.getId());
			universities.add(university);
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
	
}