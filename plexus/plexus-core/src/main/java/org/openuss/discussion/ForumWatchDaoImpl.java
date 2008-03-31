// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

/**
 * @see org.openuss.discussion.ForumWatch
 */
public class ForumWatchDaoImpl extends ForumWatchDaoBase {
	
    /**
     * @see org.openuss.discussion.ForumWatchDao#findByUser(int, org.openuss.security.User)
     */
	@Override
    public java.util.List findByUser(final int transform, final org.openuss.security.User user)
    {
        return this.findByUser(transform, "from org.openuss.discussion.ForumWatch as forumWatch where forumWatch.forumWatchPk.user = ?", user);
    }
    
    /**
     * @see org.openuss.discussion.ForumWatchDao#findByForum(int, org.openuss.discussion.Forum)
     */
	@Override
    public java.util.List findByForum(final int transform, final org.openuss.discussion.Forum forum)
    {
        return this.findByForum(transform, "from org.openuss.discussion.ForumWatch as forumWatch where forumWatch.forumWatchPk.forum = ?", forum);
    }
    
    /**
     * @see org.openuss.discussion.ForumWatchDao#findByUserAndForum(int, org.openuss.security.User, org.openuss.discussion.Forum)
     */
	@Override
    public java.lang.Object findByUserAndForum(final int transform, final org.openuss.security.User user, final org.openuss.discussion.Forum forum)
    {
        return this.findByUserAndForum(transform, "from org.openuss.discussion.ForumWatch as forumWatch where forumWatch.forumWatchPk.user = ? and forumWatch.forumWatchPk.forum = ?", user, forum);
    }
	
}