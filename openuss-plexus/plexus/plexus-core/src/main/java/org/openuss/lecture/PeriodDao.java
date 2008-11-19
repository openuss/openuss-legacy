package org.openuss.lecture;

/**
 * @see org.openuss.lecture.Period
 */
public interface PeriodDao
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
     * {@link org.openuss.lecture.PeriodInfo}.
     */
    public final static int TRANSFORM_PERIODINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toPeriodInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toPeriodInfo(
        org.openuss.lecture.Period sourceEntity,
        org.openuss.lecture.PeriodInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.lecture.PeriodInfo}.
     */
    public org.openuss.lecture.PeriodInfo toPeriodInfo(org.openuss.lecture.Period entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.lecture.PeriodInfo}.
     */
    public void toPeriodInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.lecture.PeriodInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void periodInfoToEntity(
        org.openuss.lecture.PeriodInfo sourceVO,
        org.openuss.lecture.Period targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.lecture.PeriodInfo} to this DAO's entity.
     */
    public org.openuss.lecture.Period periodInfoToEntity(org.openuss.lecture.PeriodInfo periodInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.lecture.PeriodInfo} to this
     * DAO's entity.
     */
    public void periodInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.lecture.Period from the persistent store.
     */
    public org.openuss.lecture.Period load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.lecture.Period}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.lecture.Period> loadAll();

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
     * Creates an instance of org.openuss.lecture.Period and adds it to the persistent store.
     */
    public org.openuss.lecture.Period create(org.openuss.lecture.Period period);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.Period)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.lecture.Period period);

    /**
     * Creates a new instance of org.openuss.lecture.Period and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.lecture.Period
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.lecture.Period> create(java.util.Collection<org.openuss.lecture.Period> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.Period)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.lecture.Period> entities);

    /**
     * Updates the <code>period</code> instance in the persistent store.
     */
    public void update(org.openuss.lecture.Period period);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.lecture.Period> entities);

    /**
     * Removes the instance of org.openuss.lecture.Period from the persistent store.
     */
    public void remove(org.openuss.lecture.Period period);

    /**
     * Removes the instance of org.openuss.lecture.Period having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.lecture.Period> entities);

    /**
 * 
     */
    public java.util.List findByUniversity(org.openuss.lecture.University university);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversity(org.openuss.lecture.University)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversity(org.openuss.lecture.University)}.
     * </p>
     */
    public java.util.List findByUniversity(String queryString, org.openuss.lecture.University university);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversity(org.openuss.lecture.University)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByUniversity(int transform, org.openuss.lecture.University university);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversity(boolean, org.openuss.lecture.University)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversity(int, org.openuss.lecture.University university)}.
     * </p>
     */
    public java.util.List findByUniversity(int transform, String queryString, org.openuss.lecture.University university);

}
