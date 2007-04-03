// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.security.User;

/**
 * @see org.openuss.discussion.DiscussionService
 */
public class DiscussionServiceImpl
    extends org.openuss.discussion.DiscussionServiceBase
{

	public static final Logger logger = Logger.getLogger(DiscussionServiceImpl.class);
    /**
     * @see org.openuss.discussion.DiscussionService#createTopic(org.openuss.discussion.PostInfo, java.lang.Long)
     */
    protected void handleCreateTopic(PostInfo post, ForumInfo forum)
        throws java.lang.Exception
    {
    	//create topic
    	TopicInfo ti = extractTopicInfo(post, forum);    	
    	Topic topic = getTopicDao().topicInfoToEntity(ti);
    	User submitter = getSecurityService().getUserByName(post.getSubmitter());
    	topic.setSubmitter(submitter);    	
    	topic.setForum(getForumDao().load(forum.getId()));
    	getTopicDao().create(topic);
    	ti = getTopicDao().toTopicInfo(topic);
    	
    	//add to forum
    	Forum f = getForumDao().load(forum.getId());
    	Set<Topic> topics = f.getTopics();
    	topics.add(getTopicDao().topicInfoToEntity(ti));
    	f.setTopics(topics);
    	getForumDao().update(f);
    	
    	//add object identity to security
    	getSecurityService().createObjectIdentity(topic, topic.getForum().getDomainIdentifier());
    	//add first post
    	handleAddPost(post, ti);
    	topic = getTopicDao().load(ti.getId());
    	Post p = topic.getLast();
    	topic.setFirst(p);
    	getPostDao().update(p);
    	getTopicDao().update(topic);
    }

	private TopicInfo extractTopicInfo(org.openuss.discussion.PostInfo post, ForumInfo forum) {
		TopicInfo ti = new TopicInfo();
    	ti.setCreated(post.getCreated());
    	ti.setForumId(getForum(forum.getDomainIdentifier()).getId());
    	ti.setHits(0);
    	ti.setLastPost(post.getLastModification());
    	ti.setReadOnly(true);
    	ti.setSubmitter(post.getSubmitter());
    	ti.setTitle(post.getTitle());
		return ti;
	}

    /**
     * @see org.openuss.discussion.DiscussionService#deleteTopic(org.openuss.discussion.TopicInfo)
     */
    @SuppressWarnings("unchecked")
	protected void handleDeleteTopic(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception
    {
    	Topic t = getTopicDao().topicInfoToEntity(topic);
    	//remove all discussion watches to topic
    	List<DiscussionWatch> topicWatches = getDiscussionWatchDao().findByTopic(t);
    	getDiscussionWatchDao().remove(topicWatches);
    	List<Post> posts = t.getPosts();
    	// remove all attachments to posts of topic, 
    	// remove all viewstates of posts of topic 
    	Iterator i = posts.iterator();
    	Post p;
    	
    	while (i.hasNext()){
    		p = (Post) i.next();
    		getTrackingService().remove(p);
    		FolderInfo fei = getDocumentService().getFolder(p);
    		getDocumentService().removeFolderEntry(fei.getId());
    	}
 		getTopicDao().update(t);
    	//remove viewstates to topic
    	getTrackingService().remove(t);
    	//remove topic itself
    	getTopicDao().remove(t);
    	//remove object identity from security context
    	getSecurityService().removeObjectIdentity(topic);
    	
    }

    /**
     * @see org.openuss.discussion.DiscussionService#addPost(org.openuss.discussion.PostInfo, org.openuss.discussion.TopicInfo)
     */
    protected void handleAddPost(org.openuss.discussion.PostInfo post, org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception
    {
    	Topic t = getTopicDao().topicInfoToEntity(topic);
    	Post newPost = getPostDao().postInfoToEntity(post);
    	newPost.setTopic(t);
    	newPost.setSubmitter(getSecurityService().getUserByName(post.getSubmitter()));
    	getPostDao().create(newPost);
    	
    	List<Post> posts = t.getPosts();
    	posts.add(newPost);
    	t.setPosts(posts);
    	t.setLast(newPost);
    	getTopicDao().update(t);
    	//TODO formula?
    	post.setId(newPost.getId());
    	//set object identity and attachments
    	getSecurityService().createObjectIdentity(newPost, t);
		if (post.getAttachments() != null && !post.getAttachments().isEmpty()) {
			logger.debug("found "+post.getAttachments().size()+" attachments.");
			FolderInfo folder = getDocumentService().getFolder(post);

			for (FileInfo attachment : post.getAttachments()) {
				getDocumentService().createFileEntry(attachment, folder);
			}
		}		
		getPostDao().toPostInfo(newPost);
    }

    /**
     * @see org.openuss.discussion.DiscussionService#deletePost(org.openuss.discussion.PostInfo)
     */
    protected void handleDeletePost(org.openuss.discussion.PostInfo post)
        throws java.lang.Exception
    {
    	Post p = getPostDao().postInfoToEntity(post);
    	Topic t = p.getTopic();
    	List<Post> posts = t.getPosts();
    	//if first and only -> delete thread
    	if (t.getAnswerCount()==0){
    		handleDeleteTopic(getTopicDao().toTopicInfo(t));
    		return;
    	}    		
    	//if first post -> repair first
    	else if (t.getFirst().getId()==p.getId()){
    		Post newFirst = posts.get(1);
    		t.setFirst(newFirst);
    	}
    	//if last post -> delete post, repair last
    	else if(p.getTopic().getLast().getId()==p.getId()){
    		Post newLast = posts.get(posts.size()-2);
    		t.setLast(newLast);
    	}
    	// post between first and last -> just delete post    	
    	posts.remove(p);
    	t.setPosts(posts);
    	getTopicDao().update(t);
    	getPostDao().remove(p);
    	getSecurityService().removeObjectIdentity(p);
    }
    

    /**
     * @see org.openuss.discussion.DiscussionService#updatePost(org.openuss.discussion.PostInfo)
     */
    @SuppressWarnings("unchecked")
	protected void handleUpdatePost(org.openuss.discussion.PostInfo post)
        throws java.lang.Exception
    {
    	Post newPost = getPostDao().load(post.getId());
    	newPost.setText(post.getText());
    	newPost.setTitle(post.getTitle());
    	newPost.setEditor(getSecurityService().getUserByName(post.getEditor()));
    	newPost.setLastModification(post.getLastModification());
    	getPostDao().update(newPost);
    	//handle changes in attachments
    	if (post.getAttachments() == null) {
			post.setAttachments(new ArrayList<FileInfo>());
		}
		List<FileInfo> savedAttachments = getDocumentService().getFileEntries(post);
		Collection<FileInfo> removedAttachments = CollectionUtils.subtract(savedAttachments, post.getAttachments());
		getDocumentService().removeFileEntries(removedAttachments);
		FolderInfo folder = getDocumentService().getFolder(post);
		for (FileInfo attachment: post.getAttachments()) {
			if (attachment.getId() == null) {
				getDocumentService().createFileEntry(attachment, folder);
			}
		}
		post = getPostDao().toPostInfo(newPost);
		//TODO formula
    }

    /**
     * @see org.openuss.discussion.DiscussionService#getPost(org.openuss.discussion.PostInfo)
     */
    @SuppressWarnings("unchecked")
	protected org.openuss.discussion.PostInfo handleGetPost(org.openuss.discussion.PostInfo post)
        throws java.lang.Exception
    {
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
	protected java.util.List handleGetPosts(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception
    {
    	Validate.notNull(topic);
    	Validate.notNull(topic.getId());
    	Topic t = getTopicDao().load(topic.getId());
    	List<PostInfo> posts = new ArrayList<PostInfo>();
    	if (t==null) return posts;
    	List<Post> pList = t.getPosts();
    	Iterator i = pList.iterator();    	
    	while (i.hasNext()){
    		PostInfo newPost = getPostDao().toPostInfo((Post)i.next()); 
    		List<FileInfo> attachments = getAttachments(newPost);
    		newPost.setAttachments(attachments);
    		posts.add(newPost);
    	}
    	return posts;
    }

    /**
     * @see org.openuss.discussion.DiscussionService#getTopic(org.openuss.discussion.TopicInfo)
     */
    protected org.openuss.discussion.TopicInfo handleGetTopic(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception
    {
    	Validate.notNull(topic);
    	Validate.notNull(topic.getId());
    	return getTopicDao().toTopicInfo(getTopicDao().load(topic.getId()));
    }

    /**
     * @see org.openuss.discussion.DiscussionService#getTopics(java.lang.Long)
     */
    protected java.util.List handleGetTopics(ForumInfo forum)
        throws java.lang.Exception
    {
    	//String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();    	
    	//List<Object[]> viewStates = getTrackingService().getTopicViewStates(forum.getId(), getSecurityService().getUserByName(currentUser).getId());
    	Validate.notNull(forum);
    	Validate.notNull(forum.getId());
    	Forum f = getForumDao().load(forum.getId());
    	Set<Topic> tList = f.getTopics();
    	List<TopicInfo> topics = new ArrayList<TopicInfo>();
    	//TODO add viewstate to topicInfos
    	Iterator i = tList.iterator();
    	while (i.hasNext()){
    		topics.add(getTopicDao().toTopicInfo((Topic)i.next()));
    	}
    	return topics;
    }

    /**
     * @see org.openuss.discussion.DiscussionService#addTopicWatch(org.openuss.discussion.TopicInfo)
     */
    protected void handleAddTopicWatch(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception
    {
    	Validate.notNull(topic);
    	Validate.notNull(topic.getId());
    	DiscussionWatch dw = DiscussionWatch.Factory.newInstance();
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	dw.setTopic(getTopicDao().load(topic.getId()));
    	dw.setUser(user);
    	getDiscussionWatchDao().create(dw);
    }

    /**
     * @see org.openuss.discussion.DiscussionService#addForumWatch(java.lang.Long)
     */
    protected void handleAddForumWatch(ForumInfo forum)
        throws java.lang.Exception
    {
    	Validate.notNull(forum);
    	Validate.notNull(forum.getId());
    	ForumWatch fw = ForumWatch.Factory.newInstance();
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	fw.setForum(getForumDao().load(forum.getId()));
    	fw.setUser(user);
    	getForumWatchDao().create(fw);
    }

    /**
     * @see org.openuss.discussion.DiscussionService#removeTopicWatch(org.openuss.discussion.TopicInfo)
     */
    protected void handleRemoveTopicWatch(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception
    {
    	Validate.notNull(topic);
    	Validate.notNull(topic.getId());
    	Topic t = getTopicDao().load(topic.getId());
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	DiscussionWatch dw = getDiscussionWatchDao().findByTopicAndUser(t, user);
    	getDiscussionWatchDao().remove(dw);
    }

    /**
     * @see org.openuss.discussion.DiscussionService#removeForumWatch(java.lang.Long)
     */
    protected void handleRemoveForumWatch(ForumInfo forum)
        throws java.lang.Exception
    {
    	Validate.notNull(forum);
    	Validate.notNull(forum.getId());
    	Forum f = getForumDao().load(forum.getId());
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	ForumWatch fw = getForumWatchDao().findByUserAndForum(user, f);
    	getForumWatchDao().remove(fw);
    }

    /**
     * @see org.openuss.discussion.DiscussionService#addAttachment(org.openuss.discussion.PostInfo, org.openuss.documents.FileInfo)
     */
    protected void handleAddAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo file)
        throws java.lang.Exception
    {
    	Validate.notNull(post);
    	Validate.notNull(post.getId());
    	Validate.notNull(file);    	
		FolderInfo parent = getDocumentService().getFolder(post);
		getDocumentService().createFileEntry(file, parent);
    }

    /**
     * @see org.openuss.discussion.DiscussionService#removeAttachment(org.openuss.discussion.PostInfo)
     */
    protected void handleRemoveAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo fileInfo)
        throws java.lang.Exception
    {
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
	protected ForumInfo handleGetForum(Long domainIdentifier) throws Exception {
		Forum forum = getForumDao().findByDomainIdentifier(domainIdentifier);
		if (forum==null){
			ForumInfo f = new ForumInfo();
			f.setDomainIdentifier(domainIdentifier);
			f.setReadOnly(false);
			handleAddForum(f);
			forum = getForumDao().findByDomainIdentifier(domainIdentifier);
		}
		return getForumDao().toForumInfo(forum);
	}

	@Override
	protected void handleAddHit(TopicInfo topic) throws Exception {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		Topic t = getTopicDao().load(topic.getId());
		t.setHits(t.getHits()+1);
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
		User user = getSecurityService().getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
		return (getForumWatchDao().findByUserAndForum(user, getForumDao().load(forum.getId()))!=null);
	}

	@Override
	protected boolean handleWatchesTopic(TopicInfo topic) throws Exception {
		Validate.notNull(topic);
		Validate.notNull(topic.getId());
		User user = getSecurityService().getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
		return (getDiscussionWatchDao().findByTopicAndUser(getTopicDao().load(topic.getId()), user)!=null);
	}

}