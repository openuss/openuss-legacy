// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.List;

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
@SuppressWarnings({"unchecked"})
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
		University university = testUtility.createDefaultUniversityWithDefaultUser();

		// Create a 2nd User
		User user1 = testUtility.createUserInDB();

		// Change Owner
		assertFalse(user1.getUsername().compareTo(university.getOwner().getUsername()) == 0);
		membershipService.setOwner(university.getId(), user1.getId());
		assertEquals(user1.getUsername(), university.getOwner().getUsername());

		logger.info("----> END access to setOwner test");
	}

	public void testFindOwner() {
		logger.info("----> BEGIN access to findOwner test");

		// Create University with DeafultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();

		// Find Owner
		UserInfo ownerInfo = membershipService.findOwner(university.getId());
		assertNotNull(ownerInfo);

		logger.info("----> END access to findOwner test");
	}
	
	public void testAcceptAspirant() {
		logger.info("----> BEGIN access to acceptAspirant test");
		
		// Create University with DefaultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		
		// Create a 2nd User
		User user1 = testUtility.createUserInDB();
		
		// Try to accept the User
		try {
			membershipService.acceptAspirant(university.getId(), user1.getId());
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {};
		
		//Add User to Aspirants and accept after that
		university.getAspirants().add(user1);
		int sizeAspirantsBefore = university.getAspirants().size();
		int sizeMembersBefore = university.getMembers().size();
		membershipService.acceptAspirant(university.getId(), user1.getId());
		List aspirants = university.getAspirants();
		assertNotNull(aspirants);
		assertEquals(aspirants.size(),sizeAspirantsBefore-1);
		List members = university.getMembers();
		assertNotNull(members);
		assertEquals(members.size(),sizeMembersBefore+1);
		
		logger.info("----> END access to acceptAspirant test");
	}
	
	public void testRejectAspirant() {
		logger.info("----> BEGIN access to rejectAspirant test");
		
		// Create University with DefaultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		
		// Create a 2nd User
		User user1 = testUtility.createUserInDB();
		
		// Try to accept the User
		try {
			membershipService.rejectAspirant(university.getId(), user1.getId());
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {};
		
		//Add User to Aspirants and reject after that
		university.getAspirants().add(user1);
		int sizeAspirantsBefore = university.getAspirants().size();
		int sizeMembersBefore = university.getMembers().size();
		membershipService.rejectAspirant(university.getId(), user1.getId());
		List aspirants = university.getAspirants();
		assertNotNull(aspirants);
		assertEquals(aspirants.size(),sizeAspirantsBefore-1);
		List members = university.getMembers();
		assertNotNull(members);
		assertEquals(members.size(),sizeMembersBefore);
		
		logger.info("----> END access to rejectAspirant test");
	}
	
	public void testAddMember() {
		logger.info("----> BEGIN access to addMember test");
		
		// Create University with DefaultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		
		// Try to add Owner as an Member
		try {
			membershipService.addMember(university.getId(), university.getOwner().getId());
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {};
		
		// Create a 2nd User
		User user1 = testUtility.createUserInDB();
		
		// Try to add Aspirant as a Member
		try {
			university.getAspirants().add(user1);
			membershipService.addMember(university.getId(), user1.getId());
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {};

		//	Get List of Members
		List members = university.getMembers();
		assertNotNull(members);
		int sizeBefore = members.size();
		
		// Create a 3rd User
		User user2 = testUtility.createUserInDB();
		
		// Add a User
		membershipService.addMember(university.getId(), user2.getId());
		
		// Get List of Members again
		List members2 = university.getMembers();
		assertNotNull(members2);
		assertEquals(members2.size(),sizeBefore+1);
		
		// Try to add Member again
		try {
			membershipService.addMember(university.getId(), user2.getId());
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {};
		
		logger.info("----> END access to addMember test");
	}
	
	public void testAddAspirant() {
		logger.info("----> BEGIN access to addAspirant test");
		
		// Create University with DefaultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		
		// Try to add Owner as an Aspirant
		try {
			membershipService.addAspirant(university.getId(), university.getOwner().getId());
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {};
		
		// Create a 2nd User
		User user1 = testUtility.createUserInDB();
		
		// Try to add Member as an Aspirant
		try {
			university.getMembers().add(user1);
			membershipService.addAspirant(university.getId(), user1.getId());
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {};

		//	Get List of Aspirants
		List aspirants = university.getAspirants();
		assertNotNull(aspirants);
		int sizeBefore = aspirants.size();
		
		// Create a 3rd User
		User user2 = testUtility.createUserInDB();
		
		// Add a user
		membershipService.addAspirant(university.getId(), user2.getId());
		
		// Get List of Aspirants again
		List aspirants2 = university.getAspirants();
		assertNotNull(aspirants2);
		assertEquals(aspirants2.size(),sizeBefore+1);
		
		// Try to add Aspirant again
		try {
			membershipService.addAspirant(university.getId(), user2.getId());
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {};
		
		logger.info("----> END access to addAspirant test");
	}
	
	public void testFindAllMembers() {
		logger.info("----> BEGIN access to findAllMembers test");
		
		// Create University with DeafultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		
		// Create a 2nd User
		User user1 = testUtility.createUserInDB();
		
		// Get List of Members
		List members = membershipService.findAllMembers(university.getId());
		assertNotNull(members);
		int sizeBefore = members.size();

		// Add a user using DAO object
		university.getMembers().add(user1);
		
		// Get List of Members again
		List members2 = membershipService.findAllMembers(university.getId());
		assertNotNull(members2);
		assertEquals(members2.size(),sizeBefore+1);
		
		logger.info("----> END access to findAllMembers test");
	}
	
	public void testFindAllAspirants() {
		logger.info("----> BEGIN access to findAllAspirants test");
		
		// Create University with DeafultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		
		// Create a 2nd User
		User user1 = testUtility.createUserInDB();
		
		// Get List of Aspirants
		List aspirants = membershipService.findAllAspirants(university.getId());
		assertNotNull(aspirants);
		int sizeBefore = aspirants.size();
		
		// Add a user using DAO object
		university.getAspirants().add(user1);
		
		// Get List of Aspirants again
		List aspirants2 = membershipService.findAllAspirants(university.getId());
		assertNotNull(aspirants2);
		assertEquals(aspirants2.size(),sizeBefore+1);
		
		logger.info("----> END access to findAllAspirants test");
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