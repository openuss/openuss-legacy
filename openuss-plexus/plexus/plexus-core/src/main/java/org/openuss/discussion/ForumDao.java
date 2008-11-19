package org.openuss.discussion;

/**
 * @see org.openuss.discussion.Forum
 */
public interface ForumDao
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
     * {@link org.openuss.discussion.ForumInfo}.
     */
    public final static int TRANSFORM_FORUMINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toForumInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toForumInfo(
        org.openuss.discussion.Forum sourceEntity,
        org.openuss.discussion.ForumInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.discussion.ForumInfo}.
     */
    public org.openuss.discussion.ForumInfo toForumInfo(org.openuss.discussion.Forum entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.discussion.ForumInfo}.
     */
    public void toForumInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.discussion.ForumInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void forumInfoToEntity(
        org.openuss.discussion.ForumInfo sourceVO,
        org.openuss.discussion.Forum targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.discussion.ForumInfo} to this DAO's entity.
     */
    public org.openuss.discussion.Forum forumInfoToEntity(org.openuss.discussion.ForumInfo forumInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.discussion.ForumInfo} to this
     * DAO's entity.
     */
    public void forumInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.discussion.Forum from the persistent store.
     */
    public org.openuss.discussion.Forum load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.discussion.Forum}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.discussion.Forum> loadAll();

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
     * Creates an instance of org.openuss.discussion.Forum and adds it to the persistent store.
     */
    public org.openuss.discussion.Forum create(org.openuss.discussion.Forum forum);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.discussion.Forum)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.discussion.Forum forum);

    /**
     * Creates a new instance of org.openuss.discussion.Forum and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.discussion.Forum
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.discussion.Forum> create(java.util.Collection<org.openuss.discussion.Forum> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.discussion.Forum)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.discussion.Forum> entities);

    /**
     * Updates the <code>forum</code> instance in the persistent store.
     */
    public void update(org.openuss.discussion.Forum forum);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.discussion.Forum> entities);

    /**
     * Removes the instance of org.openuss.discussion.Forum from the persistent store.
     */
    public void remove(org.openuss.discussion.Forum forum);

    /**
     * Removes the instance of org.openuss.discussion.Forum having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.discussion.Forum> entities);

    /**
 * 
     */
    public org.openuss.discussion.Forum findByDomainIdentifier(java.lang.Long domainIdentifier);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdentifier(java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainIdentifier(java.lang.Long)}.
     * </p>
     */
    public org.openuss.discussion.Forum findByDomainIdentifier(String queryString, java.lang.Long domainIdentifier);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdentifier(java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByDomainIdentifier(int transform, java.lang.Long domainIdentifier);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdentifier(boolean, java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainIdentifier(int, java.lang.Long domainIdentifier)}.
     * </p>
     */
    public Object findByDomainIdentifier(int transform, String queryString, java.lang.Long domainIdentifier);

}
