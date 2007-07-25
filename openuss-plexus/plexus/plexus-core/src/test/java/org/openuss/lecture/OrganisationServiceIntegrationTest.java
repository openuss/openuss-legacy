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
import org.openuss.security.User;
import org.openuss.security.UserInfo;

/**
 * JUnit Test for Spring Hibernate OrganisationService class.
 * 
 * @see org.openuss.lecture.OrganisationService
 * @author Ron Haus
 */
public class OrganisationServiceIntegrationTest extends OrganisationServiceIntegrationTestBase {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OrganisationServiceIntegrationTest.class);

	@SuppressWarnings( { "unchecked" })
	public void testAddMember() {
		logger.info("----> BEGIN access to addMember test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();

		// Get List of Members
		List members = university.getMembership().getMembers();
		assertNotNull(members);
		int sizeBefore = members.size();

		// Create a User
		User user = testUtility.createUserInDB();

		// Add a User
		organisationService.addMember(university.getId(), user.getId());

		// Get List of Members again
		List members2 = university.getMembership().getMembers();
		assertNotNull(members2);
		assertEquals(sizeBefore + 1, members2.size());

		// Synchronize with Database
		flush();

		logger.info("----> END access to addMember test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testAddAspirant() {
		logger.info("----> BEGIN access to addAspirant test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();

		// Get List of Aspirants
		List aspirants = university.getMembership().getAspirants();
		assertNotNull(aspirants);
		int sizeBefore = aspirants.size();

		// Create a User
		User user = testUtility.createUserInDB();

		// Add a user
		organisationService.addAspirant(university.getId(), user.getId());

		// Get List of Aspirants again
		List aspirants2 = university.getMembership().getAspirants();
		assertNotNull(aspirants2);
		assertEquals(sizeBefore + 1, aspirants2.size());

		// Synchronize with Database
		flush();

		logger.info("----> END access to addAspirant test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testFindAllMembers() {
		logger.info("----> BEGIN access to findAllMembers test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();

		// Create 3 User
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 3; i++) {
			users.add(testUtility.createUserInDB());
		}

		// Add users as members
		for (User user : users) {
			university.getMembership().getMembers().add(user);
		}

		List userInfos = organisationService.findAllMembers(university.getId());

		assertEquals(university.getMembership().getMembers().size(), userInfos.size());
		Iterator iterator = null;
		UserInfo userInfo = null;
		int count = 0;
		for (User user : users) {
			iterator = userInfos.iterator();
			while (iterator.hasNext()) {
				userInfo = (UserInfo) iterator.next();
				if (userInfo.getId() == user.getId()) {
					count++;
				}
			}
		}
		assertEquals(users.size(), count);

		logger.info("----> END access to findAllMembers test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testFindAllAspirants() {
		logger.info("----> BEGIN access to findAllAspirants test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createDefaultUniversityWithDefaultUser();

		// Create 3 User
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 3; i++) {
			users.add(testUtility.createUserInDB());
		}

		// Add users as Aspirants
		for (User user : users) {
			university.getMembership().getAspirants().add(user);
		}

		List userInfos = organisationService.findAllAspirants(university.getId());

		assertEquals(university.getMembership().getAspirants().size(), userInfos.size());
		Iterator iterator = null;
		UserInfo userInfo = null;
		int count = 0;
		for (User user : users) {
			iterator = userInfos.iterator();
			while (iterator.hasNext()) {
				userInfo = (UserInfo) iterator.next();
				if (userInfo.getId() == user.getId()) {
					count++;
				}
			}
		}
		assertEquals(users.size(), count);

		logger.info("----> END access to findAllAspirants test");
	}

	public void testAcceptAspirant() {

	}

	public void testRejectAspirant() {

	}

	public void testCreateGroup() {

	}

	public void testRemoveGroup() {

	}

	public void testAddUserToGroup() {

	}

	public void testRemoveUserFromGroup() {
	}
}