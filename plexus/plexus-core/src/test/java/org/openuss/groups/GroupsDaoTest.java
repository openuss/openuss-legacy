// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;


/**
 * JUnit Test for Spring Hibernate GroupsDao class.
 * @see org.openuss.groups.GroupsDao
 */
public class GroupsDaoTest extends GroupsDaoTestBase {
	
	public void testGroupsDaoCreate() {
		Groups groups = Groups.Factory.newInstance();
		groups.setAccessType(AccessType.OPEN);
		groups.setForum(false);
		groups.setNewsletter(false);
		groups.setChat(false);
		groups.setDocuments(false);
		groups.setCalendar(false);
		groups.setName("Group");
		assertNull(groups.getId());
		groupsDao.create(groups);
		assertNotNull(groups.getId());
	}
}
