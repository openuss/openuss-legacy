package org.openuss.security.ldap;

/**
 * @see org.openuss.security.ldap.UserDnPattern
 */
public interface UserDnPatternDao
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
     * {@link org.openuss.security.ldap.UserDnPatternInfo}.
     */
    public final static int TRANSFORM_USERDNPATTERNINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toUserDnPatternInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toUserDnPatternInfo(
        org.openuss.security.ldap.UserDnPattern sourceEntity,
        org.openuss.security.ldap.UserDnPatternInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.security.ldap.UserDnPatternInfo}.
     */
    public org.openuss.security.ldap.UserDnPatternInfo toUserDnPatternInfo(org.openuss.security.ldap.UserDnPattern entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.security.ldap.UserDnPatternInfo}.
     */
    public void toUserDnPatternInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.security.ldap.UserDnPatternInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void userDnPatternInfoToEntity(
        org.openuss.security.ldap.UserDnPatternInfo sourceVO,
        org.openuss.security.ldap.UserDnPattern targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.security.ldap.UserDnPatternInfo} to this DAO's entity.
     */
    public org.openuss.security.ldap.UserDnPattern userDnPatternInfoToEntity(org.openuss.security.ldap.UserDnPatternInfo userDnPatternInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.security.ldap.UserDnPatternInfo} to this
     * DAO's entity.
     */
    public void userDnPatternInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.security.ldap.UserDnPattern from the persistent store.
     */
    public org.openuss.security.ldap.UserDnPattern load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.security.ldap.UserDnPattern}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.security.ldap.UserDnPattern> loadAll();

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
     * Creates an instance of org.openuss.security.ldap.UserDnPattern and adds it to the persistent store.
     */
    public org.openuss.security.ldap.UserDnPattern create(org.openuss.security.ldap.UserDnPattern userDnPattern);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.security.ldap.UserDnPattern)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.security.ldap.UserDnPattern userDnPattern);

    /**
     * Creates a new instance of org.openuss.security.ldap.UserDnPattern and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.security.ldap.UserDnPattern
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.security.ldap.UserDnPattern> create(java.util.Collection<org.openuss.security.ldap.UserDnPattern> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.security.ldap.UserDnPattern)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.security.ldap.UserDnPattern> entities);

    /**
     * Updates the <code>userDnPattern</code> instance in the persistent store.
     */
    public void update(org.openuss.security.ldap.UserDnPattern userDnPattern);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.security.ldap.UserDnPattern> entities);

    /**
     * Removes the instance of org.openuss.security.ldap.UserDnPattern from the persistent store.
     */
    public void remove(org.openuss.security.ldap.UserDnPattern userDnPattern);

    /**
     * Removes the instance of org.openuss.security.ldap.UserDnPattern having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.security.ldap.UserDnPattern> entities);

    /**
 * 
     */
    public org.openuss.security.ldap.UserDnPattern findByName(java.lang.String name);

    /**
     * <p>
     * Does the same thing as {@link #findByName(java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByName(java.lang.String)}.
     * </p>
     */
    public org.openuss.security.ldap.UserDnPattern findByName(String queryString, java.lang.String name);

    /**
     * <p>
     * Does the same thing as {@link #findByName(java.lang.String)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByName(int transform, java.lang.String name);

    /**
     * <p>
     * Does the same thing as {@link #findByName(boolean, java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByName(int, java.lang.String name)}.
     * </p>
     */
    public Object findByName(int transform, String queryString, java.lang.String name);

}
