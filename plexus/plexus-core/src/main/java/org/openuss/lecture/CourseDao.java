
package org.openuss.lecture;

/**
 * @see org.openuss.lecture.Course
 */
public interface CourseDao
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
     * {@link org.openuss.lecture.CourseInfo}.
     */
    public final static int TRANSFORM_COURSEINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toCourseInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toCourseInfo(
        org.openuss.lecture.Course sourceEntity,
        org.openuss.lecture.CourseInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.lecture.CourseInfo}.
     */
    public org.openuss.lecture.CourseInfo toCourseInfo(org.openuss.lecture.Course entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.lecture.CourseInfo}.
     */
    public void toCourseInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.lecture.CourseInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void courseInfoToEntity(
        org.openuss.lecture.CourseInfo sourceVO,
        org.openuss.lecture.Course targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.lecture.CourseInfo} to this DAO's entity.
     */
    public org.openuss.lecture.Course courseInfoToEntity(org.openuss.lecture.CourseInfo courseInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.lecture.CourseInfo} to this
     * DAO's entity.
     */
    public void courseInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.lecture.Course from the persistent store.
     */
    public org.openuss.lecture.Course load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.lecture.Course}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.lecture.Course> loadAll();

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
     * Creates an instance of org.openuss.lecture.Course and adds it to the persistent store.
     */
    public org.openuss.lecture.Course create(org.openuss.lecture.Course course);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.Course)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.lecture.Course course);

    /**
     * Creates a new instance of org.openuss.lecture.Course and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.lecture.Course
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.lecture.Course> create(java.util.Collection<org.openuss.lecture.Course> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.Course)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.lecture.Course> entities);

    /**
     * Updates the <code>course</code> instance in the persistent store.
     */
    public void update(org.openuss.lecture.Course course);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.lecture.Course> entities);

    /**
     * Removes the instance of org.openuss.lecture.Course from the persistent store.
     */
    public void remove(org.openuss.lecture.Course course);

    /**
     * Removes the instance of org.openuss.lecture.Course having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.lecture.Course> entities);

    /**
 * 
     */
    public org.openuss.lecture.Course findByShortcut(java.lang.String shortcut);

    /**
     * <p>
     * Does the same thing as {@link #findByShortcut(java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByShortcut(java.lang.String)}.
     * </p>
     */
    public org.openuss.lecture.Course findByShortcut(String queryString, java.lang.String shortcut);

    /**
     * <p>
     * Does the same thing as {@link #findByShortcut(java.lang.String)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByShortcut(int transform, java.lang.String shortcut);

    /**
     * <p>
     * Does the same thing as {@link #findByShortcut(boolean, java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByShortcut(int, java.lang.String shortcut)}.
     * </p>
     */
    public Object findByShortcut(int transform, String queryString, java.lang.String shortcut);

    /**
 * 
     */
    public java.util.List findByCourseType(org.openuss.lecture.CourseType courseType);

    /**
     * <p>
     * Does the same thing as {@link #findByCourseType(org.openuss.lecture.CourseType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByCourseType(org.openuss.lecture.CourseType)}.
     * </p>
     */
    public java.util.List findByCourseType(String queryString, org.openuss.lecture.CourseType courseType);

    /**
     * <p>
     * Does the same thing as {@link #findByCourseType(org.openuss.lecture.CourseType)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByCourseType(int transform, org.openuss.lecture.CourseType courseType);

    /**
     * <p>
     * Does the same thing as {@link #findByCourseType(boolean, org.openuss.lecture.CourseType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByCourseType(int, org.openuss.lecture.CourseType courseType)}.
     * </p>
     */
    public java.util.List findByCourseType(int transform, String queryString, org.openuss.lecture.CourseType courseType);

    /**
 * 
     */
    public java.util.List findByPeriod(org.openuss.lecture.Period period);

    /**
     * <p>
     * Does the same thing as {@link #findByPeriod(org.openuss.lecture.Period)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByPeriod(org.openuss.lecture.Period)}.
     * </p>
     */
    public java.util.List findByPeriod(String queryString, org.openuss.lecture.Period period);

    /**
     * <p>
     * Does the same thing as {@link #findByPeriod(org.openuss.lecture.Period)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByPeriod(int transform, org.openuss.lecture.Period period);

    /**
     * <p>
     * Does the same thing as {@link #findByPeriod(boolean, org.openuss.lecture.Period)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByPeriod(int, org.openuss.lecture.Period period)}.
     * </p>
     */
    public java.util.List findByPeriod(int transform, String queryString, org.openuss.lecture.Period period);

    /**
 * 
     */
    public java.util.List findByPeriodAndCourseType(org.openuss.lecture.Period period, org.openuss.lecture.CourseType courseType);

    /**
     * <p>
     * Does the same thing as {@link #findByPeriodAndCourseType(org.openuss.lecture.Period, org.openuss.lecture.CourseType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByPeriodAndCourseType(org.openuss.lecture.Period, org.openuss.lecture.CourseType)}.
     * </p>
     */
    public java.util.List findByPeriodAndCourseType(String queryString, org.openuss.lecture.Period period, org.openuss.lecture.CourseType courseType);

    /**
     * <p>
     * Does the same thing as {@link #findByPeriodAndCourseType(org.openuss.lecture.Period, org.openuss.lecture.CourseType)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByPeriodAndCourseType(int transform, org.openuss.lecture.Period period, org.openuss.lecture.CourseType courseType);

    /**
     * <p>
     * Does the same thing as {@link #findByPeriodAndCourseType(boolean, org.openuss.lecture.Period, org.openuss.lecture.CourseType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByPeriodAndCourseType(int, org.openuss.lecture.Period period, org.openuss.lecture.CourseType courseType)}.
     * </p>
     */
    public java.util.List findByPeriodAndCourseType(int transform, String queryString, org.openuss.lecture.Period period, org.openuss.lecture.CourseType courseType);

    /**
 * 
     */
    public java.util.List findByPeriodAndEnabled(org.openuss.lecture.Period period, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByPeriodAndEnabled(org.openuss.lecture.Period, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByPeriodAndEnabled(org.openuss.lecture.Period, boolean)}.
     * </p>
     */
    public java.util.List findByPeriodAndEnabled(String queryString, org.openuss.lecture.Period period, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByPeriodAndEnabled(org.openuss.lecture.Period, boolean)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByPeriodAndEnabled(int transform, org.openuss.lecture.Period period, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByPeriodAndEnabled(boolean, org.openuss.lecture.Period, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByPeriodAndEnabled(int, org.openuss.lecture.Period period, boolean enabled)}.
     * </p>
     */
    public java.util.List findByPeriodAndEnabled(int transform, String queryString, org.openuss.lecture.Period period, boolean enabled);

    /**
 * 
     */
    public java.util.List findByEnabled(boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByEnabled(boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByEnabled(boolean)}.
     * </p>
     */
    public java.util.List findByEnabled(String queryString, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByEnabled(boolean)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByEnabled(int transform, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByEnabled(boolean, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByEnabled(int, boolean enabled)}.
     * </p>
     */
    public java.util.List findByEnabled(int transform, String queryString, boolean enabled);

}
