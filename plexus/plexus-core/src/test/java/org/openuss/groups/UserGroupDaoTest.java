// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;


/**
 * JUnit Test for Spring Hibernate UserGroupDao class.
 * @see org.openuss.groups.UserGroupDao
 */
public class UserGroupDaoTest extends UserGroupDaoTestBase {
	
	public void testUserGroupDaoCreate() {
		UserGroup userGroup = UserGroup.Factory.newInstance();
		userGroup.setAccessType(GroupAccessType.OPEN);
		userGroup.setForum(true);
		userGroup.setNewsletter(true);
		userGroup.setChat(false);
		userGroup.setDocuments(true);
		userGroup.setCalendar(true);
		userGroup.setName("Group");
		assertNull(userGroup.getId());
		userGroupDao.create(userGroup);
		assertNotNull(userGroup.getId());
	}
}
