package org.openuss.registration;

/**
 * @see org.openuss.registration.ActivationCode
 */
public interface ActivationCodeDao
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
     * Loads an instance of org.openuss.registration.ActivationCode from the persistent store.
     */
    public org.openuss.registration.ActivationCode load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.registration.ActivationCode}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.registration.ActivationCode> loadAll();

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
     * Creates an instance of org.openuss.registration.ActivationCode and adds it to the persistent store.
     */
    public org.openuss.registration.ActivationCode create(org.openuss.registration.ActivationCode activationCode);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.registration.ActivationCode)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.registration.ActivationCode activationCode);

    /**
     * Creates a new instance of org.openuss.registration.ActivationCode and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.registration.ActivationCode
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.registration.ActivationCode> create(java.util.Collection<org.openuss.registration.ActivationCode> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.registration.ActivationCode)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.registration.ActivationCode> entities);

    /**
     * Updates the <code>activationCode</code> instance in the persistent store.
     */
    public void update(org.openuss.registration.ActivationCode activationCode);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.registration.ActivationCode> entities);

    /**
     * Removes the instance of org.openuss.registration.ActivationCode from the persistent store.
     */
    public void remove(org.openuss.registration.ActivationCode activationCode);

    /**
     * Removes the instance of org.openuss.registration.ActivationCode having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.registration.ActivationCode> entities);

}
