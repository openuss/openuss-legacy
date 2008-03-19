// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.openuss.security.User;
import org.openuss.viewtracking.ViewState;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @see org.openuss.discussion.Topic
 */
public class TopicDaoImpl extends org.openuss.discussion.TopicDaoBase {
	/**
	 * @see org.openuss.discussion.TopicDao#toTopicInfo(org.openuss.discussion.Topic,
	 *      org.openuss.discussion.TopicInfo)
	 */
	public void toTopicInfo(Topic sourceEntity, TopicInfo targetVO) {
		super.toTopicInfo(sourceEntity, targetVO);
		if (sourceEntity.getForum() != null || sourceEntity.getId() != null)
			targetVO.setForumId(sourceEntity.getForum().getId());
		if (sourceEntity.getFirst() != null) {
			if (sourceEntity.getFirst().getTitle() != null)
				targetVO.setTitle(sourceEntity.getFirst().getTitle());
			if (sourceEntity.getFirst().getCreated() != null)
				targetVO.setCreated(sourceEntity.getFirst().getCreated());
			if (sourceEntity.getFirst().getSubmitterName() != null)
				targetVO.setSubmitter(sourceEntity.getFirst().getSubmitterName());
		}
		if (sourceEntity.getLastPostDate() != null)
			targetVO.setLastPost(sourceEntity.getLastPostDate());
		if (sourceEntity.getLastPostSubmitterName() != null)
			targetVO.setLastPostSubmitterName(sourceEntity.getLastPostSubmitterName());
		if (sourceEntity.getAnswerCount() != null)
			targetVO.setAnswerCount(sourceEntity.getAnswerCount());
	}

	/**
	 * @see org.openuss.discussion.TopicDao#toTopicInfo(org.openuss.discussion.Topic)
	 */
	public TopicInfo toTopicInfo(final Topic entity) {
		final TopicInfo target = new TopicInfo();
		this.toTopicInfo(entity, target);
		return target;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Topic loadTopicFromTopicInfo(TopicInfo topicInfo) {
		if (topicInfo.getId() == null) {
			return Topic.Factory.newInstance();
		}
		return this.load(topicInfo.getId());
	}

	/**
	 * @see org.openuss.discussion.TopicDao#topicInfoToEntity(org.openuss.discussion.TopicInfo)
	 */
	public Topic topicInfoToEntity(TopicInfo topicInfo) {
		Topic entity = this.loadTopicFromTopicInfo(topicInfo);
		if (entity == null) {
			entity = Topic.Factory.newInstance();
		}
		this.topicInfoToEntity(topicInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.discussion.TopicDao#topicInfoToEntity(org.openuss.discussion.TopicInfo,
	 *      org.openuss.discussion.Topic)
	 */
	public void topicInfoToEntity(TopicInfo sourceVO, Topic targetEntity, boolean copyIfNull) {
		super.topicInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

	@Override
	protected List handleLoadTopicsWithViewState(final Forum forum, final User user) throws Exception {
		// FIXME - Need to extend andromda generator to support association classes
		// Hibernate doesn't support left outer join on object that doesn't have a association.
		// Therefore ViewState should be an associaction class between topic and user, but
		// this isn't support by andromda 3.2 yet.
		
		// So the workaround are these two queries and the memory join.
		
		final String queryString = 
			" SELECT topic.ID,  v.VIEW_STATE " +
			" FROM DISCUSSION_TOPIC as topic LEFT OUTER JOIN TRACKING_VIEWSTATE as v " +
			" ON topic.id = v.DOMAIN_IDENTIFIER and v.USER_IDENTIFIER = :userId " +
			" WHERE topic.FORUM_FK = :forumId ";
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createSQLQuery(queryString);
				queryObject.setParameter("forumId", forum.getId());
				queryObject.setParameter("userId", user.getId());
				List<Object[]> results = queryObject.list();
				
				Map<Long, ViewState> viewstates = new HashMap<Long, ViewState>();
				for(Object[] obj : results) {
					Long topicId = ((BigInteger) obj[0]).longValue();
					ViewState state = ViewState.NEW;
					if (obj[1] != null) {
						state = ViewState.fromInteger((Integer)obj[1]);
					} 
					viewstates.put(topicId, state);
				}
				
				List<TopicInfo> topics = findByForum(TRANSFORM_TOPICINFO, forum);
				// inject view state
				for(TopicInfo info: topics) {
					info.setViewState(viewstates.get(info.getId()));
				}
				return topics;
			}
		}, true);
	}

	/**
	 * finds all users, which have specified a topicwatch for a topic. users with specified topicwatch
	 * without any viewstate are ignored, because a viewstate is defined while viewing a topic and users 
	 * need to view a topic to define a topicwatch.
	 */
	@Override
	protected List handleFindUsersToNotifyByTopic(final Topic topic) throws Exception {
		final String queryString = 
			" SELECT DISTINCT u" +
			" FROM UserImpl u, TopicImpl as t, DomainViewStateImpl as v, DiscussionWatchImpl as tw" +
			" WHERE"+
			" v.viewState = :viewStateRead and v.domainViewStatePk.domainIdentifier = :topicId"+
			" and tw.topic.id = v.domainViewStatePk.domainIdentifier and tw.user.id = v.domainViewStatePk.userIdentifier" +
			" and u = tw.user";			
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameter("topicId", topic.getId());				
				queryObject.setParameter("viewStateRead", ViewState.READ);
				List<Object> results = queryObject.list();				
				return results; 
			}
		}, true);		
	}
	

	/**
	 * finds all users, which defined a forumwatch, when a new post is added to a topic they have read. 
	 * (new topics are not handled by this method, if there is a topic they haven't read yet, they are
	 * notified on creation of this topic)
	 * it is assumed that given topic is part of given forum.
	 */
	@Override
	protected List handleFindUsersToNotifyByForum(final Topic topic, final Forum forum) throws Exception {
		final String queryString = 
			" SELECT DISTINCT u" +
			" FROM UserImpl as u, TopicImpl as t, DomainViewStateImpl as v, ForumWatchImpl as fw" +
			" WHERE"+
			" v.viewState = :viewStateRead and v.domainViewStatePk.domainIdentifier = :topicId"+
			" and fw.user.id = v.domainViewStatePk.userIdentifier" +
			" and u = fw.user and fw.forum.id = :forumId";
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameter("forumId", forum.getId());				
				queryObject.setParameter("topicId", topic.getId());				
				queryObject.setParameter("viewStateRead", ViewState.READ);
				List<Object> results = queryObject.list();				
				return results; 
			}
		}, true);		
	}

}