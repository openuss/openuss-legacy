// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.foundation.DomainObject;
import org.openuss.security.User;
import org.openuss.system.SystemProperties;

/**
 * @see org.openuss.discussion.DiscussionService
 */
public class DiscussionServiceImpl extends DiscussionServiceBase {

	public static final Logger logger = Logger.getLogger(DiscussionServiceImpl.class);

	/**
	 * @see org.openuss.discussion.DiscussionService#createTopic(org.openuss.discussion.PostInfo,
	 *      java.lang.Long)
	 */
	protected void handleCreateTopic(final PostInfo postInfo, final ForumInfo forumInfo) {
		Validate.notNull(postInfo, "postInfo must not be null");
		Validate.notNull(forumInfo, "forumInfo must not be null");

		// create topic
		Topic topic = Topic.Factory.newInstance();
		topic.setReadOnly(false);

		topic.setSubmitter(getSecurityService().getUserObject(
				getSecurityService().getUserByName(postInfo.getSubmitter())));
		Forum forum = getForumDao().load(forumInfo.getId());
		// TODO define a add topic method in forum
		forum.getTopics().add(topic);
		topic.setForum(forum);

		getTopicDao().create(topic);
		getForumDao().update(forum);

		// add object identity to security
		getSecurityService().createObjectIdentity(topic, topic.getForum().getDomainIdentifier());

		TopicInfo topicInfo = getTopicDao().toTopicInfo(topic);

		// add first post
		handleAddPost(postInfo, topicInfo);
		topic = getTopicDao().load(topicInfo.getId());

		getPostDao().toPostInfo(topic.getFirst(), postInfo);

		sendNotificationsToForumWatchers(topic, topic.getForum());

	}

	private void sendNotificationsToForumWatchers(final Topic topic, final Forum forum) {
		List<ForumWatch> watches = getForumWatchDao().findByForum(forum);
		List<User> emails = new ArrayList<User>();
		for (ForumWatch watch : watches) {
			emails.add(watch.getForumWatchPk().getUser());
		}
		logEmailAdresses(emails);
		sendNotificationEmail(emails, topic);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#deleteTopic(org.openuss.discussion.TopicInfo)
	 */
	protected void handleDeleteTopic(final TopicInfo topicInfo) {
		Validate.notNull(topicInfo, "topicInfo must not be null.");
		removeTopic(getTopicDao().topicInfoToEntity(topicInfo));
	}

	private void removeTopic(final Topic topic) {
		removeAllDiscussionWatchesOfTopic(topic);
		for (Post post : topic.getPosts()) {
			getTrackingService().remove(post);
			getDocumentService().remove(post);
		}

		getTopicDao().update(topic);
		// remove viewstates to topic
		getTrackingService().remove(topic);
		// remove topic itself
		getTopicDao().remove(topic);
		// remove object identity from security context
		getSecurityService().removeObjectIdentity(topic);
	}

	private void removeAllDiscussionWatchesOfTopic(final Topic topic) {
		getDiscussionWatchDao().remove(getDiscussionWatchDao().findByTopic(topic));
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#addPost(org.openuss.discussion.PostInfo,
	 *      org.openuss.discussion.TopicInfo)
	 */
	protected void handleAddPost(final PostInfo postInfo, final TopicInfo topicInfo) {
		Validate.notNull(postInfo, "PostInfo must not be null.");
		Validate.notNull(topicInfo, "TopicInfo must not be null.");

		Topic topic = getTopicDao().load(topicInfo.getId());

		Post post = getPostDao().postInfoToEntity(postInfo);

		post.setSubmitter(getSecurityService().getUserObject(
				getSecurityService().getUserByName(postInfo.getSubmitter())));

		addPostToTopicAndPersist(topic, post);

		getSecurityService().createObjectIdentity(post, topicInfo.getId());

		getDocumentService().diffSave(post, postInfo.getAttachments());

		sendNotifications(topic, topic.getForum());
		getTrackingService().setModified(topic);
		getTrackingService().setRead(topic);

		getPostDao().toPostInfo(post, postInfo);

	}

	private void sendNotifications(final Topic topic, final Forum forum) {
		List<User> emailsByTopic = getTopicDao().findUsersToNotifyByTopic(topic);
		List<User> emailsByForum = getTopicDao().findUsersToNotifyByForum(topic, forum);
		Set<User> emails = new HashSet();
		emails.addAll(emailsByTopic);
		emails.addAll(emailsByForum);
		logEmailAdresses(emails);
		sendNotificationEmail(new ArrayList(emails), topic);
		logger.debug("got users to notify");
	}

	/**
	 * obsolete method, which prints out email adresses of users to notify just
	 * for testing purposes TODO remove me
	 */
	private void logEmailAdresses(final Collection<User> emails) {
		logger.debug("email adresses to notify:");
		for (User adress : emails) {
			logger.debug("----------- " + adress.getEmail() + "-----------");
		}
	}

	private void addPostToTopicAndPersist(final Topic topic, final Post post) {
		topic.addPost(post);
		getPostDao().create(post);
		getTopicDao().update(topic);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#deletePost(org.openuss.discussion.PostInfo)
	 */
	protected void handleDeletePost(final PostInfo postInfo) {
		Validate.notNull(postInfo, "post must not be null");
		Post post = getPostDao().postInfoToEntity(postInfo);
		Topic topic = post.getTopic();
		if (topic != null) {
			topic.removePost(post);
			if (topic.getPosts().isEmpty()) {
				removeTopic(topic);
			} else {
				getDocumentService().remove(post);
				getSecurityService().removeObjectIdentity(postInfo);
				getTopicDao().update(topic);
				getPostDao().remove(post);
			}
		}

	}

	/**
	 * @see org.openuss.discussion.DiscussionService#updatePost(org.openuss.discussion.PostInfo)
	 */
	protected void handleUpdatePost(final PostInfo postInfo) {
		Post post = getPostDao().load(postInfo.getId());
		post.setText(postInfo.getText());
		post.setTitle(postInfo.getTitle());
		post.setEditor(getSecurityService().getUserObject(getSecurityService().getUserByName(postInfo.getEditor())));
		post.setLastModification(postInfo.getLastModification());
		getPostDao().update(post);

		getDocumentService().diffSave(post, postInfo.getAttachments());

		getPostDao().toPostInfo(post, postInfo);

		sendNotifications(post.getTopic(), post.getTopic().getForum());

		getTrackingService().setModified(post.getTopic());
		getTrackingService().setRead(post.getTopic());

	}

	/**
	 * @see org.openuss.discussion.DiscussionService#getPost(org.openuss.discussion.PostInfo)
	 */
	protected PostInfo handleGetPost(final PostInfo post) {
		Validate.notNull(post);
		Validate.notNull(post.getId());
		PostInfo postInfo = (PostInfo) getPostDao().load(PostDao.TRANSFORM_POSTINFO, post.getId());
		if (postInfo != null) {
			List<FileInfo> attachments = getAttachments(postInfo);
			postInfo.setAttachments(attachments);
			postInfo.setUserIsSubmitter(postInfo.getSubmitterId().equals(getSecurityService().getCurrentUser().getId()));
		}
		return postInfo;

	}

	/**
	 * @see org.openuss.discussion.DiscussionService#getPosts(org.openuss.discussion.TopicInfo)
	 */
	protected List handleGetPosts(final TopicInfo topicInfo) {
		Validate.notNull(topicInfo);
		Validate.notNull(topicInfo.getId());

		getTrackingService().setRead(topicInfo);

		Topic topic = getTopicDao().load(topicInfo.getId());
		List<PostInfo> posts = getPostDao().findByTopic(PostDao.TRANSFORM_POSTINFO, topic);
		Collections.sort(posts, new PostInfoComparator());
		for (PostInfo post : posts) {
			post.setUserIsSubmitter(post.getSubmitterId().equals(getSecurityService().getCurrentUser().getId()));
			List<FileInfo> attachments = getDocumentService().getFileEntries(post);
			post.setAttachments(attachments);
		}

		return posts;
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#getTopic(org.openuss.discussion.TopicInfo)
	 */
	protected TopicInfo handleGetTopic(final TopicInfo topic) {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		return (TopicInfo) getTopicDao().load(TopicDao.TRANSFORM_TOPICINFO, topic.getId());
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#getTopics(java.lang.Long)
	 */
	protected List handleGetTopics(final ForumInfo forumInfo) {
		Validate.notNull(forumInfo, "Parameter forum must not be null");
		Validate.notNull(forumInfo.getId(), "Parameter form must provide an valid id.");

		Forum forum = getForumDao().load(forumInfo.getId());
		List<TopicInfo> topics;
		if (forum == null) {
			topics =  new ArrayList<TopicInfo>();
		} else {
			topics = getTopicDao().loadTopicsWithViewState(forum, getCurrentUserObject());
			Collections.sort(topics, new TopicInfoComparator());
		}
		return topics;
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#addTopicWatch(org.openuss.discussion.TopicInfo)
	 */
	protected void handleAddTopicWatch(final TopicInfo topic)  {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		if (!watchesTopic(topic)){
			DiscussionWatch watch = DiscussionWatch.Factory.newInstance();
			watch.setDiscussionWatchPk(new DiscussionWatchPK());
			watch.getDiscussionWatchPk().setTopic(getTopicDao().load(topic.getId()));
			watch.getDiscussionWatchPk().setUser(getCurrentUserObject());
			getDiscussionWatchDao().create(watch);
		}
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#addForumWatch(java.lang.Long)
	 */
	protected void handleAddForumWatch(final ForumInfo forum)  {
		Validate.notNull(forum);
		Validate.notNull(forum.getId());
		if (!watchesForum(forum)){
			ForumWatch watch = ForumWatch.Factory.newInstance();
			watch.setForumWatchPk(new ForumWatchPK());
			watch.getForumWatchPk().setForum(getForumDao().load(forum.getId()));
			watch.getForumWatchPk().setUser(getCurrentUserObject());
			getForumWatchDao().create(watch);
		}
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#removeTopicWatch(org.openuss.discussion.TopicInfo)
	 */
	protected void handleRemoveTopicWatch(final TopicInfo topicInfo) {
		Validate.notNull(topicInfo);
		Validate.notNull(topicInfo.getId());
		Topic topic = getTopicDao().load(topicInfo.getId());
		DiscussionWatch watch = getDiscussionWatchDao().findByTopicAndUser(topic, getCurrentUserObject());
		getDiscussionWatchDao().remove(watch);
	}

	private User getCurrentUserObject() {
		return getSecurityService().getUserObject(getSecurityService().getCurrentUser());
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#removeForumWatch(java.lang.Long)
	 */
	protected void handleRemoveForumWatch(final ForumInfo forum) {
		Validate.notNull(forum);
		Validate.notNull(forum.getId());
		
		Forum f = getForumDao().load(forum.getId());
		User user = getCurrentUserObject();
		ForumWatch fw = getForumWatchDao().findByUserAndForum(user, f);
		if(fw != null) {
			getForumWatchDao().remove(fw);
		}
	}

	/**
	 * @throws DocumentApplicationException 
	 * @see org.openuss.discussion.DiscussionService#addAttachment(org.openuss.discussion.PostInfo,
	 *      org.openuss.documents.FileInfo)
	 */
	protected void handleAddAttachment(final PostInfo post, final FileInfo file) throws DocumentApplicationException {
		Validate.notNull(post);
		Validate.notNull(post.getId());
		Validate.notNull(file);
		FolderInfo parent = getDocumentService().getFolder(post);
		getDocumentService().createFileEntry(file, parent);
	}

	/**
	 * @throws DocumentApplicationException 
	 * @see org.openuss.discussion.DiscussionService#removeAttachment(org.openuss.discussion.PostInfo)
	 */
	protected void handleRemoveAttachment(final PostInfo post, final FileInfo fileInfo) throws DocumentApplicationException {
		Validate.notNull(post);
		Validate.notNull(post.getId());
		Validate.notNull(fileInfo);
		Validate.notNull(fileInfo.getId());
		getDocumentService().removeFolderEntry(fileInfo.getId());
	}

	@Override
	protected void handleAddForum(final ForumInfo forum) {
		Validate.notNull(forum, "forum must not be null");
		Validate.notNull(forum.getDomainIdentifier(), "domain Identifier of forum must not be null");
		Forum forumObject = getForumDao().forumInfoToEntity(forum);
		getForumDao().create(forumObject);
	}

	@Override
	protected void handleChangeEditState(final ForumInfo forumInfo) {
		Validate.notNull(forumInfo);
		Validate.notNull(forumInfo.getId());
		Forum forum = getForumDao().load(forumInfo.getId());
		forum.setReadOnly(!forum.isReadOnly());
		getForumDao().update(forum);
	}

	@Override
	protected void handleChangeEditState(final TopicInfo topicInfo) {
		Validate.notNull(topicInfo);
		Validate.notNull(topicInfo.getId());
		Topic topic = getTopicDao().load(topicInfo.getId());
		topic.setReadOnly(!topic.isReadOnly());
		getTopicDao().update(topic);
	}

	@Override
	protected ForumInfo handleGetForum(final DomainObject domainObject) {
		Validate.notNull(domainObject, "DomainObject must not be null.");
		Validate.notNull(domainObject.getId(), "DomainObject must provide an id.");
		Forum forum = getForumDao().findByDomainIdentifier(domainObject.getId());
		if (forum == null) {
			ForumInfo forumInfo = new ForumInfo();
			forumInfo.setDomainIdentifier(domainObject.getId());
			forumInfo.setReadOnly(false);
			handleAddForum(forumInfo);
			forum = getForumDao().findByDomainIdentifier(domainObject.getId());
		}
		return getForumDao().toForumInfo(forum);
	}

	@Override
	protected void handleAddHit(final TopicInfo topicInfo) {
		Validate.notNull(topicInfo);
		Validate.notNull(topicInfo.getId());
		Topic topic = getTopicDao().load(topicInfo.getId());
		topic.setHits(topic.getHits() + 1);
		topicInfo.setHits(topic.getHits());
		getTopicDao().update(topic);
	}

	@Override
	protected List handleGetAttachments(final PostInfo post) {
		Validate.notNull(post);
		Validate.notNull(post.getId());
		return getDocumentService().getFileEntries(post);
	}

	@Override
	protected boolean handleWatchesForum(final ForumInfo forum){
		Validate.notNull(forum);
		Validate.notNull(forum.getId());
		return (getForumWatchDao().findByUserAndForum(
				getCurrentUserObject(),
				getForumDao().load(forum.getId())) != null);
	}

	@Override
	protected boolean handleWatchesTopic(final TopicInfo topic) {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		return (getDiscussionWatchDao().findByTopicAndUser(getTopicDao().load(topic.getId()),
				getCurrentUserObject()) != null);
	}

	private void sendNotificationEmail(final List<User> recipients, final Topic topic) {
		if (recipients == null || recipients.isEmpty()) {
			return;
		}
		try {
			String link = "/views/secured/discussion/discussionthread.faces?topic=" + topic.getId() + "&course="
					+ topic.getForum().getDomainIdentifier();

			link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

			Map parameters = new HashMap();
			parameters.put("topicname", topic.getTitle());
			
//			FIXME Dependency from diskussion to lecture is not allowed
			
//			Course course = getCourseDao().load(topic.getForum().getDomainIdentifier());
//			CourseInfo courseInfo = getCourseDao().toCourseInfo(course);
			
//			parameters.put("coursename", courseInfo.getName());
			
			// FIXME Use forum name and an application event if course is updated
			parameters.put("forumname", "ISSUE: Find domain name");
			parameters.put("topiclink", link);

			getMessageService().sendMessage("user.discussion.watch.sender", "user.discussion.watch.subject",
					"discussionnotification", parameters, recipients);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

}