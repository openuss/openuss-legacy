package org.openuss.discussion;

/**
 * @see org.openuss.discussion.DiscussionWatch
 */
public interface DiscussionWatchDao
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
     * Loads an instance of org.openuss.discussion.DiscussionWatch from the persistent store.
     */
    public org.openuss.discussion.DiscussionWatch load(org.openuss.discussion.DiscussionWatchPK discussionWatchPk);

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
    public Object load(int transform, org.openuss.discussion.DiscussionWatchPK discussionWatchPk);

    /**
     * Loads all entities of type {@link org.openuss.discussion.DiscussionWatch}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.discussion.DiscussionWatch> loadAll();

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
     * Creates an instance of org.openuss.discussion.DiscussionWatch and adds it to the persistent store.
     */
    public org.openuss.discussion.DiscussionWatch create(org.openuss.discussion.DiscussionWatch discussionWatch);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.discussion.DiscussionWatch)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.discussion.DiscussionWatch discussionWatch);

    /**
     * Creates a new instance of org.openuss.discussion.DiscussionWatch and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.discussion.DiscussionWatch
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.discussion.DiscussionWatch> create(java.util.Collection<org.openuss.discussion.DiscussionWatch> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.discussion.DiscussionWatch)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.discussion.DiscussionWatch> entities);

    /**
     * Updates the <code>discussionWatch</code> instance in the persistent store.
     */
    public void update(org.openuss.discussion.DiscussionWatch discussionWatch);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.discussion.DiscussionWatch> entities);

    /**
     * Removes the instance of org.openuss.discussion.DiscussionWatch from the persistent store.
     */
    public void remove(org.openuss.discussion.DiscussionWatch discussionWatch);

    /**
     * Removes the instance of org.openuss.discussion.DiscussionWatch having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(org.openuss.discussion.DiscussionWatchPK discussionWatchPk);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.discussion.DiscussionWatch> entities);

    /**
 * 
     */
    public java.util.List findByTopic(org.openuss.discussion.Topic topic);

    /**
     * <p>
     * Does the same thing as {@link #findByTopic(org.openuss.discussion.Topic)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByTopic(org.openuss.discussion.Topic)}.
     * </p>
     */
    public java.util.List findByTopic(String queryString, org.openuss.discussion.Topic topic);

    /**
     * <p>
     * Does the same thing as {@link #findByTopic(org.openuss.discussion.Topic)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByTopic(int transform, org.openuss.discussion.Topic topic);

    /**
     * <p>
     * Does the same thing as {@link #findByTopic(boolean, org.openuss.discussion.Topic)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByTopic(int, org.openuss.discussion.Topic topic)}.
     * </p>
     */
    public java.util.List findByTopic(int transform, String queryString, org.openuss.discussion.Topic topic);

    /**
 * 
     */
    public java.util.List findByUser(org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUser(org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUser(org.openuss.security.User)}.
     * </p>
     */
    public java.util.List findByUser(String queryString, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUser(org.openuss.security.User)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByUser(int transform, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUser(boolean, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUser(int, org.openuss.security.User user)}.
     * </p>
     */
    public java.util.List findByUser(int transform, String queryString, org.openuss.security.User user);

    /**
 * 
     */
    public org.openuss.discussion.DiscussionWatch findByTopicAndUser(org.openuss.discussion.Topic topic, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByTopicAndUser(org.openuss.discussion.Topic, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByTopicAndUser(org.openuss.discussion.Topic, org.openuss.security.User)}.
     * </p>
     */
    public org.openuss.discussion.DiscussionWatch findByTopicAndUser(String queryString, org.openuss.discussion.Topic topic, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByTopicAndUser(org.openuss.discussion.Topic, org.openuss.security.User)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByTopicAndUser(int transform, org.openuss.discussion.Topic topic, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByTopicAndUser(boolean, org.openuss.discussion.Topic, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByTopicAndUser(int, org.openuss.discussion.Topic topic, org.openuss.security.User user)}.
     * </p>
     */
    public Object findByTopicAndUser(int transform, String queryString, org.openuss.discussion.Topic topic, org.openuss.security.User user);

}
