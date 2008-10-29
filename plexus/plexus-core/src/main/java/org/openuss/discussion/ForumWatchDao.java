package org.openuss.discussion;

/**
 * @see org.openuss.discussion.ForumWatch
 */
public interface ForumWatchDao
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
     * Loads an instance of org.openuss.discussion.ForumWatch from the persistent store.
     */
    public org.openuss.discussion.ForumWatch load(org.openuss.discussion.ForumWatchPK forumWatchPk);

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
    public Object load(int transform, org.openuss.discussion.ForumWatchPK forumWatchPk);

    /**
     * Loads all entities of type {@link org.openuss.discussion.ForumWatch}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.discussion.ForumWatch> loadAll();

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
     * Creates an instance of org.openuss.discussion.ForumWatch and adds it to the persistent store.
     */
    public org.openuss.discussion.ForumWatch create(org.openuss.discussion.ForumWatch forumWatch);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.discussion.ForumWatch)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.discussion.ForumWatch forumWatch);

    /**
     * Creates a new instance of org.openuss.discussion.ForumWatch and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.discussion.ForumWatch
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.discussion.ForumWatch> create(java.util.Collection<org.openuss.discussion.ForumWatch> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.discussion.ForumWatch)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.discussion.ForumWatch> entities);

    /**
     * Updates the <code>forumWatch</code> instance in the persistent store.
     */
    public void update(org.openuss.discussion.ForumWatch forumWatch);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.discussion.ForumWatch> entities);

    /**
     * Removes the instance of org.openuss.discussion.ForumWatch from the persistent store.
     */
    public void remove(org.openuss.discussion.ForumWatch forumWatch);

    /**
     * Removes the instance of org.openuss.discussion.ForumWatch having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(org.openuss.discussion.ForumWatchPK forumWatchPk);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.discussion.ForumWatch> entities);

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
    public org.openuss.discussion.ForumWatch findByUserAndForum(org.openuss.security.User user, org.openuss.discussion.Forum forum);

    /**
     * <p>
     * Does the same thing as {@link #findByUserAndForum(org.openuss.security.User, org.openuss.discussion.Forum)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUserAndForum(org.openuss.security.User, org.openuss.discussion.Forum)}.
     * </p>
     */
    public org.openuss.discussion.ForumWatch findByUserAndForum(String queryString, org.openuss.security.User user, org.openuss.discussion.Forum forum);

    /**
     * <p>
     * Does the same thing as {@link #findByUserAndForum(org.openuss.security.User, org.openuss.discussion.Forum)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByUserAndForum(int transform, org.openuss.security.User user, org.openuss.discussion.Forum forum);

    /**
     * <p>
     * Does the same thing as {@link #findByUserAndForum(boolean, org.openuss.security.User, org.openuss.discussion.Forum)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUserAndForum(int, org.openuss.security.User user, org.openuss.discussion.Forum forum)}.
     * </p>
     */
    public Object findByUserAndForum(int transform, String queryString, org.openuss.security.User user, org.openuss.discussion.Forum forum);

}
