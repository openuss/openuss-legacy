// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.TestingAuthenticationToken;
import org.apache.log4j.Logger;
import org.openuss.lecture.University;

/**
 * JUnit Test for Spring Hibernate MembershipService class.
 * 
 * @see org.openuss.security.MembershipService
 * @author Ron Haus, Florian Dondorf
 */
public class MembershipServiceIntegrationTest extends MembershipServiceIntegrationTestBase {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MembershipServiceIntegrationTest.class);

	public void testSetOwner() {
		logger.info("----> BEGIN access to setOwner test");

		// Check for Exception
		try {
			membershipService.setOwner(-100L, -100L);
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {
		}
		;

		// Create University with DeafultUser as Owner
		University university = testUtility.createPersistUniversityWithDefaultUser();

		// Create a 2nd User
		User user1 = testUtility.createUserInDB();

		// Change Owner
		assertFalse(user1.getUsername().compareTo(university.getOwner().getUsername()) == 0);
		membershipService.setOwner(university.getId(), user1.getId());
		assertEquals(user1.getUsername(), university.getOwner().getUsername());

		logger.info("----> END access to setOwner test");
	}

	private static void createSecureContext(String roleName) {
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("principal", "credentials",
				new GrantedAuthority[] { new GrantedAuthorityImpl(roleName) });
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private static void destroySecureContext() {
		SecurityContextHolder.setContext(new SecurityContextImpl());
	}

}