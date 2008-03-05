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
	protected void handleCreateTopic(PostInfo postInfo, ForumInfo forumInfo) throws Exception {
		Validate.notNull(postInfo, "postInfo must not be null");
		Validate.notNull(forumInfo, "forumInfo must not be null");

		// create topic
		Topic topic = Topic.Factory.newInstance();
		topic.setReadOnly(false);

		topic.setSubmitter(getSecurityService().getUserObject(getSecurityService().getUserByName(postInfo.getSubmitter())));
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

	@SuppressWarnings("unchecked")
	private void sendNotificationsToForumWatchers(Topic topic, Forum forum) {
		List<ForumWatch> watches = getForumWatchDao().findByForum(forum);
		List<User> emails = new ArrayList<User>();
		for (ForumWatch watch : watches){
			emails.add(watch.getUser());			
		}
		logEmailAdresses(emails);
		sendNotificationEmail(emails, topic);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#deleteTopic(org.openuss.discussion.TopicInfo)
	 */
	@SuppressWarnings("unchecked")
	protected void handleDeleteTopic(TopicInfo topicInfo) throws Exception {
		Validate.notNull(topicInfo, "topicInfo must not be null.");
		Topic topic = getTopicDao().topicInfoToEntity(topicInfo);
		removeTopic(topic);
	}

	private void removeTopic(Topic topic) {
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

	@SuppressWarnings("unchecked")
	private void removeAllDiscussionWatchesOfTopic(Topic topic) {
		List<DiscussionWatch> topicWatches = getDiscussionWatchDao().findByTopic(topic);
		getDiscussionWatchDao().remove(topicWatches);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#addPost(org.openuss.discussion.PostInfo,
	 *      org.openuss.discussion.TopicInfo)
	 */
	protected void handleAddPost(PostInfo postInfo, TopicInfo topicInfo) throws Exception {
		Validate.notNull(postInfo, "PostInfo must not be null.");
		Validate.notNull(topicInfo, "TopicInfo must not be null.");
		
		Topic topic = getTopicDao().topicInfoToEntity(topicInfo);
		
		Post post = getPostDao().postInfoToEntity(postInfo);
		
		post.setSubmitter(getSecurityService().getUserObject(getSecurityService().getUserByName(postInfo.getSubmitter())));

		addPostToTopicAndPersist(topic, post);
		
		getSecurityService().createObjectIdentity(post, topicInfo.getId());

		getDocumentService().diffSave(post, postInfo.getAttachments());
		
		sendNotifications(topic, topic.getForum());
		getTrackingService().setModified(topic);
		getTrackingService().setRead(topic);
		
		getPostDao().toPostInfo(post, postInfo);
		
	}
	
	@SuppressWarnings("unchecked")
	private void sendNotifications(Topic topic, Forum forum){
		List<User> emailsByTopic = getTopicDao().findUsersToNotifyByTopic(topic);
		List<User> emailsByForum = getTopicDao().findUsersToNotifyByForum(topic, forum);
		Set<User> emails = new HashSet();
		emails.addAll(emailsByTopic);
		emails.addAll(emailsByForum);		
		logEmailAdresses(emails);
		List l = new ArrayList(emails);
		sendNotificationEmail(l, topic);
		logger.debug("got users to notify");
	}
	
	/**
	 * obsolete method, which prints out email adresses of users to notify
	 * just for testing purposes
	 * TODO remove me 
	 */
	private void logEmailAdresses(Collection<User> emails){
		logger.debug("email adresses to notify:");
		for (User adress: emails){
			logger.debug("----------- " + adress.getEmail()+ "-----------");
		}
	}

	private void addPostToTopicAndPersist(Topic topic, Post post) {
		topic.addPost(post);
		getPostDao().create(post);
		getTopicDao().update(topic);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#deletePost(org.openuss.discussion.PostInfo)
	 */
	protected void handleDeletePost(PostInfo postInfo) throws Exception {
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
	@SuppressWarnings("unchecked")
	protected void handleUpdatePost(PostInfo postInfo) throws Exception {
		Post post = getPostDao().load(postInfo.getId());
		post.setText(postInfo.getText());
		post.setTitle(postInfo.getTitle());
		post.setEditor(getSecurityService().getUserObject(getSecurityService().getUserByName(postInfo.getEditor())));
		post.setLastModification(postInfo.getLastModification());
		getPostDao().update(post);
		
		getDocumentService().diffSave(post, postInfo.getAttachments());

		getPostDao().toPostInfo(post,postInfo);
		
		sendNotifications(post.getTopic(), post.getTopic().getForum());
		
		getTrackingService().setModified(post.getTopic());
		getTrackingService().setRead(post.getTopic());

	}

	/**
	 * @see org.openuss.discussion.DiscussionService#getPost(org.openuss.discussion.PostInfo)
	 */
	@SuppressWarnings("unchecked")
	protected PostInfo handleGetPost(PostInfo post) throws Exception {
		Validate.notNull(post);
		Validate.notNull(post.getId());
		PostInfo postInfo = (PostInfo) getPostDao().load(PostDao.TRANSFORM_POSTINFO, post.getId());
		if (postInfo == null) {
			return null;  
		}
		List<FileInfo> attachments = getAttachments(postInfo);
		postInfo.setAttachments(attachments);
		postInfo.setUserIsSubmitter(postInfo.getSubmitterId().equals(getSecurityService().getCurrentUser().getId()));
		return postInfo;

	}

	/**
	 * @see org.openuss.discussion.DiscussionService#getPosts(org.openuss.discussion.TopicInfo)
	 */
	@SuppressWarnings("unchecked")
	protected List handleGetPosts(TopicInfo topicInfo) throws Exception {
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
	protected TopicInfo handleGetTopic(TopicInfo topic)	throws Exception {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		return (TopicInfo) getTopicDao().load(TopicDao.TRANSFORM_TOPICINFO, topic.getId());
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#getTopics(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	protected List handleGetTopics(ForumInfo forumInfo) throws Exception {
		Validate.notNull(forumInfo, "Parameter forum must not be null");
		Validate.notNull(forumInfo.getId(), "Parameter form must provide an valid id.");
		
		Forum forum = getForumDao().load(forumInfo.getId());
		if (forum == null) {
			return new ArrayList<TopicInfo>();
		} else {
			List <TopicInfo> topics = getTopicDao().loadTopicsWithViewState(forum, getSecurityService().getUserObject(getSecurityService().getCurrentUser()));
			Collections.sort(topics, new TopicInfoComparator());
			return topics;
		}
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#addTopicWatch(org.openuss.discussion.TopicInfo)
	 */
	protected void handleAddTopicWatch(TopicInfo topic) throws Exception {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		DiscussionWatch dw = DiscussionWatch.Factory.newInstance();
		dw.setTopic(getTopicDao().load(topic.getId()));
		dw.setUser(getSecurityService().getUserObject(getSecurityService().getCurrentUser()));
		getDiscussionWatchDao().create(dw);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#addForumWatch(java.lang.Long)
	 */
	protected void handleAddForumWatch(ForumInfo forum) throws Exception {
		Validate.notNull(forum);
		Validate.notNull(forum.getId());
		ForumWatch fw = ForumWatch.Factory.newInstance();
		fw.setForum(getForumDao().load(forum.getId()));
		fw.setUser(getSecurityService().getUserObject(getSecurityService().getCurrentUser()));
		getForumWatchDao().create(fw);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#removeTopicWatch(org.openuss.discussion.TopicInfo)
	 */
	protected void handleRemoveTopicWatch(TopicInfo topic) throws Exception {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		Topic t = getTopicDao().load(topic.getId());
		DiscussionWatch dw = getDiscussionWatchDao().findByTopicAndUser(t, getSecurityService().getUserObject(getSecurityService().getCurrentUser()));
		getDiscussionWatchDao().remove(dw);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#removeForumWatch(java.lang.Long)
	 */
	protected void handleRemoveForumWatch(ForumInfo forum) throws Exception {
		Validate.notNull(forum);
		Validate.notNull(forum.getId());
		Forum f = getForumDao().load(forum.getId());
		ForumWatch fw = getForumWatchDao().findByUserAndForum(getSecurityService().getUserObject(getSecurityService().getCurrentUser()), f);
		getForumWatchDao().remove(fw);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#addAttachment(org.openuss.discussion.PostInfo,
	 *      org.openuss.documents.FileInfo)
	 */
	protected void handleAddAttachment(PostInfo post, FileInfo file) throws Exception {
		Validate.notNull(post);
		Validate.notNull(post.getId());
		Validate.notNull(file);
		FolderInfo parent = getDocumentService().getFolder(post);
		getDocumentService().createFileEntry(file, parent);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#removeAttachment(org.openuss.discussion.PostInfo)
	 */
	protected void handleRemoveAttachment(PostInfo post, FileInfo fileInfo) throws Exception {
		Validate.notNull(post);
		Validate.notNull(post.getId());
		Validate.notNull(fileInfo);
		Validate.notNull(fileInfo.getId());
		getDocumentService().removeFolderEntry(fileInfo.getId());
	}

	@Override
	protected void handleAddForum(ForumInfo forum) throws Exception {
		Validate.notNull(forum, "forum must not be null");
		Validate.notNull(forum.getDomainIdentifier(), "domain Identifier of forum must not be null");
		Forum forumObject = getForumDao().forumInfoToEntity(forum);
		getForumDao().create(forumObject);
	}

	@Override
	protected void handleChangeEditState(ForumInfo forum) throws Exception {
		Validate.notNull(forum);
		Validate.notNull(forum.getId());
		Forum f = getForumDao().load(forum.getId());
		f.setReadOnly(!f.isReadOnly());
		getForumDao().update(f);
	}

	@Override
	protected void handleChangeEditState(TopicInfo topic) throws Exception {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		Topic t = getTopicDao().load(topic.getId());
		t.setReadOnly(!t.isReadOnly());
		getTopicDao().update(t);
	}

	@Override
	protected ForumInfo handleGetForum(DomainObject domainObject) throws Exception {
		Validate.notNull(domainObject, "DomainObject must not be null.");
		Validate.notNull(domainObject.getId(),"DomainObject must provide an id.");
		Forum forum = getForumDao().findByDomainIdentifier(domainObject.getId());
		if (forum == null) {
			ForumInfo f = new ForumInfo();
			f.setDomainIdentifier(domainObject.getId());
			f.setReadOnly(false);
			handleAddForum(f);
			forum = getForumDao().findByDomainIdentifier(domainObject.getId());
		}
		return getForumDao().toForumInfo(forum);
	}

	@Override
	protected void handleAddHit(TopicInfo topic) throws Exception {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		Topic t = getTopicDao().load(topic.getId());
		t.setHits(t.getHits() + 1);
		topic.setHits(t.getHits());
		getTopicDao().update(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleGetAttachments(PostInfo post) throws Exception {
		Validate.notNull(post);
		Validate.notNull(post.getId());
		List<FileInfo> attachments = getDocumentService().getFileEntries(post);
		return attachments;
	}

	@Override
	protected boolean handleWatchesForum(ForumInfo forum) throws Exception {
		Validate.notNull(forum);
		Validate.notNull(forum.getId());
		return (getForumWatchDao().findByUserAndForum(getSecurityService().getUserObject(getSecurityService().getCurrentUser()), getForumDao().load(forum.getId())) != null);
	}

	@Override
	protected boolean handleWatchesTopic(TopicInfo topic) throws Exception {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		return (getDiscussionWatchDao().findByTopicAndUser(getTopicDao().load(topic.getId()), getSecurityService().getUserObject(getSecurityService().getCurrentUser())) != null);
	}
	
	@SuppressWarnings("unchecked")
	private void sendNotificationEmail(List<User> recipients, Topic topic) {
		if (recipients==null||recipients.size()==0) return;
		try {
			String link = "/views/secured/discussion/discussionthread.faces?topic="+ topic.getId()+"&course="+topic.getForum().getDomainIdentifier();

			link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+link;
				

			Map parameters = new HashMap();
			parameters.put("topicname", topic.getTitle());
			parameters.put("topiclink", link);
			
			getMessageService().sendMessage(
					"user.discussion.watch.sender", 
					"user.discussion.watch.subject", 
					"discussionnotification",
					parameters, recipients);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}



}