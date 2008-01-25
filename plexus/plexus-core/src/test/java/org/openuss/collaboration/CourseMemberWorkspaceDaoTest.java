// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.collaboration;


/**
 * JUnit Test for Spring Hibernate CourseMemberWorkspaceDao class.
 * @see org.openuss.collaboration.CourseMemberWorkspaceDao
 */
public class CourseMemberWorkspaceDaoTest extends CourseMemberWorkspaceDaoTestBase {
	
	public void testCourseMemberWorkspaceDaoCreate() {
		CourseMemberWorkspace courseMemberWorkspace = CourseMemberWorkspace.Factory.newInstance();
		assertNull(courseMemberWorkspace.getId());
		courseMemberWorkspaceDao.create(courseMemberWorkspace);
		assertNotNull(courseMemberWorkspace.getId());
	}
}
