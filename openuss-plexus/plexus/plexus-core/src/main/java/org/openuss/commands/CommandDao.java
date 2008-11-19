package org.openuss.commands;

/**
 * @see org.openuss.commands.Command
 */
public interface CommandDao
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
     * Loads an instance of org.openuss.commands.Command from the persistent store.
     */
    public org.openuss.commands.Command load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.commands.Command}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.commands.Command> loadAll();

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
     * Creates an instance of org.openuss.commands.Command and adds it to the persistent store.
     */
    public org.openuss.commands.Command create(org.openuss.commands.Command command);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.commands.Command)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.commands.Command command);

    /**
     * Creates a new instance of org.openuss.commands.Command and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.commands.Command
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.commands.Command> create(java.util.Collection<org.openuss.commands.Command> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.commands.Command)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.commands.Command> entities);

    /**
     * Updates the <code>command</code> instance in the persistent store.
     */
    public void update(org.openuss.commands.Command command);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.commands.Command> entities);

    /**
     * Removes the instance of org.openuss.commands.Command from the persistent store.
     */
    public void remove(org.openuss.commands.Command command);

    /**
     * Removes the instance of org.openuss.commands.Command having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.commands.Command> entities);

    /**
 * 
     */
    public java.util.List findAllEachCommandsAfter(java.lang.Long commandId);

    /**
     * <p>
     * Does the same thing as {@link #findAllEachCommandsAfter(java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findAllEachCommandsAfter(java.lang.Long)}.
     * </p>
     */
    public java.util.List findAllEachCommandsAfter(String queryString, java.lang.Long commandId);

    /**
     * <p>
     * Does the same thing as {@link #findAllEachCommandsAfter(java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findAllEachCommandsAfter(int transform, java.lang.Long commandId);

    /**
     * <p>
     * Does the same thing as {@link #findAllEachCommandsAfter(boolean, java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findAllEachCommandsAfter(int, java.lang.Long commandId)}.
     * </p>
     */
    public java.util.List findAllEachCommandsAfter(int transform, String queryString, java.lang.Long commandId);

    /**
 * 
     */
    public java.util.List findAllOnceCommands();

    /**
     * <p>
     * Does the same thing as {@link #findAllOnceCommands()} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findAllOnceCommands()}.
     * </p>
     */
    public java.util.List findAllOnceCommands(String queryString);

    /**
     * <p>
     * Does the same thing as {@link #findAllOnceCommands()} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findAllOnceCommands(int transform);

    /**
     * <p>
     * Does the same thing as {@link #findAllOnceCommands(boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findAllOnceCommands(int)}.
     * </p>
     */
    public java.util.List findAllOnceCommands(int transform, String queryString);

}
