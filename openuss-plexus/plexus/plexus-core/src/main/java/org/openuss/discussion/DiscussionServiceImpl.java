// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.discussion.DiscussionService
 */
public class DiscussionServiceImpl
    extends org.openuss.discussion.DiscussionServiceBase
{

    /**
     * @see org.openuss.discussion.DiscussionService#createTopic(org.openuss.discussion.PostInfo, java.lang.Long)
     */
    protected void handleCreateTopic(PostInfo post, Long domainObject)
        throws java.lang.Exception
    {
    	//create topic
    	TopicInfo ti = extractTopicInfo(post, domainObject);    	
    	Topic topic = getTopicDao().topicInfoToEntity(ti);
    	ti = getTopicDao().toTopicInfo(topic);
    	//add first post
    	handleAddPost(post, ti);
    	Post p = getPostDao().postInfoToEntity(post);
    	topic.setFirst(p);
    	topic.setSubmitter(getSecurityService().getUserByName(post.getSubmitter()));
    }

	private TopicInfo extractTopicInfo(org.openuss.discussion.PostInfo post, java.lang.Long domainObject) {
		TopicInfo ti = new TopicInfo();
    	ti.setCreated(post.getCreated());
    	ti.setForumId(getForum(domainObject).getId());
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
    		getDocumentService().removeFolderEntry(getDocumentService().getFolder(p).getId());
    	}
    	//remove viewstates to topic
    	getTrackingService().remove(t);
    	//remove topic itself
    	getTopicDao().remove(t);
    	
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
    	getPostDao().update(newPost);
    	
    	
    	List<Post> posts = t.getPosts();
    	posts.add(newPost);
    	t.setPosts(posts);
    	t.setLast(newPost);
    	getTopicDao().update(t);
    	//TODO formula?
    	
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
    }
    

    /**
     * @see org.openuss.discussion.DiscussionService#updatePost(org.openuss.discussion.PostInfo)
     */
    protected void handleUpdatePost(org.openuss.discussion.PostInfo post)
        throws java.lang.Exception
    {
        // @todo implement protected void handleUpdatePost(org.openuss.discussion.PostInfo post)
        throw new java.lang.UnsupportedOperationException("org.openuss.discussion.DiscussionService.handleUpdatePost(org.openuss.discussion.PostInfo post) Not implemented!");
    }

    /**
     * @see org.openuss.discussion.DiscussionService#getPost(org.openuss.discussion.PostInfo)
     */
    protected org.openuss.discussion.PostInfo handleGetPost(org.openuss.discussion.PostInfo post)
        throws java.lang.Exception
    {
        // @todo implement protected org.openuss.discussion.PostInfo handleGetPost(org.openuss.discussion.PostInfo post)
        return null;
    }

    /**
     * @see org.openuss.discussion.DiscussionService#getPosts(org.openuss.discussion.TopicInfo)
     */
    protected java.util.List handleGetPosts(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleGetPosts(org.openuss.discussion.TopicInfo topic)
        return null;
    }

    /**
     * @see org.openuss.discussion.DiscussionService#getTopic(org.openuss.discussion.TopicInfo)
     */
    protected org.openuss.discussion.TopicInfo handleGetTopic(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception
    {
        // @todo implement protected org.openuss.discussion.TopicInfo handleGetTopic(org.openuss.discussion.TopicInfo topic)
        return null;
    }

    /**
     * @see org.openuss.discussion.DiscussionService#getTopics(java.lang.Long)
     */
    protected java.util.List handleGetTopics(java.lang.Long domainIdentifier)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleGetTopics(java.lang.Long domainIdentifier)
        return null;
    }

    /**
     * @see org.openuss.discussion.DiscussionService#addTopicWatch(org.openuss.discussion.TopicInfo)
     */
    protected void handleAddTopicWatch(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddTopicWatch(org.openuss.discussion.TopicInfo topic)
        throw new java.lang.UnsupportedOperationException("org.openuss.discussion.DiscussionService.handleAddTopicWatch(org.openuss.discussion.TopicInfo topic) Not implemented!");
    }

    /**
     * @see org.openuss.discussion.DiscussionService#addForumWatch(java.lang.Long)
     */
    protected void handleAddForumWatch(java.lang.Long domainIdentifier)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddForumWatch(java.lang.Long domainIdentifier)
        throw new java.lang.UnsupportedOperationException("org.openuss.discussion.DiscussionService.handleAddForumWatch(java.lang.Long domainIdentifier) Not implemented!");
    }

    /**
     * @see org.openuss.discussion.DiscussionService#removeTopicWatch(org.openuss.discussion.TopicInfo)
     */
    protected void handleRemoveTopicWatch(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveTopicWatch(org.openuss.discussion.TopicInfo topic)
        throw new java.lang.UnsupportedOperationException("org.openuss.discussion.DiscussionService.handleRemoveTopicWatch(org.openuss.discussion.TopicInfo topic) Not implemented!");
    }

    /**
     * @see org.openuss.discussion.DiscussionService#removeForumWatch(java.lang.Long)
     */
    protected void handleRemoveForumWatch(java.lang.Long domainIdentifier)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveForumWatch(java.lang.Long domainIdentifier)
        throw new java.lang.UnsupportedOperationException("org.openuss.discussion.DiscussionService.handleRemoveForumWatch(java.lang.Long domainIdentifier) Not implemented!");
    }

    /**
     * @see org.openuss.discussion.DiscussionService#addAttachment(org.openuss.discussion.PostInfo, org.openuss.documents.FileInfo)
     */
    protected void handleAddAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo file)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo file)
        throw new java.lang.UnsupportedOperationException("org.openuss.discussion.DiscussionService.handleAddAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo file) Not implemented!");
    }

    /**
     * @see org.openuss.discussion.DiscussionService#removeAttachment(org.openuss.discussion.PostInfo)
     */
    protected void handleRemoveAttachment(org.openuss.discussion.PostInfo post)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveAttachment(org.openuss.discussion.PostInfo post)
        throw new java.lang.UnsupportedOperationException("org.openuss.discussion.DiscussionService.handleRemoveAttachment(org.openuss.discussion.PostInfo post) Not implemented!");
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleChangeEditState(TopicInfo topic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ForumInfo handleGetForum(Long domainIdentifier) throws Exception {
		Forum forum = getForumDao().findByDomainIdentifier(domainIdentifier);
		return getForumDao().toForumInfo(forum);
	}

}