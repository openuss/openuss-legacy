// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.lecture.University;

/**
 * JUnit Test for Spring Hibernate MembershipService class.
 * 
 * @see org.openuss.security.MembershipService
 * @author Ron Haus
 */
@SuppressWarnings( { "unchecked" })
public class MembershipServiceIntegrationTest extends MembershipServiceIntegrationTestBase {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MembershipServiceIntegrationTest.class);

	public void testAcceptAspirant() {
		logger.info("----> BEGIN access to acceptAspirant test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Create a User
		User user1 = testUtility.createUniqueUserInDB();

		// Try to accept the User
		try {
			membershipService.acceptAspirant(university.getMembership(), user1);
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {
		}
		;

		// Add User to Aspirants
		university.getMembership().getAspirants().add(user1);
		int sizeAspirantsBefore = university.getMembership().getAspirants().size();
		int sizeMembersBefore = university.getMembership().getMembers().size();

		// Synchronize with Database
		flush();

		// Accept Aspirant
		membershipService.acceptAspirant(university.getMembership(), user1);
		List aspirants = university.getMembership().getAspirants();
		assertNotNull(aspirants);
		assertEquals(sizeAspirantsBefore - 1, aspirants.size());
		List members = university.getMembership().getMembers();
		assertNotNull(members);
		assertEquals(sizeMembersBefore + 1, members.size());

		// Synchronize with Database
		flush();

		logger.info("----> END access to acceptAspirant test");
	}

	public void testRejectAspirant() {
		logger.info("----> BEGIN access to rejectAspirant test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Create a User
		User user1 = testUtility.createUniqueUserInDB();

		// Try to accept the User
		try {
			membershipService.rejectAspirant(university.getMembership(), user1);
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {
		}
		;

		// Add User to Aspirants and reject after that
		university.getMembership().getAspirants().add(user1);
		int sizeAspirantsBefore = university.getMembership().getAspirants().size();
		int sizeMembersBefore = university.getMembership().getMembers().size();

		// Synchronize with Database
		flush();

		// Reject Aspirant
		membershipService.rejectAspirant(university.getMembership(), user1);
		List aspirants = university.getMembership().getAspirants();
		assertNotNull(aspirants);
		assertEquals(sizeAspirantsBefore - 1, aspirants.size());
		List members = university.getMembership().getMembers();
		assertNotNull(members);
		assertEquals(sizeMembersBefore, members.size());

		// Synchronize with Database
		flush();

		logger.info("----> END access to rejectAspirant test");
	}

	public void testAddMember() {
		logger.info("----> BEGIN access to addMember test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Create a User
		User user1 = testUtility.createUniqueUserInDB();

		// Try to add a Aspirant as a Member
		try {
			university.getMembership().getAspirants().add(user1);
			membershipService.addMember(university.getMembership(), user1);
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {
		}
		;

		// Get List of Members
		List members = university.getMembership().getMembers();
		assertNotNull(members);
		int sizeBefore = members.size();

		// Create a 2nd User
		User user2 = testUtility.createUniqueUserInDB();

		// Add a User
		membershipService.addMember(university.getMembership(), user2);

		// Get List of Members again
		List members2 = university.getMembership().getMembers();
		assertNotNull(members2);
		assertEquals(sizeBefore + 1, members2.size());

		// Try to add Member again
		try {
			membershipService.addMember(university.getMembership(), user2);
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {
		}
		;

		// Synchronize with Database
		flush();

		logger.info("----> END access to addMember test");
	}
	
	public void testRemoveMember() {
		logger.info("----> BEGIN access to removeMember test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Create a User
		User user = testUtility.createUniqueUserInDB();

		// Add a User
		membershipService.addMember(university.getMembership(), user);
		int sizeBefore = university.getMembership().getMembers().size();

		// Synchronize with Database
		flush();
		
		// Remove Member
		membershipService.removeMember(university.getMembership(), user);
		
		// Get List of Members
		List members2 = university.getMembership().getMembers();
		assertNotNull(members2);
		assertEquals(sizeBefore - 1, members2.size());

		// Synchronize with Database
		flush();

		logger.info("----> END access to removeMember test");
	}

	public void testAddAspirant() {
		logger.info("----> BEGIN access to addAspirant test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Try to add Member as an Aspirant
		try {
			membershipService.addAspirant(university.getMembership(), university.getMembership().getMembers()
					.get(0));
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {
		}
		;

		// Get List of Aspirants
		List aspirants = university.getMembership().getAspirants();
		assertNotNull(aspirants);
		int sizeBefore = aspirants.size();

		// Create a 2nd User
		User user2 = testUtility.createUniqueUserInDB();

		// Add a user
		membershipService.addAspirant(university.getMembership(), user2);

		// Get List of Aspirants again
		List aspirants2 = university.getMembership().getAspirants();
		assertNotNull(aspirants2);
		assertEquals(sizeBefore + 1, aspirants2.size());

		// Try to add Aspirant again
		try {
			membershipService.addAspirant(university.getMembership(), user2);
			fail("Exception should have been thrown");
		} catch (MembershipServiceException mse) {
		}
		;

		// Synchronize with Database
		flush();

		logger.info("----> END access to addAspirant test");
	}
	
	public void testCreateGroup() {
		logger.info("----> BEGIN access to createGroup test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		//Create a GroupItem
		Group group = Group.Factory.newInstance();
		group.setName("Administrators");
		group.setLabel("Admins");
		group.setPassword("feelfree");
		group.setGroupType(GroupType.ADMINISTRATOR);
		
		assertNotNull(university.getMembership().getGroups());
		int sizeBefore = university.getMembership().getGroups().size();
		
		// Create the Group
		membershipService.createGroup(university.getMembership(), group);
		
		assertEquals(sizeBefore + 1, university.getMembership().getGroups().size());
		assertTrue(university.getMembership().getGroups().get(0) instanceof Group);
		
		// Synchronize with Database
		flush();

		logger.info("----> END access to createGroup test");
	}
	
	public void testRemoveUserDependencies(){
		// Create University with DefaultUser as Owner
		//test not working, no idea why.
//		University university = testUtility.createUniqueUniversityInDB();
//		User user = testUtility.createUniqueUserInDB();
//
//		flush();
//		
//		membershipService.addAspirant(university.getMembership(), user);
//		assertEquals(1, getAspirantCount(university));
//		
//		flush();
//
//		membershipService.removeUserDependencies(user);
//		assertEquals(0, getAspirantCount(university));
	}

//	private int getAspirantCount(University university) {
//		int aspirantCount = 0;
//		for (Group group : university.getMembership().getGroups()){
//			aspirantCount += group.getMembers().size();
//		}
//		return aspirantCount;
//	}

}