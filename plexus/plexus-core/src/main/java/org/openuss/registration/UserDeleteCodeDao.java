package org.openuss.registration;

/**
 * @see org.openuss.registration.UserDeleteCode
 */
public interface UserDeleteCodeDao
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
     * Loads an instance of org.openuss.registration.UserDeleteCode from the persistent store.
     */
    public org.openuss.registration.UserDeleteCode load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.registration.UserDeleteCode}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.registration.UserDeleteCode> loadAll();

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
     * Creates an instance of org.openuss.registration.UserDeleteCode and adds it to the persistent store.
     */
    public org.openuss.registration.UserDeleteCode create(org.openuss.registration.UserDeleteCode userDeleteCode);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.registration.UserDeleteCode)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.registration.UserDeleteCode userDeleteCode);

    /**
     * Creates a new instance of org.openuss.registration.UserDeleteCode and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.registration.UserDeleteCode
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.registration.UserDeleteCode> create(java.util.Collection<org.openuss.registration.UserDeleteCode> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.registration.UserDeleteCode)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.registration.UserDeleteCode> entities);

    /**
     * Updates the <code>userDeleteCode</code> instance in the persistent store.
     */
    public void update(org.openuss.registration.UserDeleteCode userDeleteCode);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.registration.UserDeleteCode> entities);

    /**
     * Removes the instance of org.openuss.registration.UserDeleteCode from the persistent store.
     */
    public void remove(org.openuss.registration.UserDeleteCode userDeleteCode);

    /**
     * Removes the instance of org.openuss.registration.UserDeleteCode having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.registration.UserDeleteCode> entities);

    /**
 * 
     */
    public org.openuss.registration.UserDeleteCode findByCode(java.lang.String code);

    /**
     * <p>
     * Does the same thing as {@link #findByCode(java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByCode(java.lang.String)}.
     * </p>
     */
    public org.openuss.registration.UserDeleteCode findByCode(String queryString, java.lang.String code);

    /**
     * <p>
     * Does the same thing as {@link #findByCode(java.lang.String)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByCode(int transform, java.lang.String code);

    /**
     * <p>
     * Does the same thing as {@link #findByCode(boolean, java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByCode(int, java.lang.String code)}.
     * </p>
     */
    public Object findByCode(int transform, String queryString, java.lang.String code);

}
