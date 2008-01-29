// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;


/**
 * JUnit Test for Spring Hibernate GroupMemberDao class.
 * @see org.openuss.groups.GroupMemberDao
 */
public class GroupMemberDaoTest extends GroupMemberDaoTestBase {
	
	public void testGroupMemberDaoCreate() {
		GroupMember groupMember = GroupMember.Factory.newInstance();
		groupMember.setMemberType(GroupMemberType.MEMBER);
		assertNull(groupMember.getId());
		groupMemberDao.create(groupMember);
		assertNotNull(groupMember.getId());
	}
}
