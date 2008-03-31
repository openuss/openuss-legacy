// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

/**
 * @see org.openuss.discussion.DiscussionWatch
 */
public class DiscussionWatchDaoImpl extends DiscussionWatchDaoBase {
	
    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByTopic(int, org.openuss.discussion.Topic)
     */
	@Override
    public java.util.List findByTopic(final int transform, final org.openuss.discussion.Topic topic)
    {
        return this.findByTopic(transform, "from org.openuss.discussion.DiscussionWatch as discussionWatch where discussionWatch.discussionWatchPk.topic = ?", topic);
    }
	
    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByUser(int, org.openuss.security.User)
     */
	@Override
    public java.util.List findByUser(final int transform, final org.openuss.security.User user)
    {
        return this.findByUser(transform, "from org.openuss.discussion.DiscussionWatch as discussionWatch where discussionWatch.discussionWatchPk.user = ?", user);
    }
    
    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByTopicAndUser(int, org.openuss.discussion.Topic, org.openuss.security.User)
     */
	@Override
    public java.lang.Object findByTopicAndUser(final int transform, final org.openuss.discussion.Topic topic, final org.openuss.security.User user)
    {
        return this.findByTopicAndUser(transform, "from org.openuss.discussion.DiscussionWatch as discussionWatch where discussionWatch.discussionWatchPk.topic = ? and discussionWatch.discussionWatchPk.user = ?", topic, user);
    }
}