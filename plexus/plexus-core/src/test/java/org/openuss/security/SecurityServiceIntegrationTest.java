// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate SecurityService class.
 * @see org.openuss.security.SecurityService
 */
/**
 * @author Ingo Dueppe
 *
 */
public class SecurityServiceIntegrationTest extends SecurityServiceIntegrationTestBase {
	
	private TestUtility testUtility;
	
	public void testSaveLoadAndRemoveUser() {
		User user = testUtility.createDefaultUser();
		
		securityService.createUser(user);
		assertNotNull(user.getId());
		securityService.saveUser(user);
		
		User user2 = securityService.getUserByName(user.getUsername());
		assertEquals(user, user2);
		
		securityService.removeUser(user);	
	}
	
	public void testIsNonExistingUserName() {
		User user = testUtility.createDefaultUser();
		
		assertTrue(securityService.isValidUserName(null, user.getUsername()));
		securityService.createUser(user);
		assertFalse(securityService.isValidUserName(null, user.getUsername()));
		assertTrue(securityService.isValidUserName(user, user.getUsername()));
		
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}
	
	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
}
