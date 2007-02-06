// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;


/**
 * JUnit Test for Spring Hibernate ForumDao class.
 * @see org.openuss.discussion.ForumDao
 */
public class ForumDaoTest extends ForumDaoTestBase {
	
	public void testForumDaoCreate() {
		//TODO Auto-generated test case
		Forum forum = new ForumImpl();
		forum.setName(" ");
		forum.setPosition(1);
		assertNull(forum.getId());
		forumDao.create(forum);
		assertNotNull(forum.getId());
	}
}