package org.openuss.collaboration;

/**
 * @see org.openuss.collaboration.Workspace
 */
public interface WorkspaceDao
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
     * {@link org.openuss.collaboration.WorkspaceInfo}.
     */
    public final static int TRANSFORM_WORKSPACEINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toWorkspaceInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toWorkspaceInfo(
        org.openuss.collaboration.Workspace sourceEntity,
        org.openuss.collaboration.WorkspaceInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.collaboration.WorkspaceInfo}.
     */
    public org.openuss.collaboration.WorkspaceInfo toWorkspaceInfo(org.openuss.collaboration.Workspace entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.collaboration.WorkspaceInfo}.
     */
    public void toWorkspaceInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.collaboration.WorkspaceInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void workspaceInfoToEntity(
        org.openuss.collaboration.WorkspaceInfo sourceVO,
        org.openuss.collaboration.Workspace targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.collaboration.WorkspaceInfo} to this DAO's entity.
     */
    public org.openuss.collaboration.Workspace workspaceInfoToEntity(org.openuss.collaboration.WorkspaceInfo workspaceInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.collaboration.WorkspaceInfo} to this
     * DAO's entity.
     */
    public void workspaceInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.collaboration.Workspace from the persistent store.
     */
    public org.openuss.collaboration.Workspace load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.collaboration.Workspace}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.collaboration.Workspace> loadAll();

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
     * Creates an instance of org.openuss.collaboration.Workspace and adds it to the persistent store.
     */
    public org.openuss.collaboration.Workspace create(org.openuss.collaboration.Workspace workspace);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.collaboration.Workspace)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.collaboration.Workspace workspace);

    /**
     * Creates a new instance of org.openuss.collaboration.Workspace and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.collaboration.Workspace
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.collaboration.Workspace> create(java.util.Collection<org.openuss.collaboration.Workspace> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.collaboration.Workspace)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.collaboration.Workspace> entities);

    /**
     * Updates the <code>workspace</code> instance in the persistent store.
     */
    public void update(org.openuss.collaboration.Workspace workspace);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.collaboration.Workspace> entities);

    /**
     * Removes the instance of org.openuss.collaboration.Workspace from the persistent store.
     */
    public void remove(org.openuss.collaboration.Workspace workspace);

    /**
     * Removes the instance of org.openuss.collaboration.Workspace having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.collaboration.Workspace> entities);

    /**
 * <p>
 * Finds all the workspaces created under a domain-object.
 * </p>
     */
    public java.util.List findByDomainId(java.lang.Long domainId);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainId(java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainId(java.lang.Long)}.
     * </p>
     */
    public java.util.List findByDomainId(String queryString, java.lang.Long domainId);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainId(java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByDomainId(int transform, java.lang.Long domainId);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainId(boolean, java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainId(int, java.lang.Long domainId)}.
     * </p>
     */
    public java.util.List findByDomainId(int transform, String queryString, java.lang.Long domainId);

    /**
 * <p>
 * Finds all the workspaces created under a domain-object and
 * according to a concrete User
 * </p>
     */
    public java.util.List findByDomainIdAndUser(java.lang.Long domainId, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdAndUser(java.lang.Long, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainIdAndUser(java.lang.Long, org.openuss.security.User)}.
     * </p>
     */
    public java.util.List findByDomainIdAndUser(String queryString, java.lang.Long domainId, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdAndUser(java.lang.Long, org.openuss.security.User)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByDomainIdAndUser(int transform, java.lang.Long domainId, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdAndUser(boolean, java.lang.Long, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainIdAndUser(int, java.lang.Long domainId, org.openuss.security.User user)}.
     * </p>
     */
    public java.util.List findByDomainIdAndUser(int transform, String queryString, java.lang.Long domainId, org.openuss.security.User user);

    /**
 * 
     */
    public java.util.List findByUser(org.openuss.security.User user);

}
