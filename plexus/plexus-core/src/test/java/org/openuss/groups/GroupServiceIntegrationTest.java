// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import java.util.ArrayList;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate GroupService class.
 * 
 * @author Lutz D. Kramer
 * 
 * @see org.openuss.groups.GroupService
 */
public class GroupServiceIntegrationTest extends
		GroupServiceIntegrationTestBase {

	private TestUtility testUtility;

	public void testCreateUserGroup() {
		logger.debug("----> BEGIN access to create test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroupInfo
		UserGroupInfo groupInfo = new UserGroupInfo();
		groupInfo.setAccessType(GroupAccessType.OPEN);
		groupInfo.setCalendar(true);
		groupInfo.setChat(true);
		User user = testUtility.createUniqueUserInDB();
		groupInfo.setCreator(user.getId());
		groupInfo.setDescription("A UserGroup");
		groupInfo.setDocuments(true);
		groupInfo.setForum(true);
		groupInfo.setName("UserGroup");
		groupInfo.setPassword(null);
		groupInfo.setShortcut("group");

		// Test
		Long groupId = this.getGroupService().createUserGroup(groupInfo,
				user.getId());
		assertNotNull(groupId);

		UserGroup groupTest = testUtility.getUserGroupDao().load(groupId);
		assertNotNull(groupTest);
		assertEquals(groupInfo.getAccessType(), groupTest.getAccessType());
		assertEquals(groupInfo.getDescription(), groupTest.getDescription());
		assertEquals(groupInfo.getCreator(), groupTest.getCreator().getId());
		assertEquals(groupInfo.getName(), groupTest.getName());
		assertEquals(groupInfo.getPassword(), groupTest.getPassword());
		assertEquals(groupInfo.getShortcut(), groupTest.getShortcut());
		assertEquals(groupInfo.isCalendar(), groupTest.getCalendar()
				.booleanValue());
		assertEquals(groupInfo.isChat(), groupTest.getChat().booleanValue());
		assertEquals(groupInfo.isDocuments(), groupTest.getDocuments()
				.booleanValue());
		assertEquals(groupInfo.isForum(), groupTest.getForum().booleanValue());
		assertEquals(groupInfo.isNewsletter(), groupTest.getNewsletter()
				.booleanValue());

		logger.debug("----> END access to create test <---- ");
	}

	public void testUpdateUserGroup() {
		logger.debug("----> BEGIN access to update test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();

		// Change UserInfoObject
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		groupInfo.setName("TestName");
		groupInfo.setDescription("TestDescription");

		// Test
		this.getGroupService().updateUserGroup(groupInfo);
		UserGroup groupTest = testUtility.getUserGroupDao().load(
				groupInfo.getId());
		assertEquals(groupInfo.getAccessType(), groupTest.getAccessType());
		assertEquals(groupInfo.getDescription(), groupTest.getDescription());
		assertEquals(groupInfo.getCreator(), groupTest.getCreator().getId());
		assertEquals(groupInfo.getName(), groupTest.getName());
		assertEquals(groupInfo.getPassword(), groupTest.getPassword());
		assertEquals(groupInfo.getShortcut(), groupTest.getShortcut());
		assertEquals(groupInfo.isCalendar(), groupTest.getCalendar()
				.booleanValue());
		assertEquals(groupInfo.isChat(), groupTest.getChat().booleanValue());
		assertEquals(groupInfo.isDocuments(), groupTest.getDocuments()
				.booleanValue());
		assertEquals(groupInfo.isForum(), groupTest.getForum().booleanValue());
		assertEquals(groupInfo.isNewsletter(), groupTest.getNewsletter()
				.booleanValue());

		logger.debug("----> END access to update test <---- ");

	}

	public void testDeleteUserGroup() {
		logger.debug("----> BEGIN access to delete test <---- ");

		// Synchronize with DB
		flush();

		
		// Create UserGroupInfo
		UserGroupInfo groupInfo = new UserGroupInfo();
		groupInfo.setAccessType(GroupAccessType.OPEN);
		groupInfo.setCalendar(true);
		groupInfo.setChat(true);
		User user = testUtility.createUniqueUserInDB();
		groupInfo.setCreator(user.getId());
		groupInfo.setDescription("A UserGroup");
		groupInfo.setDocuments(true);
		groupInfo.setForum(true);
		groupInfo.setName("UserGroup");
		groupInfo.setPassword(null);
		groupInfo.setShortcut("group");
		assertNotNull(groupInfo);
		
		
		// Create UserGroup
		Long groupId = this.getGroupService().createUserGroup(groupInfo,
				user.getId());
		assertNotNull(groupId);
		
		// Load Group
		UserGroup userGroup = testUtility.getUserGroupDao().load(groupId);
		assertNotNull(userGroup);
		

		// Load UserInfoObject
		UserGroupInfo groupInfoTest = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfoTest);

		// Delete Group
		this.getGroupService().deleteUserGroup(groupInfoTest);

		// Test
		try {
			UserGroup groupTest = testUtility.getUserGroupDao().load(
					groupInfoTest.getId());
			assertNull(groupTest);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}

		logger.debug("----> END access to delete test <---- ");
	}

	public void testAddAspirant() {
		logger.debug("----> BEGIN access to AddAspirant test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Aspirant
		this.getGroupService().addAspirant(groupInfo, user.getId());

		// Test
		boolean asp = false;
		List<User> aspirants = userGroup.getMembership().getAspirants();
		for (User aspirant : aspirants) {
			if (aspirant == user) {
				asp = true;
			}
		}
		assertTrue(asp);
		boolean mem = this.getGroupService().isMember(groupInfo, user.getId());
		assertFalse(mem);

		logger.debug("----> END access to AddAspirant test <---- ");
	}

	public void testAddMember() {
		logger.debug("----> BEGIN access to AddMember test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Member
		this.getGroupService().addMember(groupInfo, user.getId());

		// Test
		boolean mem = this.getGroupService().isMember(groupInfo, user.getId());
		assertTrue(mem);
		boolean mod = this.getGroupService().isModerator(groupInfo,
				user.getId());
		assertFalse(mod);

		logger.debug("----> END access to AddMember test <---- ");
	}

	public void testAddModerator() {
		logger.debug("----> BEGIN access to AddModerator test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Moderator
		this.getGroupService().addMember(groupInfo, user.getId());
		this.getGroupService().addModerator(groupInfo, user.getId());

		// Test
		boolean mod = this.getGroupService().isModerator(groupInfo,
				user.getId());
		assertTrue(mod);

		logger.debug("----> END access to AddModerator test <---- ");
	}

	public void testAddUserByPassword() {
		logger.debug("----> BEGIN access to AddUserByPassword test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Set AccessType to Password
		userGroup.setAccessType(GroupAccessType.PASSWORD);
		userGroup.setPassword("Password");
		testUtility.getUserGroupDao().update(userGroup);

		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Member
		this.getGroupService().addUserByPassword(groupInfo, "Password",
				user.getId());

		// Test
		boolean mem = this.getGroupService().isMember(groupInfo, user.getId());
		assertTrue(mem);
		boolean mod = this.getGroupService().isModerator(groupInfo,
				user.getId());
		assertFalse(mod);

		logger.debug("----> END access to AddUserByPassword test <---- ");
	}

	public void testRemoveModerator() {
		logger.debug("----> BEGIN access to RemoveModerator test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Moderator
		this.getGroupService().addMember(groupInfo, user.getId());
		this.getGroupService().addModerator(groupInfo, user.getId());

		// Test
		boolean mod1 = this.getGroupService().isModerator(groupInfo,
				user.getId());
		assertTrue(mod1);

		// Remove User as Moderator
		this.getGroupService().removeModerator(groupInfo, user.getId());

		// Test
		boolean mod2 = this.getGroupService().isModerator(groupInfo,
				user.getId());
		assertFalse(mod2);

		logger.debug("----> END access to RemoveModerator test <---- ");
	}

	public void testRemoveMember() {
		logger.debug("----> BEGIN access to RemoveMember test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Member
		this.getGroupService().addMember(groupInfo, user.getId());

		// Test
		boolean mem1 = this.getGroupService().isMember(groupInfo, user.getId());
		assertTrue(mem1);

		// Remove User as Member
		this.getGroupService().removeMember(groupInfo, user.getId());

		// Test
		boolean mem2 = this.getGroupService().isMember(groupInfo, user.getId());
		assertFalse(mem2);

		logger.debug("----> END access to RemoveMember test <---- ");
	}

	public void testAcceptAspirant() {
		logger.debug("----> BEGIN access to AcceptAspirant test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Aspirant
		this.getGroupService().addAspirant(groupInfo, user.getId());

		// Test
		boolean asp1 = false;
		List<User> aspirants1 = userGroup.getMembership().getAspirants();
		for (User aspirant : aspirants1) {
			if (aspirant == user) {
				asp1 = true;
			}
		}
		assertTrue(asp1);
		boolean mem1 = this.getGroupService().isMember(groupInfo, user.getId());
		assertFalse(mem1);

		// Accept User as Aspirant
		this.getGroupService().acceptAspirant(groupInfo, user.getId());

		// Test
		boolean asp2 = false;
		List<User> aspirants2 = userGroup.getMembership().getAspirants();
		for (User aspirant : aspirants2) {
			if (aspirant == user) {
				asp2 = true;
			}
		}
		assertFalse(asp2);
		boolean mem2 = this.getGroupService().isMember(groupInfo, user.getId());
		assertTrue(mem2);

		logger.debug("----> END access to AcceptAspirant test <---- ");
	}

	public void testRejectAspirant() {
		logger.debug("----> BEGIN access to RejectAspirant test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Aspirant
		this.getGroupService().addAspirant(groupInfo, user.getId());

		// Test
		boolean asp1 = false;
		List<User> aspirants1 = userGroup.getMembership().getAspirants();
		for (User aspirant : aspirants1) {
			if (aspirant == user) {
				asp1 = true;
			}
		}
		assertTrue(asp1);
		boolean mem1 = this.getGroupService().isMember(groupInfo, user.getId());
		assertFalse(mem1);

		// Accept User as Aspirant
		this.getGroupService().rejectAspirant(groupInfo, user.getId());

		// Test
		boolean asp2 = false;
		List<User> aspirants2 = userGroup.getMembership().getAspirants();
		for (User aspirant : aspirants2) {
			if (aspirant == user) {
				asp2 = true;
			}
		}
		assertFalse(asp2);
		boolean mem2 = this.getGroupService().isMember(groupInfo, user.getId());
		assertFalse(mem2);

		logger.debug("----> END access to RejectAspirant test <---- ");
	}

	public void testGetMembers() {
		logger.debug("----> BEGIN access to GetMembers test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Create Users
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			User user = testUtility.createUniqueUserInDB();
			assertNotNull(user);
			users.add(user);
			assertNotNull(users.get(i));

		}
		assertNotNull(users);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Member
		for (User user : users) {
			this.getGroupService().addMember(groupInfo, user.getId());
		}

		// Test
		List<UserGroupMemberInfo> members = this.getGroupService().getMembers(
				groupInfo);
		assertEquals(users.size(), members.size());
		for (UserGroupMemberInfo member : members) {
			assertTrue(this.getGroupService().isMember(groupInfo,
					member.getUserId()));
			assertFalse(this.getGroupService().isModerator(groupInfo,
					member.getUserId()));
		}
		for (User user : users) {
			assertTrue(this.getGroupService().isMember(groupInfo, user.getId()));
			assertFalse(this.getGroupService().isModerator(groupInfo,
					user.getId()));
		}

		logger.debug("----> END access to GetMembers test <---- ");
	}

	public void testGetModerators() {
		logger.debug("----> BEGIN access to GetModerators test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Create Users
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			User user = testUtility.createUniqueUserInDB();
			assertNotNull(user);
			users.add(user);
			assertNotNull(users.get(i));

		}
		assertNotNull(users);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Member
		for (User user : users) {
			this.getGroupService().addMember(groupInfo, user.getId());
			this.getGroupService().addModerator(groupInfo, user.getId());
		}

		// Test
		List<UserGroupMemberInfo> moderators = this.getGroupService()
				.getModerators(groupInfo);
		assertEquals(users.size(), moderators.size());
		for (UserGroupMemberInfo moderator : moderators) {
			assertTrue(this.getGroupService().isMember(groupInfo,
					moderator.getUserId()));
			assertTrue(this.getGroupService().isModerator(groupInfo,
					moderator.getUserId()));
		}
		for (User user : users) {
			assertTrue(this.getGroupService().isMember(groupInfo, user.getId()));
			assertTrue(this.getGroupService().isModerator(groupInfo,
					user.getId()));
		}

		logger.debug("----> END access to GetModerators test <---- ");
	}

	public void testGetAspirants() {
		logger.debug("----> BEGIN access to GetAspirants test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);

		// Create Users
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			User user = testUtility.createUniqueUserInDB();
			assertNotNull(user);
			users.add(user);
			assertNotNull(users.get(i));

		}
		assertNotNull(users);

		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertNotNull(groupInfo.getId());

		// Add User as Member
		for (User user : users) {
			this.getGroupService().addAspirant(groupInfo, user.getId());
		}

		// Test
		List<UserGroupMemberInfo> aspirants = this.getGroupService()
				.getAspirants(groupInfo);
		assertEquals(users.size(), aspirants.size());
		for (UserGroupMemberInfo aspirant : aspirants) {
			boolean test = false;
			for (User user : users) {
				//TODO Lutz Check if this works
				if (aspirant.getUserId() == user.getId()) {
					test = true;
				}
			}
			assertTrue(test);
		}
		for (User user : users) {
			boolean test = false;
			for (UserGroupMemberInfo aspirant : aspirants) {
				if (aspirant.getUserId() == user.getId()) {
					test = true;
				}
			}
			assertTrue(test);
		}

		logger.debug("----> END access to GetAspirants test <---- ");
	}

	public void testGetAllGroups() {
		logger.debug("----> BEGIN access to GetAllGroups test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		for (int i = 0; i < 10; i++) {
			UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
			assertNotNull(userGroup);
			userGroups.add(userGroup);
		}
		assertNotNull(userGroups);

		// Group to GroupInfo
		List<UserGroupInfo> groupInfos = new ArrayList<UserGroupInfo>();
		for (UserGroup group : userGroups) {
			UserGroupInfo groupInfo = testUtility.getUserGroupDao()
					.toUserGroupInfo(group);
			assertNotNull(groupInfo);
			assertNotNull(groupInfo.getId());
			groupInfos.add(groupInfo);
		}
		assertNotNull(groupInfos);

		// Test
		List<UserGroupInfo> groupsByUser = this.getGroupService()
				.getAllGroups();
		for (UserGroupInfo groupByUser : groupsByUser) {
			boolean test = false;
			for (UserGroup userGroup : userGroups) {
				//TODO Lutz Check if this works
				if (userGroup.getId() == testUtility.getUserGroupDao()
						.userGroupInfoToEntity(groupByUser).getId()) {
					test = true;
				}
			}
			assertTrue(test);
		}

		logger.debug("----> END access to GetAllGroups test <---- ");
	}

	public void testGetGroupsByUser() {
		logger.debug("----> BEGIN access to GetGroupsByUser test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroup
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		for (int i = 0; i < 10; i++) {
			UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
			assertNotNull(userGroup);
			userGroups.add(userGroup);
		}
		assertNotNull(userGroups);

		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);

		// Group to GroupInfo
		List<UserGroupInfo> groupInfos = new ArrayList<UserGroupInfo>();
		for (UserGroup group : userGroups) {
			UserGroupInfo groupInfo = testUtility.getUserGroupDao()
					.toUserGroupInfo(group);
			assertNotNull(groupInfo);
			assertNotNull(groupInfo.getId());
			groupInfos.add(groupInfo);
		}
		assertNotNull(groupInfos);

		// Add User as Member
		for (UserGroupInfo infos : groupInfos) {
			this.getGroupService().addMember(infos, user.getId());
		}

		// Test
		List<UserGroupInfo> groupsByUser = this.getGroupService()
				.getGroupsByUser(user.getId());
		for (UserGroupInfo groupByUser : groupsByUser) {
			boolean test = false;
			for (UserGroup userGroup : userGroups) {
				//TODO Lutz Check if this works
				if (userGroup.getId() == testUtility.getUserGroupDao()
						.userGroupInfoToEntity(groupByUser).getId()) {
					test = true;
				}
			}
			assertTrue(test);
		}

		logger.debug("----> END access to GetGroupsByUser test <---- ");
	}

	public void testGetGroupInfo() {
		logger.debug("----> BEGIN access to GetGroupInfo test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroupInfo
		UserGroupInfo groupInfo = new UserGroupInfo();
		groupInfo.setAccessType(GroupAccessType.OPEN);
		groupInfo.setCalendar(true);
		groupInfo.setChat(true);
		User user = testUtility.createUniqueUserInDB();
		groupInfo.setCreator(user.getId());
		groupInfo.setDescription("A UserGroup");
		groupInfo.setDocuments(true);
		groupInfo.setForum(true);
		groupInfo.setName("UserGroup");
		groupInfo.setPassword(null);
		groupInfo.setShortcut("group");

		// Test
		Long groupId = this.getGroupService().createUserGroup(groupInfo,
				user.getId());
		assertNotNull(groupId);

		UserGroupInfo groupTest = this.getGroupService().getGroupInfo(groupId);
		assertNotNull(groupTest);

		assertEquals(groupInfo.getAccessType(), groupTest.getAccessType());
		assertEquals(groupInfo.getDescription(), groupTest.getDescription());
		assertEquals(groupInfo.getCreator(), groupTest.getCreator());
		assertEquals(groupInfo.getName(), groupTest.getName());
		assertEquals(groupInfo.getPassword(), groupTest.getPassword());
		assertEquals(groupInfo.getShortcut(), groupTest.getShortcut());
		assertEquals(groupInfo.isCalendar(), groupTest.isCalendar());
		assertEquals(groupInfo.isChat(), groupTest.isChat());
		assertEquals(groupInfo.isDocuments(), groupTest.isDocuments());
		assertEquals(groupInfo.isForum(), groupTest.isForum());
		assertEquals(groupInfo.isNewsletter(), groupTest.isNewsletter());

		logger.debug("----> END access to GetGroupInfo test <---- ");
	}

	public void testIsCreator() {
		logger.debug("----> BEGIN access to IsCreator test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroupInfo
		UserGroupInfo groupInfo = new UserGroupInfo();
		groupInfo.setAccessType(GroupAccessType.OPEN);
		groupInfo.setCalendar(true);
		groupInfo.setChat(true);
		User user = testUtility.createUniqueUserInDB();
		groupInfo.setCreator(user.getId());
		groupInfo.setDescription("A UserGroup");
		groupInfo.setDocuments(true);
		groupInfo.setForum(true);
		groupInfo.setName("UserGroup");
		groupInfo.setPassword(null);
		groupInfo.setShortcut("group");
		Long groupId = this.getGroupService().createUserGroup(groupInfo,
				user.getId());
		assertNotNull(groupId);

		// Test
		UserGroupInfo groupTest = this.getGroupService().getGroupInfo(groupId);
		logger.debug("----> " + groupTest.getCreator() + " " + user.getId()
				+ " <---- ");
		assertTrue(this.getGroupService().isCreator(groupTest, user.getId()));
		User user2 = testUtility.createUniqueUserInDB();
		assertFalse(this.getGroupService().isCreator(groupTest, user2.getId()));
		logger.debug("----> END access to IsCreator test <---- ");
	}

	public void testIsUniqueShortcut() {
		logger.debug("----> BEGIN access to IsUniqueShortcut test <---- ");

		// Synchronize with DB
		flush();

		// Create UserGroupInfo
		UserGroupInfo groupInfo = new UserGroupInfo();
		groupInfo.setAccessType(GroupAccessType.OPEN);
		groupInfo.setCalendar(true);
		groupInfo.setChat(true);
		User user = testUtility.createUniqueUserInDB();
		groupInfo.setCreator(user.getId());
		groupInfo.setDescription("A UserGroup");
		groupInfo.setDocuments(true);
		groupInfo.setForum(true);
		groupInfo.setName("UserGroup");
		groupInfo.setPassword(null);
		groupInfo.setShortcut("group");
		Long groupId = this.getGroupService().createUserGroup(groupInfo,
				user.getId());
		assertNotNull(groupId);

		// Test
		assertFalse(this.getGroupService().isUniqueShortcut("group"));
		assertTrue(this.getGroupService().isUniqueShortcut("test"));

		logger.debug("----> END access to IsUniqueShortcut test <---- ");
	}

	/*------------------- getter and setter  -------------------- */

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

}
