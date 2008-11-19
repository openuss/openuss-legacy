
package org.openuss.security.acl;

/**
 * @see org.openuss.security.acl.Permission
 */
public interface PermissionDao
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
     * Loads an instance of org.openuss.security.acl.Permission from the persistent store.
     */
    public org.openuss.security.acl.Permission load(org.openuss.security.acl.PermissionPK permissionPk);

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
    public Object load(int transform, org.openuss.security.acl.PermissionPK permissionPk);

    /**
     * Loads all entities of type {@link org.openuss.security.acl.Permission}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.security.acl.Permission> loadAll();

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
     * Creates an instance of org.openuss.security.acl.Permission and adds it to the persistent store.
     */
    public org.openuss.security.acl.Permission create(org.openuss.security.acl.Permission permission);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.security.acl.Permission)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.security.acl.Permission permission);

    /**
     * Creates a new instance of org.openuss.security.acl.Permission and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.security.acl.Permission
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.security.acl.Permission> create(java.util.Collection<org.openuss.security.acl.Permission> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.security.acl.Permission)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.security.acl.Permission> entities);

    /**
     * Updates the <code>permission</code> instance in the persistent store.
     */
    public void update(org.openuss.security.acl.Permission permission);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.security.acl.Permission> entities);

    /**
     * Removes the instance of org.openuss.security.acl.Permission from the persistent store.
     */
    public void remove(org.openuss.security.acl.Permission permission);

    /**
     * Removes the instance of org.openuss.security.acl.Permission having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(org.openuss.security.acl.PermissionPK permissionPk);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.security.acl.Permission> entities);

    /**
 * 
     */
    public org.openuss.security.acl.Permission findPermission(org.openuss.security.acl.ObjectIdentity aclObjectIdentity, org.openuss.security.Authority recipient);

    /**
     * <p>
     * Does the same thing as {@link #findPermission(org.openuss.security.acl.ObjectIdentity, org.openuss.security.Authority)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findPermission(org.openuss.security.acl.ObjectIdentity, org.openuss.security.Authority)}.
     * </p>
     */
    public org.openuss.security.acl.Permission findPermission(String queryString, org.openuss.security.acl.ObjectIdentity aclObjectIdentity, org.openuss.security.Authority recipient);

    /**
     * <p>
     * Does the same thing as {@link #findPermission(org.openuss.security.acl.ObjectIdentity, org.openuss.security.Authority)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findPermission(int transform, org.openuss.security.acl.ObjectIdentity aclObjectIdentity, org.openuss.security.Authority recipient);

    /**
     * <p>
     * Does the same thing as {@link #findPermission(boolean, org.openuss.security.acl.ObjectIdentity, org.openuss.security.Authority)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findPermission(int, org.openuss.security.acl.ObjectIdentity aclObjectIdentity, org.openuss.security.Authority recipient)}.
     * </p>
     */
    public Object findPermission(int transform, String queryString, org.openuss.security.acl.ObjectIdentity aclObjectIdentity, org.openuss.security.Authority recipient);

    /**
 * 
     */
    public java.util.List findPermissions(org.openuss.security.acl.ObjectIdentity aclObjectIdentity);

    /**
     * <p>
     * Does the same thing as {@link #findPermissions(org.openuss.security.acl.ObjectIdentity)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findPermissions(org.openuss.security.acl.ObjectIdentity)}.
     * </p>
     */
    public java.util.List findPermissions(String queryString, org.openuss.security.acl.ObjectIdentity aclObjectIdentity);

    /**
     * <p>
     * Does the same thing as {@link #findPermissions(org.openuss.security.acl.ObjectIdentity)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findPermissions(int transform, org.openuss.security.acl.ObjectIdentity aclObjectIdentity);

    /**
     * <p>
     * Does the same thing as {@link #findPermissions(boolean, org.openuss.security.acl.ObjectIdentity)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findPermissions(int, org.openuss.security.acl.ObjectIdentity aclObjectIdentity)}.
     * </p>
     */
    public java.util.List findPermissions(int transform, String queryString, org.openuss.security.acl.ObjectIdentity aclObjectIdentity);

    /**
 * 
     */
    public java.util.List findPermissionsByAuthority(org.openuss.security.Authority recipient);

    /**
     * <p>
     * Does the same thing as {@link #findPermissionsByAuthority(org.openuss.security.Authority)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findPermissionsByAuthority(org.openuss.security.Authority)}.
     * </p>
     */
    public java.util.List findPermissionsByAuthority(String queryString, org.openuss.security.Authority recipient);

    /**
     * <p>
     * Does the same thing as {@link #findPermissionsByAuthority(org.openuss.security.Authority)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findPermissionsByAuthority(int transform, org.openuss.security.Authority recipient);

    /**
     * <p>
     * Does the same thing as {@link #findPermissionsByAuthority(boolean, org.openuss.security.Authority)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findPermissionsByAuthority(int, org.openuss.security.Authority recipient)}.
     * </p>
     */
    public java.util.List findPermissionsByAuthority(int transform, String queryString, org.openuss.security.Authority recipient);

    /**
 * 
     */
    public java.util.List findPermissionsWithRecipient(org.openuss.security.acl.ObjectIdentity objectIdentity);

    /**
     * <p>
     * Does the same thing as {@link #findPermissionsWithRecipient(org.openuss.security.acl.ObjectIdentity)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findPermissionsWithRecipient(org.openuss.security.acl.ObjectIdentity)}.
     * </p>
     */
    public java.util.List findPermissionsWithRecipient(String queryString, org.openuss.security.acl.ObjectIdentity objectIdentity);

    /**
     * <p>
     * Does the same thing as {@link #findPermissionsWithRecipient(org.openuss.security.acl.ObjectIdentity)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findPermissionsWithRecipient(int transform, org.openuss.security.acl.ObjectIdentity objectIdentity);

    /**
     * <p>
     * Does the same thing as {@link #findPermissionsWithRecipient(boolean, org.openuss.security.acl.ObjectIdentity)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findPermissionsWithRecipient(int, org.openuss.security.acl.ObjectIdentity objectIdentity)}.
     * </p>
     */
    public java.util.List findPermissionsWithRecipient(int transform, String queryString, org.openuss.security.acl.ObjectIdentity objectIdentity);

}
