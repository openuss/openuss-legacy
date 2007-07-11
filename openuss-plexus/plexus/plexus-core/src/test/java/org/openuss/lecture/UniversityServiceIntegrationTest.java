// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.apache.log4j.Logger;
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
		universityInfo.setUniversityType(0);
		
		//Create a User
		User owner = testUtility.createUserInDB();
		
		//Create Entity
		Long userId = universityService.create(universityInfo, owner.getId());
		assertNotNull(userId);

		logger.info("----> END access to create(University) test");
	}
	
}