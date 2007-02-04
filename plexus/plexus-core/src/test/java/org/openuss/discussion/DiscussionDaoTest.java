// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;


/**
 * JUnit Test for Spring Hibernate DiscussionDao class.
 * @see org.openuss.discussion.DiscussionDao
 */
public class DiscussionDaoTest extends DiscussionDaoTestBase {
	
	public void testDiscussionDaoCreate() {
		Discussion discussion = new DiscussionImpl();
		assertNull(discussion.getId());
		discussionDao.create(discussion);
		assertNotNull(discussion.getId());
	}
}