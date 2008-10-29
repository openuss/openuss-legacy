package org.openuss.statistics;

/**
 * @see org.openuss.statistics.OnlineSession
 */
public interface OnlineSessionDao
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
     * {@link org.openuss.statistics.OnlineUserInfo}.
     */
    public final static int TRANSFORM_ONLINEUSERINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toOnlineUserInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toOnlineUserInfo(
        org.openuss.statistics.OnlineSession sourceEntity,
        org.openuss.statistics.OnlineUserInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.statistics.OnlineUserInfo}.
     */
    public org.openuss.statistics.OnlineUserInfo toOnlineUserInfo(org.openuss.statistics.OnlineSession entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.statistics.OnlineUserInfo}.
     */
    public void toOnlineUserInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.statistics.OnlineUserInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void onlineUserInfoToEntity(
        org.openuss.statistics.OnlineUserInfo sourceVO,
        org.openuss.statistics.OnlineSession targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.statistics.OnlineUserInfo} to this DAO's entity.
     */
    public org.openuss.statistics.OnlineSession onlineUserInfoToEntity(org.openuss.statistics.OnlineUserInfo onlineUserInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.statistics.OnlineUserInfo} to this
     * DAO's entity.
     */
    public void onlineUserInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.statistics.OnlineSession from the persistent store.
     */
    public org.openuss.statistics.OnlineSession load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.statistics.OnlineSession}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.statistics.OnlineSession> loadAll();

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
     * Creates an instance of org.openuss.statistics.OnlineSession and adds it to the persistent store.
     */
    public org.openuss.statistics.OnlineSession create(org.openuss.statistics.OnlineSession onlineSession);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.statistics.OnlineSession)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.statistics.OnlineSession onlineSession);

    /**
     * Creates a new instance of org.openuss.statistics.OnlineSession and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.statistics.OnlineSession
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.statistics.OnlineSession> create(java.util.Collection<org.openuss.statistics.OnlineSession> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.statistics.OnlineSession)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.statistics.OnlineSession> entities);

    /**
     * Updates the <code>onlineSession</code> instance in the persistent store.
     */
    public void update(org.openuss.statistics.OnlineSession onlineSession);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.statistics.OnlineSession> entities);

    /**
     * Removes the instance of org.openuss.statistics.OnlineSession from the persistent store.
     */
    public void remove(org.openuss.statistics.OnlineSession onlineSession);

    /**
     * Removes the instance of org.openuss.statistics.OnlineSession having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.statistics.OnlineSession> entities);

    /**
 * 
     */
    public java.util.List findUserSessions(org.openuss.statistics.UserSessionCriteria criteria);

    /**
     * <p>
     * Does the same thing as {@link #findUserSessions(org.openuss.statistics.UserSessionCriteria)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findUserSessions(final int transform, final org.openuss.statistics.UserSessionCriteria criteria);

    /**
 * 
     */
    public java.util.List findActiveSessionByUser(org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findActiveSessionByUser(org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findActiveSessionByUser(org.openuss.security.User)}.
     * </p>
     */
    public java.util.List findActiveSessionByUser(String queryString, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findActiveSessionByUser(org.openuss.security.User)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findActiveSessionByUser(int transform, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findActiveSessionByUser(boolean, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findActiveSessionByUser(int, org.openuss.security.User user)}.
     * </p>
     */
    public java.util.List findActiveSessionByUser(int transform, String queryString, org.openuss.security.User user);

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
    public java.util.List findActiveUsers();

    /**
 * 
     */
    public org.openuss.statistics.OnlineInfo loadOnlineInfo();

}
