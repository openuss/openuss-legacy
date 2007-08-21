// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.apache.log4j.Logger;
import org.openuss.security.Group;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

/**
 * JUnit Test for Spring Hibernate OrganisationService class.
 * 
 * @see org.openuss.lecture.OrganisationService
 * @author Ron Haus
 * @author Florian Dondorf
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
		University university = testUtility.createUniqueUniversityInDB();

		// Get List of Members
		List members = university.getMembership().getMembers();
		assertNotNull(members);
		int sizeBefore = members.size();

		// Create a User
		User user = testUtility.createUniqueUserInDB();

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
		University university = testUtility.createUniqueUniversityInDB();

		// Get List of Aspirants
		List aspirants = university.getMembership().getAspirants();
		assertNotNull(aspirants);
		int sizeBefore = aspirants.size();

		// Create a User
		User user = testUtility.createUniqueUserInDB();

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
		University university = testUtility.createUniqueUniversityInDB();

		// Create 3 User
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 3; i++) {
			users.add(testUtility.createUniqueUserInDB());
		}

		// Add users as members
		for (User user : users) {
			university.getMembership().getMembers().add(user);
		}

		// Synchronize with Database
		flush();

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
		University university = testUtility.createUniqueUniversityInDB();

		// Create 3 User
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 3; i++) {
			users.add(testUtility.createUniqueUserInDB());
		}

		// Add users as Aspirants
		for (User user : users) {
			university.getMembership().getAspirants().add(user);
		}

		// Synchronize with Database
		flush();

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

	@SuppressWarnings( { "unchecked" })
	public void testAcceptAspirant() {
		logger.info("----> BEGIN access to acceptAspirants test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Create a User
		User user = testUtility.createUniqueUserInDB();

		// Add user as Aspirant
		university.getMembership().getAspirants().add(user);

		// Get List of Aspirants
		List aspirants = university.getMembership().getAspirants();
		assertNotNull(aspirants);
		int sizeAspBefore = aspirants.size();

		// Get List of Members
		List members = university.getMembership().getMembers();
		assertNotNull(members);
		int sizeMemBefore = members.size();

		// Synchronize with Database
		flush();

		// Accept Aspirant
		organisationService.acceptAspirant(university.getId(), user.getId());

		assertEquals(sizeAspBefore - 1, university.getMembership().getAspirants().size());
		assertEquals(sizeMemBefore + 1, university.getMembership().getMembers().size());

		logger.info("----> END access to acceptAspirants test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testRejectAspirant() {
		logger.info("----> BEGIN access to rejectAspirants test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Create a User
		User user = testUtility.createUniqueUserInDB();

		// Add user as Aspirant
		university.getMembership().getAspirants().add(user);

		// Get List of Aspirants
		List aspirants = university.getMembership().getAspirants();
		assertNotNull(aspirants);
		int sizeAspBefore = aspirants.size();

		// Get List of Members
		List members = university.getMembership().getMembers();
		assertNotNull(members);
		int sizeMemBefore = members.size();

		// Synchronize with Database
		flush();

		// Reject Aspirant
		organisationService.rejectAspirant(university.getId(), user.getId());

		assertEquals(sizeAspBefore - 1, university.getMembership().getAspirants().size());
		assertEquals(sizeMemBefore, university.getMembership().getMembers().size());

		logger.info("----> END access to rejectAspirants test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testCreateGroup() {
		logger.info("----> BEGIN access to createGroup test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Get List of Groups
		List groups = university.getMembership().getGroups();
		assertNotNull(groups);
		int sizeBefore = groups.size();

		// Define a new Group
		GroupItem groupItem = new GroupItem();
		groupItem.setName("UNIVERSITY_" + university.getId() + "_TestGroup");
		groupItem.setLabel("autogroup_testgroup_label");
		groupItem.setGroupType(GroupType.ADMINISTRATOR);

		// Synchronize with Database
		flush();

		// Create Group
		organisationService.createGroup(university.getId(), groupItem);

		assertEquals(sizeBefore + 1, university.getMembership().getGroups().size());

		logger.info("----> END access to createGroup test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testRemoveGroup() {
		logger.info("----> BEGIN access to removeGroup test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Add a Group
		Group group = Group.Factory.newInstance();
		group.setName("UNIVERSITY_" + university.getId() + "_TestGroup");
		group.setLabel("autogroup_testgroup_label");
		group.setGroupType(GroupType.ADMINISTRATOR);
		university.getMembership().getGroups().add(group);

		// Add Members to the Group
		User user1 = testUtility.createUniqueUserInDB();
		User user2 = testUtility.createUniqueUserInDB();
		flush();
		organisationService.addUserToGroup(user1.getId(), group.getId());
		organisationService.addUserToGroup(user2.getId(), group.getId());

		// Get List of Groups
		List groups = university.getMembership().getGroups();
		assertNotNull(groups);
		int sizeBefore = groups.size();

		// Synchronize with Database
		flush();

		// Remove Group
		organisationService.removeGroup(university.getId(), group.getId());

		assertEquals(sizeBefore - 1, university.getMembership().getGroups().size());

		// Synchronize with Database
		flush();

		logger.info("----> END access to removeGroup test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testAddUserToGroup() {
		logger.info("----> BEGIN access to addUserToGroup test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Add a Group
		Group group = Group.Factory.newInstance();
		group.setName("UNIVERSITY_" + university.getId() + "_TestGroup");
		group.setLabel("autogroup_testgroup_label");
		group.setGroupType(GroupType.ADMINISTRATOR);
		university.getMembership().getGroups().add(group);

		// Create a User
		User user = testUtility.createUniqueUserInDB();

		// Get List of Users of the Group
		List users = group.getMembers();
		assertNotNull(users);
		int sizeBefore = users.size();

		// Synchronize with Database
		flush();

		// Add User to Group
		organisationService.addUserToGroup(user.getId(), group.getId());

		assertEquals(sizeBefore + 1, group.getMembers().size());

		// Synchronize with Database
		flush();

		logger.info("----> END access to addUserToGroup test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testRemoveUserFromGroup() {
		logger.info("----> BEGIN access to removeUserFromGroup test");

		// Create University with DefaultUser as Owner
		University university = testUtility.createUniqueUniversityInDB();

		// Add a Group
		Group group = Group.Factory.newInstance();
		group.setName("UNIVERSITY_" + university.getId() + "_TestGroup");
		group.setLabel("autogroup_testgroup_label");
		group.setGroupType(GroupType.ADMINISTRATOR);
		university.getMembership().getGroups().add(group);

		// Create a User
		User user = testUtility.createUniqueUserInDB();

		// Add user to Group
		group.addMember(user);

		// Get List of Users of the Group
		List users = group.getMembers();
		assertNotNull(users);
		int sizeBefore = users.size();

		// Synchronize with Database
		flush();

		// Remove User from Group
		organisationService.removeUserFromGroup(user.getId(), group.getId());

		assertEquals(sizeBefore - 1, group.getMembers().size());

		// Synchronize with Database
		flush();

		logger.info("----> END access to removeUserFromGroup test");
	}

	public void testFindGroupsByOrganisation() {
		logger.info("----> BEGIN access to findGroupsByOrganisation test");

		// Create university
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university);
		int sizeBefore = university.getMembership().getGroups().size();

		// Create 2 more Groups
		GroupItem groupItem1 = new GroupItem();
		groupItem1.setName("UNIVERSITY_" + university.getId() + "_TUTORS");
		groupItem1.setLabel("autogroup_tutors_label");
		groupItem1.setGroupType(GroupType.ADMINISTRATOR);
		Group group1 = this.getOrganisationService().createGroup(university.getId(), groupItem1);

		GroupItem groupItem2 = new GroupItem();
		groupItem2.setName("UNIVERSITY_" + university.getId() + "_ASSISTENT");
		groupItem2.setLabel("autogroup_assistent_label");
		groupItem2.setGroupType(GroupType.ADMINISTRATOR);
		Group group2 = this.getOrganisationService().createGroup(university.getId(), groupItem2);

		// Synchronize with DB
		flush();

		// Test
		List groups = this.getOrganisationService().findGroupsByOrganisation(university.getId());

		// assertEquals(sizeBefore+2, groups.size());
		assertTrue(groups.get(0) instanceof GroupItem);
		assertTrue(university.getMembership().getGroups().get(0) instanceof Group);

		Iterator iterator = null;
		GroupItem groupItem = null;
		int count = 0;
		for (Group group : university.getMembership().getGroups()) {
			iterator = groups.iterator();
			while (iterator.hasNext()) {
				groupItem = (GroupItem) iterator.next();
				if (groupItem.getId() == group.getId()) {
					count++;
				}
			}
		}
		assertEquals(university.getMembership().getGroups().size(), count);

		logger.info("----> END access to findGroupsByOrganisation test");
	}

	public void testSetOrganisationEnabled() {
		logger.info("----> BEGIN access to setOrganisationEnabled test");

		// Create university
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university);
		assertTrue(university.getEnabled());

		// Synchronize with DB
		flush();

		testUtility.createUserSecureContext();
		try {
			this.getOrganisationService().setOrganisationEnabled(university.getId(), false);
			fail("AccessDeniedException should have been thrown.");

		} catch (AccessDeniedException ade) {
			;
		} finally {
			testUtility.destroySecureContext();
		}

		testUtility.createAdminSecureContext();
		this.getOrganisationService().setOrganisationEnabled(university.getId(), false);

		// Load university
		UniversityDao universityDao = (UniversityDao) this.getApplicationContext().getBean("universityDao");
		University universityTest = universityDao.load(university.getId());

		assertFalse(universityTest.getEnabled());
		testUtility.destroySecureContext();

		testUtility.createAdminSecureContext();
		this.getOrganisationService().setOrganisationEnabled(university.getId(), true);

		// Load university
		universityDao = (UniversityDao) this.getApplicationContext().getBean("universityDao");
		University universityTest1 = universityDao.load(university.getId());

		assertTrue(universityTest1.getEnabled());
		testUtility.destroySecureContext();

		logger.info("----> END access to setOrganisationEnabled test");
	}
}