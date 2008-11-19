package org.openuss.messaging;

/**
 * @see org.openuss.messaging.MessageJob
 */
public interface MessageJobDao
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
     * {@link org.openuss.messaging.JobInfo}.
     */
    public final static int TRANSFORM_JOBINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toJobInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toJobInfo(
        org.openuss.messaging.MessageJob sourceEntity,
        org.openuss.messaging.JobInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.messaging.JobInfo}.
     */
    public org.openuss.messaging.JobInfo toJobInfo(org.openuss.messaging.MessageJob entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.messaging.JobInfo}.
     */
    public void toJobInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.messaging.JobInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void jobInfoToEntity(
        org.openuss.messaging.JobInfo sourceVO,
        org.openuss.messaging.MessageJob targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.messaging.JobInfo} to this DAO's entity.
     */
    public org.openuss.messaging.MessageJob jobInfoToEntity(org.openuss.messaging.JobInfo jobInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.messaging.JobInfo} to this
     * DAO's entity.
     */
    public void jobInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.messaging.MessageJob from the persistent store.
     */
    public org.openuss.messaging.MessageJob load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.messaging.MessageJob}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.messaging.MessageJob> loadAll();

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
     * Creates an instance of org.openuss.messaging.MessageJob and adds it to the persistent store.
     */
    public org.openuss.messaging.MessageJob create(org.openuss.messaging.MessageJob messageJob);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.messaging.MessageJob)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.messaging.MessageJob messageJob);

    /**
     * Creates a new instance of org.openuss.messaging.MessageJob and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.messaging.MessageJob
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.messaging.MessageJob> create(java.util.Collection<org.openuss.messaging.MessageJob> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.messaging.MessageJob)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.messaging.MessageJob> entities);

    /**
     * Updates the <code>messageJob</code> instance in the persistent store.
     */
    public void update(org.openuss.messaging.MessageJob messageJob);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.messaging.MessageJob> entities);

    /**
     * Removes the instance of org.openuss.messaging.MessageJob from the persistent store.
     */
    public void remove(org.openuss.messaging.MessageJob messageJob);

    /**
     * Removes the instance of org.openuss.messaging.MessageJob having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.messaging.MessageJob> entities);

    /**
 * 
     */
    public java.util.List findByState(org.openuss.messaging.JobState state);

    /**
     * <p>
     * Does the same thing as {@link #findByState(org.openuss.messaging.JobState)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByState(org.openuss.messaging.JobState)}.
     * </p>
     */
    public java.util.List findByState(String queryString, org.openuss.messaging.JobState state);

    /**
     * <p>
     * Does the same thing as {@link #findByState(org.openuss.messaging.JobState)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByState(int transform, org.openuss.messaging.JobState state);

    /**
     * <p>
     * Does the same thing as {@link #findByState(boolean, org.openuss.messaging.JobState)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByState(int, org.openuss.messaging.JobState state)}.
     * </p>
     */
    public java.util.List findByState(int transform, String queryString, org.openuss.messaging.JobState state);

}
