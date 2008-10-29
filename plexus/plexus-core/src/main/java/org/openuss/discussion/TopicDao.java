package org.openuss.discussion;

/**
 * @see org.openuss.discussion.Topic
 */
public interface TopicDao
{
    /**
     * This constant is used as a transformation flag; entities can be converted automatically into value objects
     * or other types, different methods in a class implementing this interface support this feature: look for
     * an <code>int</code> parameter called <code>transform</code>.
     * <p/>
     * This specific flag denotes no transformation will occur.
     */
    public final static int TRANSFORM_NONE = 0;

    /**
     * This constant is used as a transformation flag; entities can be converted automatically into value objects
     * or other types, different methods in a class implementing this interface support this feature: look for
     * an <code>int</code> parameter called <code>transform</code>.
     * <p/>
     * This specific flag denotes entities must be transformed into objects of type
     * {@link org.openuss.discussion.TopicInfo}.
     */
    public final static int TRANSFORM_TOPICINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toTopicInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toTopicInfo(
        org.openuss.discussion.Topic sourceEntity,
        org.openuss.discussion.TopicInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.discussion.TopicInfo}.
     */
    public org.openuss.discussion.TopicInfo toTopicInfo(org.openuss.discussion.Topic entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.discussion.TopicInfo}.
     */
    public void toTopicInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.discussion.TopicInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void topicInfoToEntity(
        org.openuss.discussion.TopicInfo sourceVO,
        org.openuss.discussion.Topic targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.discussion.TopicInfo} to this DAO's entity.
     */
    public org.openuss.discussion.Topic topicInfoToEntity(org.openuss.discussion.TopicInfo topicInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.discussion.TopicInfo} to this
     * DAO's entity.
     */
    public void topicInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.discussion.Topic from the persistent store.
     */
    public org.openuss.discussion.Topic load(java.lang.Long id);

    /**
     * <p>
     * Does the same thing as {@link #load(java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined in this class then the result <strong>WILL BE</strong> passed through an operation which can
     * optionally transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     *
     * @param id the identifier of the entity to load.
     * @return either the entity or the object transformed from the entity.
     */
    public Object load(int transform, java.lang.Long id);

    /**
     * Loads all entities of type {@link org.openuss.discussion.Topic}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.discussion.Topic> loadAll();

    /**
     * <p>
     * Does the same thing as {@link #loadAll()} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     *
     * @param transform the flag indicating what transformation to use.
     * @return the loaded entities.
     */
    public java.util.Collection loadAll(final int transform);

    /**
     * Creates an instance of org.openuss.discussion.Topic and adds it to the persistent store.
     */
    public org.openuss.discussion.Topic create(org.openuss.discussion.Topic topic);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.discussion.Topic)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.discussion.Topic topic);

    /**
     * Creates a new instance of org.openuss.discussion.Topic and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.discussion.Topic
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.discussion.Topic> create(java.util.Collection<org.openuss.discussion.Topic> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.discussion.Topic)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.discussion.Topic> entities);

    /**
     * Updates the <code>topic</code> instance in the persistent store.
     */
    public void update(org.openuss.discussion.Topic topic);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.discussion.Topic> entities);

    /**
     * Removes the instance of org.openuss.discussion.Topic from the persistent store.
     */
    public void remove(org.openuss.discussion.Topic topic);

    /**
     * Removes the instance of org.openuss.discussion.Topic having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.discussion.Topic> entities);

    /**
 * 
     */
    public java.util.List findBySubmitter(org.openuss.security.User submitter);

    /**
     * <p>
     * Does the same thing as {@link #findBySubmitter(org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findBySubmitter(org.openuss.security.User)}.
     * </p>
     */
    public java.util.List findBySubmitter(String queryString, org.openuss.security.User submitter);

    /**
     * <p>
     * Does the same thing as {@link #findBySubmitter(org.openuss.security.User)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findBySubmitter(int transform, org.openuss.security.User submitter);

    /**
     * <p>
     * Does the same thing as {@link #findBySubmitter(boolean, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findBySubmitter(int, org.openuss.security.User submitter)}.
     * </p>
     */
    public java.util.List findBySubmitter(int transform, String queryString, org.openuss.security.User submitter);

    /**
 * 
     */
    public java.util.List findByForum(org.openuss.discussion.Forum forum);

    /**
     * <p>
     * Does the same thing as {@link #findByForum(org.openuss.discussion.Forum)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByForum(org.openuss.discussion.Forum)}.
     * </p>
     */
    public java.util.List findByForum(String queryString, org.openuss.discussion.Forum forum);

    /**
     * <p>
     * Does the same thing as {@link #findByForum(org.openuss.discussion.Forum)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByForum(int transform, org.openuss.discussion.Forum forum);

    /**
     * <p>
     * Does the same thing as {@link #findByForum(boolean, org.openuss.discussion.Forum)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByForum(int, org.openuss.discussion.Forum forum)}.
     * </p>
     */
    public java.util.List findByForum(int transform, String queryString, org.openuss.discussion.Forum forum);

    /**
 * 
     */
    public java.util.List loadTopicsWithViewState(org.openuss.discussion.Forum forum, org.openuss.security.User user);

    /**
 * 
     */
    public java.util.List findUsersToNotifyByTopic(org.openuss.discussion.Topic topic);

    /**
 * 
     */
    public java.util.List findUsersToNotifyByForum(org.openuss.discussion.Topic topic, org.openuss.discussion.Forum forum);

}
