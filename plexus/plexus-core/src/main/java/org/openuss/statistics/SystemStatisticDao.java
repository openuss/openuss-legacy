package org.openuss.statistics;

/**
 * @see org.openuss.statistics.SystemStatistic
 */
public interface SystemStatisticDao
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
     * {@link org.openuss.statistics.SystemStatisticInfo}.
     */
    public final static int TRANSFORM_SYSTEMSTATISTICINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toSystemStatisticInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toSystemStatisticInfo(
        org.openuss.statistics.SystemStatistic sourceEntity,
        org.openuss.statistics.SystemStatisticInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.statistics.SystemStatisticInfo}.
     */
    public org.openuss.statistics.SystemStatisticInfo toSystemStatisticInfo(org.openuss.statistics.SystemStatistic entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.statistics.SystemStatisticInfo}.
     */
    public void toSystemStatisticInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.statistics.SystemStatisticInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void systemStatisticInfoToEntity(
        org.openuss.statistics.SystemStatisticInfo sourceVO,
        org.openuss.statistics.SystemStatistic targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.statistics.SystemStatisticInfo} to this DAO's entity.
     */
    public org.openuss.statistics.SystemStatistic systemStatisticInfoToEntity(org.openuss.statistics.SystemStatisticInfo systemStatisticInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.statistics.SystemStatisticInfo} to this
     * DAO's entity.
     */
    public void systemStatisticInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.statistics.SystemStatistic from the persistent store.
     */
    public org.openuss.statistics.SystemStatistic load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.statistics.SystemStatistic}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.statistics.SystemStatistic> loadAll();

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
     * Creates an instance of org.openuss.statistics.SystemStatistic and adds it to the persistent store.
     */
    public org.openuss.statistics.SystemStatistic create(org.openuss.statistics.SystemStatistic systemStatistic);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.statistics.SystemStatistic)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.statistics.SystemStatistic systemStatistic);

    /**
     * Creates a new instance of org.openuss.statistics.SystemStatistic and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.statistics.SystemStatistic
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.statistics.SystemStatistic> create(java.util.Collection<org.openuss.statistics.SystemStatistic> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.statistics.SystemStatistic)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.statistics.SystemStatistic> entities);

    /**
     * Updates the <code>systemStatistic</code> instance in the persistent store.
     */
    public void update(org.openuss.statistics.SystemStatistic systemStatistic);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.statistics.SystemStatistic> entities);

    /**
     * Removes the instance of org.openuss.statistics.SystemStatistic from the persistent store.
     */
    public void remove(org.openuss.statistics.SystemStatistic systemStatistic);

    /**
     * Removes the instance of org.openuss.statistics.SystemStatistic having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.statistics.SystemStatistic> entities);

    /**
 * 
     */
    public org.openuss.statistics.SystemStatistic findNewest();

    /**
 * 
     */
    public org.openuss.statistics.SystemStatistic current();

}
