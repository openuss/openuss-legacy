// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;


/**
 * JUnit Test for Spring Hibernate GroupDao class.
 * @see org.openuss.security.GroupDao
 */
public class GroupDaoTest extends GroupDaoTestBase {
	
	public void testGroupDaoCreate() {
		Group group = new GroupImpl();
		group.setName("group");
		group.setLabel(" ");
		group.setGroupType(GroupType.UNDEFINED);
		assertNull(group.getId());
		groupDao.create(group);
		assertNotNull(group.getId());
	}
}