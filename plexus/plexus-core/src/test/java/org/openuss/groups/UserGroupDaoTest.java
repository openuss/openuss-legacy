// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import org.openuss.TestUtility;
import org.openuss.security.Group;
import org.openuss.security.Membership;
import org.openuss.security.UserDao;

/**
 * JUnit Test for Spring Hibernate UserGroupDao class.
 * 
 * @see org.openuss.groups.UserGroupDao
 */
public class UserGroupDaoTest extends UserGroupDaoTestBase {

	private TestUtility testUtility;
	private UserDao userDao;

	// add Test for Unique Shortcut

	public void testUserGroupDaoCreate() {
		UserGroup userGroup = new UserGroupImpl();
		userGroup.setName("UserGroup");
		userGroup.setShortcut("group");
		userGroup.setCreator(testUtility.createUniqueUserInDB());
		userGroup.setMembership(Membership.Factory.newInstance());
		userGroup.setModeratorsGroup(generateGroup("moderator"));
		userGroup.setMembersGroup(generateGroup("member"));
		userGroup.setAccessType(GroupAccessType.OPEN);
		userGroup.setForum(true);
		userGroup.setNewsletter(true);
		userGroup.setChat(false);
		userGroup.setDescription("A UserGroup");
		userGroup.setDocuments(true);
		userGroup.setCalendar(true);
		assertNull(userGroup.getId());
		userGroupDao.create(userGroup);
		assertNotNull(userGroup.getId());
	}

	public void testUserGroupToGroupInfo() {
		
		// Create UserGroup Entity
        UserGroup userGroup = testUtility.createUniqueUserGroupInDB();
		assertNotNull(userGroup.getId());
		
		// Test
		UserGroupInfo groupInfo = this.getUserGroupDao().toUserGroupInfo(userGroup);
		assertNotNull(groupInfo);
		assertEquals(userGroup.getAccessType(), groupInfo.getAccessType());
		assertEquals(userGroup.getCreator().getId() ,groupInfo.getCreator());
		assertEquals(userGroup.getDescription(), groupInfo.getDescription());
		assertEquals(userGroup.getName(), groupInfo.getName());
		assertEquals(userGroup.getPassword(), groupInfo.getPassword());
		assertEquals(userGroup.getShortcut(), groupInfo.getShortcut());
		assertEquals(userGroup.getCalendar().booleanValue(), groupInfo.isCalendar());
		assertEquals(userGroup.getChat().booleanValue(), groupInfo.isChat());
		assertEquals(userGroup.getDocuments().booleanValue(), groupInfo.isDocuments());
		assertEquals(userGroup.getForum().booleanValue(),groupInfo.isForum());
		assertEquals(userGroup.getNewsletter().booleanValue(),groupInfo.isNewsletter());
	}
	
	public void testUserGroupInfoToEntitiy(){ // NOPMD by devopenuss on 11.03.08 14:18
		//Create UserGroupInfo
		UserGroupInfo groupInfo = new UserGroupInfo();
		groupInfo.setAccessType(GroupAccessType.OPEN);
		groupInfo.setCalendar(true);
		groupInfo.setChat(true);
		groupInfo.setCreator(testUtility.createUniqueUserInDB().getId());
		groupInfo.setDescription("A UserGroup");
		groupInfo.setDocuments(true);
		groupInfo.setForum(true);
		groupInfo.setName("UserGroup");
		groupInfo.setShortcut("group");
		
		// Test toEntity
		UserGroup group = this.getUserGroupDao().userGroupInfoToEntity(groupInfo);
		assertNotNull(group);
		assertEquals(groupInfo.getAccessType(), group.getAccessType());
		assertEquals(groupInfo.getDescription(), group.getDescription());
		assertEquals(groupInfo.getName(), group.getName());
		assertEquals(groupInfo.getPassword(), group.getPassword());
		assertEquals(groupInfo.getShortcut(), group.getShortcut());
		assertEquals(groupInfo.isCalendar(), group.getCalendar().booleanValue());
		assertEquals(groupInfo.isChat(), group.getChat().booleanValue());
		assertEquals(groupInfo.isDocuments(), group.getDocuments().booleanValue());
		assertEquals(groupInfo.isForum(), group.getForum().booleanValue());
		assertEquals(groupInfo.isNewsletter(), group.getNewsletter().booleanValue());	
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	/*------------------- private methods -------------------- */
	private Group generateGroup(String name){
		Group group = Group.Factory.newInstance();
		group.setName(name);
		return group;
	}
	
}
