// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.foundation.DomainObject;
import org.openuss.security.User;

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

		topic.setSubmitter(getSecurityService().getUserByName(postInfo.getSubmitter()));
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
		
		post.setSubmitter(getSecurityService().getUserByName(postInfo.getSubmitter()));

		addPostToTopicAndPersist(topic, post);
		
		getDocumentService().diffSave(post, postInfo.getAttachments());
		
		getTrackingService().setModified(topic);
		getTrackingService().setRead(topic);
		getPostDao().toPostInfo(post, postInfo);
	}

	private void addPostToTopicAndPersist(Topic topic, Post post) {
		topic.addPost(post);
		getPostDao().create(post);
		getTopicDao().update(topic);

		getSecurityService().createObjectIdentity(post, topic);
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
				getSecurityService().removeObjectIdentity(post);
				
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
		post.setEditor(getSecurityService().getUserByName(postInfo.getEditor()));
		post.setLastModification(postInfo.getLastModification());
		getPostDao().update(post);
		
		getDocumentService().diffSave(post, postInfo.getAttachments());

		getPostDao().toPostInfo(post,postInfo);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#getPost(org.openuss.discussion.PostInfo)
	 */
	@SuppressWarnings("unchecked")
	protected PostInfo handleGetPost(PostInfo post) throws Exception {
		Validate.notNull(post);
		Validate.notNull(post.getId());
		PostInfo postInfo = getPostDao().toPostInfo(getPostDao().load(post.getId()));
		List<FileInfo> attachments = getAttachments(postInfo);
		postInfo.setAttachments(attachments);
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

		for (PostInfo post : posts) {
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
		return getTopicDao().toTopicInfo(getTopicDao().load(topic.getId()));
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#getTopics(java.lang.Long)
	 */
	protected List handleGetTopics(ForumInfo forumInfo) throws Exception {
		Validate.notNull(forumInfo, "Parameter forum must not be null");
		Validate.notNull(forumInfo.getId(), "Parameter form must provide an valid id.");
		
		Forum forum = getForumDao().load(forumInfo.getId());
		if (forum == null) {
			return new ArrayList<TopicInfo>();
		} else {
			return getTopicDao().loadTopicsWithViewState(forum, currentUser());
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
		dw.setUser(currentUser());
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
		fw.setUser(currentUser());
		getForumWatchDao().create(fw);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#removeTopicWatch(org.openuss.discussion.TopicInfo)
	 */
	protected void handleRemoveTopicWatch(TopicInfo topic) throws Exception {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		Topic t = getTopicDao().load(topic.getId());
		DiscussionWatch dw = getDiscussionWatchDao().findByTopicAndUser(t, currentUser());
		getDiscussionWatchDao().remove(dw);
	}

	/**
	 * @see org.openuss.discussion.DiscussionService#removeForumWatch(java.lang.Long)
	 */
	protected void handleRemoveForumWatch(ForumInfo forum) throws Exception {
		Validate.notNull(forum);
		Validate.notNull(forum.getId());
		Forum f = getForumDao().load(forum.getId());
		ForumWatch fw = getForumWatchDao().findByUserAndForum(currentUser(), f);
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
		return (getForumWatchDao().findByUserAndForum(currentUser(), getForumDao().load(forum.getId())) != null);
	}

	@Override
	protected boolean handleWatchesTopic(TopicInfo topic) throws Exception {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		return (getDiscussionWatchDao().findByTopicAndUser(getTopicDao().load(topic.getId()), currentUser()) != null);
	}

	private User currentUser() {
		// FIXME me be optimized by user id 
		// UserInfo userInfo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
		// USer user = getSecurityService().getCurrentUser();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return getSecurityService().getUserByName(username);
	}

}