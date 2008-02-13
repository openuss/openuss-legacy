// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

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

	public void testAddModerator(){
		
	}
	
	public void testRemoveModerator(){
		
	}
	
	public void testAddMember(){
		
	}
	
	public void testAssUserByPassword(){
		
	}

	public void testRemoveMember(){
		
	}
	
	public void testAddAspirant(){
		
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
	
	public void testIsModerator(){
		
	}
	
	public void testIsMember(){
		
	}
	
	public void testIsCreator(){
		
	}
	
	public void isUniqueShortcut(){
		
	}

}
