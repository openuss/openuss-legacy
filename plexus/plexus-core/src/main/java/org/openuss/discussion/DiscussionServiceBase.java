package org.openuss.discussion;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.discussion.DiscussionService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.discussion.DiscussionService
 */
public abstract class DiscussionServiceBase
    implements org.openuss.discussion.DiscussionService
{

    private org.openuss.viewtracking.TrackingService trackingService;

    /**
     * Sets the reference to <code>trackingService</code>.
     */
    public void setTrackingService(org.openuss.viewtracking.TrackingService trackingService)
    {
        this.trackingService = trackingService;
    }

    /**
     * Gets the reference to <code>trackingService</code>.
     */
    protected org.openuss.viewtracking.TrackingService getTrackingService()
    {
        return this.trackingService;
    }

    private org.openuss.security.SecurityService securityService;

    /**
     * Sets the reference to <code>securityService</code>.
     */
    public void setSecurityService(org.openuss.security.SecurityService securityService)
    {
        this.securityService = securityService;
    }

    /**
     * Gets the reference to <code>securityService</code>.
     */
    protected org.openuss.security.SecurityService getSecurityService()
    {
        return this.securityService;
    }

    private org.openuss.documents.DocumentService documentService;

    /**
     * Sets the reference to <code>documentService</code>.
     */
    public void setDocumentService(org.openuss.documents.DocumentService documentService)
    {
        this.documentService = documentService;
    }

    /**
     * Gets the reference to <code>documentService</code>.
     */
    protected org.openuss.documents.DocumentService getDocumentService()
    {
        return this.documentService;
    }

    private org.openuss.system.SystemService systemService;

    /**
     * Sets the reference to <code>systemService</code>.
     */
    public void setSystemService(org.openuss.system.SystemService systemService)
    {
        this.systemService = systemService;
    }

    /**
     * Gets the reference to <code>systemService</code>.
     */
    protected org.openuss.system.SystemService getSystemService()
    {
        return this.systemService;
    }

    private org.openuss.messaging.MessageService messageService;

    /**
     * Sets the reference to <code>messageService</code>.
     */
    public void setMessageService(org.openuss.messaging.MessageService messageService)
    {
        this.messageService = messageService;
    }

    /**
     * Gets the reference to <code>messageService</code>.
     */
    protected org.openuss.messaging.MessageService getMessageService()
    {
        return this.messageService;
    }

    private org.openuss.discussion.ForumWatchDao forumWatchDao;

    /**
     * Sets the reference to <code>forumWatch</code>'s DAO.
     */
    public void setForumWatchDao(org.openuss.discussion.ForumWatchDao forumWatchDao)
    {
        this.forumWatchDao = forumWatchDao;
    }

    /**
     * Gets the reference to <code>forumWatch</code>'s DAO.
     */
    protected org.openuss.discussion.ForumWatchDao getForumWatchDao()
    {
        return this.forumWatchDao;
    }

    private org.openuss.discussion.DiscussionWatchDao discussionWatchDao;

    /**
     * Sets the reference to <code>discussionWatch</code>'s DAO.
     */
    public void setDiscussionWatchDao(org.openuss.discussion.DiscussionWatchDao discussionWatchDao)
    {
        this.discussionWatchDao = discussionWatchDao;
    }

    /**
     * Gets the reference to <code>discussionWatch</code>'s DAO.
     */
    protected org.openuss.discussion.DiscussionWatchDao getDiscussionWatchDao()
    {
        return this.discussionWatchDao;
    }

    private org.openuss.discussion.TopicDao topicDao;

    /**
     * Sets the reference to <code>topic</code>'s DAO.
     */
    public void setTopicDao(org.openuss.discussion.TopicDao topicDao)
    {
        this.topicDao = topicDao;
    }

    /**
     * Gets the reference to <code>topic</code>'s DAO.
     */
    protected org.openuss.discussion.TopicDao getTopicDao()
    {
        return this.topicDao;
    }

    private org.openuss.discussion.PostDao postDao;

    /**
     * Sets the reference to <code>post</code>'s DAO.
     */
    public void setPostDao(org.openuss.discussion.PostDao postDao)
    {
        this.postDao = postDao;
    }

    /**
     * Gets the reference to <code>post</code>'s DAO.
     */
    protected org.openuss.discussion.PostDao getPostDao()
    {
        return this.postDao;
    }

    private org.openuss.discussion.ForumDao forumDao;

    /**
     * Sets the reference to <code>forum</code>'s DAO.
     */
    public void setForumDao(org.openuss.discussion.ForumDao forumDao)
    {
        this.forumDao = forumDao;
    }

    /**
     * Gets the reference to <code>forum</code>'s DAO.
     */
    protected org.openuss.discussion.ForumDao getForumDao()
    {
        return this.forumDao;
    }

    /**
     * @see org.openuss.discussion.DiscussionService#createTopic(org.openuss.discussion.PostInfo, org.openuss.discussion.ForumInfo)
     */
    public void createTopic(org.openuss.discussion.PostInfo post, org.openuss.discussion.ForumInfo forum)
    {
        try
        {
            this.handleCreateTopic(post, forum);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.createTopic(org.openuss.discussion.PostInfo post, org.openuss.discussion.ForumInfo forum)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createTopic(org.openuss.discussion.PostInfo, org.openuss.discussion.ForumInfo)}
      */
    protected abstract void handleCreateTopic(org.openuss.discussion.PostInfo post, org.openuss.discussion.ForumInfo forum)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#deleteTopic(org.openuss.discussion.TopicInfo)
     */
    public void deleteTopic(org.openuss.discussion.TopicInfo topic)
    {
        try
        {
            this.handleDeleteTopic(topic);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.deleteTopic(org.openuss.discussion.TopicInfo topic)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteTopic(org.openuss.discussion.TopicInfo)}
      */
    protected abstract void handleDeleteTopic(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#addPost(org.openuss.discussion.PostInfo, org.openuss.discussion.TopicInfo)
     */
    public void addPost(org.openuss.discussion.PostInfo post, org.openuss.discussion.TopicInfo topic)
    {
        try
        {
            this.handleAddPost(post, topic);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.addPost(org.openuss.discussion.PostInfo post, org.openuss.discussion.TopicInfo topic)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addPost(org.openuss.discussion.PostInfo, org.openuss.discussion.TopicInfo)}
      */
    protected abstract void handleAddPost(org.openuss.discussion.PostInfo post, org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#deletePost(org.openuss.discussion.PostInfo)
     */
    public void deletePost(org.openuss.discussion.PostInfo post)
    {
        try
        {
            this.handleDeletePost(post);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.deletePost(org.openuss.discussion.PostInfo post)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deletePost(org.openuss.discussion.PostInfo)}
      */
    protected abstract void handleDeletePost(org.openuss.discussion.PostInfo post)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#updatePost(org.openuss.discussion.PostInfo)
     */
    public void updatePost(org.openuss.discussion.PostInfo post)
    {
        try
        {
            this.handleUpdatePost(post);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.updatePost(org.openuss.discussion.PostInfo post)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #updatePost(org.openuss.discussion.PostInfo)}
      */
    protected abstract void handleUpdatePost(org.openuss.discussion.PostInfo post)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#getPost(org.openuss.discussion.PostInfo)
     */
    public org.openuss.discussion.PostInfo getPost(org.openuss.discussion.PostInfo post)
    {
        try
        {
            return this.handleGetPost(post);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.getPost(org.openuss.discussion.PostInfo post)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getPost(org.openuss.discussion.PostInfo)}
      */
    protected abstract org.openuss.discussion.PostInfo handleGetPost(org.openuss.discussion.PostInfo post)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#getPosts(org.openuss.discussion.TopicInfo)
     */
    public java.util.List getPosts(org.openuss.discussion.TopicInfo topic)
    {
        try
        {
            return this.handleGetPosts(topic);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.getPosts(org.openuss.discussion.TopicInfo topic)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getPosts(org.openuss.discussion.TopicInfo)}
      */
    protected abstract java.util.List handleGetPosts(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#getTopic(org.openuss.discussion.TopicInfo)
     */
    public org.openuss.discussion.TopicInfo getTopic(org.openuss.discussion.TopicInfo topic)
    {
        try
        {
            return this.handleGetTopic(topic);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.getTopic(org.openuss.discussion.TopicInfo topic)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getTopic(org.openuss.discussion.TopicInfo)}
      */
    protected abstract org.openuss.discussion.TopicInfo handleGetTopic(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#getTopics(org.openuss.discussion.ForumInfo)
     */
    public java.util.List getTopics(org.openuss.discussion.ForumInfo forum)
    {
        try
        {
            return this.handleGetTopics(forum);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.getTopics(org.openuss.discussion.ForumInfo forum)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getTopics(org.openuss.discussion.ForumInfo)}
      */
    protected abstract java.util.List handleGetTopics(org.openuss.discussion.ForumInfo forum)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#addTopicWatch(org.openuss.discussion.TopicInfo)
     */
    public void addTopicWatch(org.openuss.discussion.TopicInfo topic)
    {
        try
        {
            this.handleAddTopicWatch(topic);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.addTopicWatch(org.openuss.discussion.TopicInfo topic)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addTopicWatch(org.openuss.discussion.TopicInfo)}
      */
    protected abstract void handleAddTopicWatch(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#addForumWatch(org.openuss.discussion.ForumInfo)
     */
    public void addForumWatch(org.openuss.discussion.ForumInfo forum)
    {
        try
        {
            this.handleAddForumWatch(forum);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.addForumWatch(org.openuss.discussion.ForumInfo forum)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addForumWatch(org.openuss.discussion.ForumInfo)}
      */
    protected abstract void handleAddForumWatch(org.openuss.discussion.ForumInfo forum)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#removeTopicWatch(org.openuss.discussion.TopicInfo)
     */
    public void removeTopicWatch(org.openuss.discussion.TopicInfo topic)
    {
        try
        {
            this.handleRemoveTopicWatch(topic);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.removeTopicWatch(org.openuss.discussion.TopicInfo topic)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeTopicWatch(org.openuss.discussion.TopicInfo)}
      */
    protected abstract void handleRemoveTopicWatch(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#removeForumWatch(org.openuss.discussion.ForumInfo)
     */
    public void removeForumWatch(org.openuss.discussion.ForumInfo forum)
    {
        try
        {
            this.handleRemoveForumWatch(forum);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.removeForumWatch(org.openuss.discussion.ForumInfo forum)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeForumWatch(org.openuss.discussion.ForumInfo)}
      */
    protected abstract void handleRemoveForumWatch(org.openuss.discussion.ForumInfo forum)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#addAttachment(org.openuss.discussion.PostInfo, org.openuss.documents.FileInfo)
     */
    public void addAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo file)
    {
        try
        {
            this.handleAddAttachment(post, file);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.addAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo file)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addAttachment(org.openuss.discussion.PostInfo, org.openuss.documents.FileInfo)}
      */
    protected abstract void handleAddAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo file)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#removeAttachment(org.openuss.discussion.PostInfo, org.openuss.documents.FileInfo)
     */
    public void removeAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo file)
    {
        try
        {
            this.handleRemoveAttachment(post, file);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.removeAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo file)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeAttachment(org.openuss.discussion.PostInfo, org.openuss.documents.FileInfo)}
      */
    protected abstract void handleRemoveAttachment(org.openuss.discussion.PostInfo post, org.openuss.documents.FileInfo file)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#addForum(org.openuss.discussion.ForumInfo)
     */
    public void addForum(org.openuss.discussion.ForumInfo forum)
    {
        try
        {
            this.handleAddForum(forum);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.addForum(org.openuss.discussion.ForumInfo forum)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addForum(org.openuss.discussion.ForumInfo)}
      */
    protected abstract void handleAddForum(org.openuss.discussion.ForumInfo forum)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#getForum(org.openuss.foundation.DomainObject)
     */
    public org.openuss.discussion.ForumInfo getForum(org.openuss.foundation.DomainObject domainObject)
    {
        try
        {
            return this.handleGetForum(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.getForum(org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getForum(org.openuss.foundation.DomainObject)}
      */
    protected abstract org.openuss.discussion.ForumInfo handleGetForum(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#changeEditState(org.openuss.discussion.ForumInfo)
     */
    public void changeEditState(org.openuss.discussion.ForumInfo forum)
    {
        try
        {
            this.handleChangeEditState(forum);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.changeEditState(org.openuss.discussion.ForumInfo forum)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #changeEditState(org.openuss.discussion.ForumInfo)}
      */
    protected abstract void handleChangeEditState(org.openuss.discussion.ForumInfo forum)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#changeEditState(org.openuss.discussion.TopicInfo)
     */
    public void changeEditState(org.openuss.discussion.TopicInfo topic)
    {
        try
        {
            this.handleChangeEditState(topic);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.changeEditState(org.openuss.discussion.TopicInfo topic)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #changeEditState(org.openuss.discussion.TopicInfo)}
      */
    protected abstract void handleChangeEditState(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#addHit(org.openuss.discussion.TopicInfo)
     */
    public void addHit(org.openuss.discussion.TopicInfo topic)
    {
        try
        {
            this.handleAddHit(topic);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.addHit(org.openuss.discussion.TopicInfo topic)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addHit(org.openuss.discussion.TopicInfo)}
      */
    protected abstract void handleAddHit(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#getAttachments(org.openuss.discussion.PostInfo)
     */
    public java.util.List getAttachments(org.openuss.discussion.PostInfo post)
    {
        try
        {
            return this.handleGetAttachments(post);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.getAttachments(org.openuss.discussion.PostInfo post)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAttachments(org.openuss.discussion.PostInfo)}
      */
    protected abstract java.util.List handleGetAttachments(org.openuss.discussion.PostInfo post)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#watchesForum(org.openuss.discussion.ForumInfo)
     */
    public boolean watchesForum(org.openuss.discussion.ForumInfo forum)
    {
        try
        {
            return this.handleWatchesForum(forum);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.watchesForum(org.openuss.discussion.ForumInfo forum)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #watchesForum(org.openuss.discussion.ForumInfo)}
      */
    protected abstract boolean handleWatchesForum(org.openuss.discussion.ForumInfo forum)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#watchesTopic(org.openuss.discussion.TopicInfo)
     */
    public boolean watchesTopic(org.openuss.discussion.TopicInfo topic)
    {
        try
        {
            return this.handleWatchesTopic(topic);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.watchesTopic(org.openuss.discussion.TopicInfo topic)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #watchesTopic(org.openuss.discussion.TopicInfo)}
      */
    protected abstract boolean handleWatchesTopic(org.openuss.discussion.TopicInfo topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.DiscussionService#removeUserFromDiscussions(org.openuss.security.User)
     */
    public void removeUserFromDiscussions(org.openuss.security.User user)
    {
        try
        {
            this.handleRemoveUserFromDiscussions(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.discussion.DiscussionServiceException(
                "Error performing 'org.openuss.discussion.DiscussionService.removeUserFromDiscussions(org.openuss.security.User user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeUserFromDiscussions(org.openuss.security.User)}
      */
    protected abstract void handleRemoveUserFromDiscussions(org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * Gets the current <code>principal</code> if one has been set,
     * otherwise returns <code>null</code>.
     *
     * @return the current principal
     */
    protected java.security.Principal getPrincipal()
    {
        return org.andromda.spring.PrincipalStore.get();
    }
}