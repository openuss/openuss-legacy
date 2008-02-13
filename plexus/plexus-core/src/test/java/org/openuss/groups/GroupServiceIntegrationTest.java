// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import java.util.List;

import org.openuss.TestUtility;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate GroupService class.
 * 
 * @see org.openuss.groups.GroupService
 */
public class GroupServiceIntegrationTest extends
		GroupServiceIntegrationTestBase {

	private TestUtility testUtility;

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

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

		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();

		// Change UserInfoObject
		UserGroupInfo groupInfo = testUtility.getUserGroupDao().toUserGroupInfo(userGroup);
		groupInfo.setName("TestName");
		groupInfo.setDescription("TestDescription");

		// Test
		this.getGroupService().updateUserGroup(groupInfo);
		UserGroup groupTest = testUtility.getUserGroupDao().load(groupInfo.getId());
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
		
		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		
		// Load UserInfoObject
		UserGroupInfo groupInfo = testUtility.getUserGroupDao().toUserGroupInfo(userGroup);
		
		// Delete Group
		this.getGroupService().deleteUserGroup(groupInfo);
		
		// Test
		try{
			UserGroup groupTest = testUtility.getUserGroupDao().load(groupInfo.getId());
			assertNull(groupTest);
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}
		
		logger.debug("----> END access to delete test <---- ");
	}

	public void testAddAspirant(){
		logger.debug("----> BEGIN access to AddAspirant test <---- ");
		
		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);
		
		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);
		
		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao().toUserGroupInfo(userGroup);
		
		// Add User as Moderator
		this.getGroupService().addAspirant(groupInfo, user.getId());
		
		// Test
		boolean asp = false;
		List<User> aspirants = userGroup.getMembership().getAspirants();
		for(User aspirant:aspirants){
			if (aspirant == user){
				asp = true;
			}
		}
		assertEquals(true, asp);
		boolean mem = this.getGroupService().isMember(groupInfo, user.getId());
		assertEquals(false, mem);
			
		logger.debug("----> END access to AddAspirant test <---- ");
	}
	
	public void testAddMember(){
		logger.debug("----> BEGIN access to AddMember test <---- ");
		
		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);
		
		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);
		
		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao().toUserGroupInfo(userGroup);
		
		// Add User as Moderator
		this.getGroupService().addMember(groupInfo, user.getId());
		
		// Test
		boolean mem = this.getGroupService().isMember(groupInfo, user.getId());
		assertEquals(true, mem);
		boolean mod = this.getGroupService().isModerator(groupInfo, user.getId());
		assertEquals(false, mod);		
		
		logger.debug("----> END access to AddMember test <---- ");
	}
	
	public void testAddModerator(){
		logger.debug("----> BEGIN access to AddModerator test <---- ");
		
		// Create UserGroup
		UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup);
		
		// Create User
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user);
		
		// Group to GroupInfo
		UserGroupInfo groupInfo = testUtility.getUserGroupDao().toUserGroupInfo(userGroup);
		
		// Add User as Moderator
		this.getGroupService().addMember(groupInfo, user.getId());
		this.getGroupService().addModerator(groupInfo, user.getId());
		
		// Test
		boolean mod = this.getGroupService().isModerator(groupInfo, user.getId());
		assertEquals(true, mod);
				
		logger.debug("----> END access to AddModerator test <---- ");
	}
	
	public void testAddUserByPassword(){
		logger.debug("----> BEGIN access to AddModerator test <---- ");
		
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
		UserGroupInfo groupInfo = testUtility.getUserGroupDao().toUserGroupInfo(userGroup);
		
		// Add User as Moderator
		this.getGroupService().addUserByPassword(groupInfo, "Password", user.getId());
		
		// Test
		boolean mem = this.getGroupService().isMember(groupInfo, user.getId());
		assertEquals(true, mem);
		boolean mod = this.getGroupService().isModerator(groupInfo, user.getId());
		assertEquals(false, mod);
				
		logger.debug("----> END access to AddModerator test <---- ");
	}
	
	public void testRemoveModerator(){
		
	}
	
	

	public void testRemoveMember(){
		
	}
	

	public void testAcceptAspirant(){
		
	}
	
	public void testRejectAspirant(){
		
	}

	public void testGetMembers(){
		
	}
	
	public void testGetModerators(){
		
	}
	
	public void testGetAspirants(){
		
	}
	
	public void testGetAllGroups(){
		
	}
	
	public void testGetGroupsByUser(){
		
	}
	
	public void testGetGroupInfo(){

	}
	
	public void testIsCreator(){
		
	}
	
	public void isUniqueShortcut(){
		
	}

}
