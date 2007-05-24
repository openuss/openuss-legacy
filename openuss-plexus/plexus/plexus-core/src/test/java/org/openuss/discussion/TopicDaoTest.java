// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.List;

import org.openuss.TestUtility;
import org.openuss.security.User;
import org.openuss.viewtracking.DomainViewState;
import org.openuss.viewtracking.DomainViewStateDao;
import org.openuss.viewtracking.DomainViewStatePK;
import org.openuss.viewtracking.ViewState;


/**
 * JUnit Test for Spring Hibernate TopicDao class.
 * @see org.openuss.discussion.TopicDao
 */
public class TopicDaoTest extends TopicDaoTestBase {
	
	public DomainViewStateDao domainViewStateDao;
	
	public TestUtility testUtility;
	
	public ForumDao forumDao;
	
	public ForumWatchDao forumWatchDao;
	
	public DiscussionWatchDao discussionWatchDao;
	
	
	
	
	public void testTopicDaoViewState() {
		Forum forum = Forum.Factory.newInstance();
		forum.setDomainIdentifier(testUtility.unique());
		forum.setReadOnly(false);
		
		forumDao.create(forum);
		
		User user = testUtility.createUserInDB();

		Topic topic1 = createTopic(user, forum);
		createViewState(user, topic1);
		
		Topic topic2 = createTopic(user, forum);
		createViewState(user, topic2);
		
		createViewState(testUtility.createUserInDB(), topic2);
		
		createTopic(user, forum);
		
		commit();
		
		List<TopicInfo> topics = topicDao.loadTopicsWithViewState(forum, user);
		assertEquals(3, topics.size());
		assertEquals(ViewState.MODIFIED, topics.get(0).getViewState());
		assertEquals(ViewState.MODIFIED, topics.get(1).getViewState());
		assertEquals(ViewState.NEW, topics.get(2).getViewState());
		
	}

	private DomainViewState createViewState(User user, Topic topic) {
		DomainViewState viewState = DomainViewState.Factory.newInstance();
		DomainViewStatePK pk = new DomainViewStatePK();
		pk.setUserIdentifier(user.getId());
		pk.setDomainIdentifier(topic.getId());
		viewState.setDomainViewStatePk(pk);
		viewState.setViewState(ViewState.MODIFIED);
		domainViewStateDao.create(viewState);
		return viewState;
	}

	private Topic createTopic(User user, Forum forum) {
		Topic topic = Topic.Factory.newInstance();
		
		forum.addTopic(topic);
		topic.setSubmitter(user);
		topicDao.create(topic);
		forumDao.update(forum);
		
		assertNotNull(topic);
		return topic;
	}

	public void testSearchForUsersToNotify(){
		Forum forum = Forum.Factory.newInstance();
		forum.setDomainIdentifier(testUtility.unique());
		forum.setReadOnly(false);
		
		forumDao.create(forum);
		
		User user = testUtility.createUserInDB();

		Topic topic1 = createTopic(user, forum);
		
		ForumWatch fw = ForumWatch.Factory.newInstance();
		fw.setForum(forum);
		fw.setUser(user);
		getForumWatchDao().create(fw);
		
		DiscussionWatch dw = DiscussionWatch.Factory.newInstance();
		dw.setTopic(topic1);
		dw.setUser(user);
		getDiscussionWatchDao().create(dw);
		commit();
		List list = getTopicDao().findUsersToNotify(topic1, forum);
//		assertEquals(1,list.size());		
	}
	
	public DomainViewStateDao getDomainViewStateDao() {
		return domainViewStateDao;
	}

	public void setDomainViewStateDao(DomainViewStateDao domainViewStateDao) {
		this.domainViewStateDao = domainViewStateDao;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public ForumDao getForumDao() {
		return forumDao;
	}

	public void setForumDao(ForumDao forumDao) {
		this.forumDao = forumDao;
	}

	public DiscussionWatchDao getDiscussionWatchDao() {
		return discussionWatchDao;
	}

	public void setDiscussionWatchDao(DiscussionWatchDao discussionWatchDao) {
		this.discussionWatchDao = discussionWatchDao;
	}

	public ForumWatchDao getForumWatchDao() {
		return forumWatchDao;
	}

	public void setForumWatchDao(ForumWatchDao forumWatchDao) {
		this.forumWatchDao = forumWatchDao;
	}
}