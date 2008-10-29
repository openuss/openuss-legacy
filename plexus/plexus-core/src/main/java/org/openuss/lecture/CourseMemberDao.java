package org.openuss.lecture;

/**
 * @see org.openuss.lecture.CourseMember
 */
public interface CourseMemberDao
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
     * {@link org.openuss.lecture.CourseMemberInfo}.
     */
    public final static int TRANSFORM_COURSEMEMBERINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toCourseMemberInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toCourseMemberInfo(
        org.openuss.lecture.CourseMember sourceEntity,
        org.openuss.lecture.CourseMemberInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.lecture.CourseMemberInfo}.
     */
    public org.openuss.lecture.CourseMemberInfo toCourseMemberInfo(org.openuss.lecture.CourseMember entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.lecture.CourseMemberInfo}.
     */
    public void toCourseMemberInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.lecture.CourseMemberInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void courseMemberInfoToEntity(
        org.openuss.lecture.CourseMemberInfo sourceVO,
        org.openuss.lecture.CourseMember targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.lecture.CourseMemberInfo} to this DAO's entity.
     */
    public org.openuss.lecture.CourseMember courseMemberInfoToEntity(org.openuss.lecture.CourseMemberInfo courseMemberInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.lecture.CourseMemberInfo} to this
     * DAO's entity.
     */
    public void courseMemberInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.lecture.CourseMember from the persistent store.
     */
    public org.openuss.lecture.CourseMember load(org.openuss.lecture.CourseMemberPK courseMemberPk);

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
    public Object load(int transform, org.openuss.lecture.CourseMemberPK courseMemberPk);

    /**
     * Loads all entities of type {@link org.openuss.lecture.CourseMember}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.lecture.CourseMember> loadAll();

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
     * Creates an instance of org.openuss.lecture.CourseMember and adds it to the persistent store.
     */
    public org.openuss.lecture.CourseMember create(org.openuss.lecture.CourseMember courseMember);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.CourseMember)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.lecture.CourseMember courseMember);

    /**
     * Creates a new instance of org.openuss.lecture.CourseMember and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.lecture.CourseMember
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.lecture.CourseMember> create(java.util.Collection<org.openuss.lecture.CourseMember> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.CourseMember)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.lecture.CourseMember> entities);

    /**
     * Updates the <code>courseMember</code> instance in the persistent store.
     */
    public void update(org.openuss.lecture.CourseMember courseMember);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.lecture.CourseMember> entities);

    /**
     * Removes the instance of org.openuss.lecture.CourseMember from the persistent store.
     */
    public void remove(org.openuss.lecture.CourseMember courseMember);

    /**
     * Removes the instance of org.openuss.lecture.CourseMember having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(org.openuss.lecture.CourseMemberPK courseMemberPk);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.lecture.CourseMember> entities);

    /**
 * 
     */
    public java.util.List findByCourse(org.openuss.lecture.Course course);

    /**
     * <p>
     * Does the same thing as {@link #findByCourse(org.openuss.lecture.Course)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByCourse(org.openuss.lecture.Course)}.
     * </p>
     */
    public java.util.List findByCourse(String queryString, org.openuss.lecture.Course course);

    /**
     * <p>
     * Does the same thing as {@link #findByCourse(org.openuss.lecture.Course)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByCourse(int transform, org.openuss.lecture.Course course);

    /**
     * <p>
     * Does the same thing as {@link #findByCourse(boolean, org.openuss.lecture.Course)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByCourse(int, org.openuss.lecture.Course course)}.
     * </p>
     */
    public java.util.List findByCourse(int transform, String queryString, org.openuss.lecture.Course course);

    /**
 * 
     */
    public java.util.List findByType(org.openuss.lecture.Course course, org.openuss.lecture.CourseMemberType memberType);

    /**
     * <p>
     * Does the same thing as {@link #findByType(org.openuss.lecture.Course, org.openuss.lecture.CourseMemberType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByType(org.openuss.lecture.Course, org.openuss.lecture.CourseMemberType)}.
     * </p>
     */
    public java.util.List findByType(String queryString, org.openuss.lecture.Course course, org.openuss.lecture.CourseMemberType memberType);

    /**
     * <p>
     * Does the same thing as {@link #findByType(org.openuss.lecture.Course, org.openuss.lecture.CourseMemberType)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByType(int transform, org.openuss.lecture.Course course, org.openuss.lecture.CourseMemberType memberType);

    /**
     * <p>
     * Does the same thing as {@link #findByType(boolean, org.openuss.lecture.Course, org.openuss.lecture.CourseMemberType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByType(int, org.openuss.lecture.Course course, org.openuss.lecture.CourseMemberType memberType)}.
     * </p>
     */
    public java.util.List findByType(int transform, String queryString, org.openuss.lecture.Course course, org.openuss.lecture.CourseMemberType memberType);

    /**
 * 
     */
    public java.util.List findByUser(org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUser(org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUser(org.openuss.security.User)}.
     * </p>
     */
    public java.util.List findByUser(String queryString, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUser(org.openuss.security.User)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByUser(int transform, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUser(boolean, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUser(int, org.openuss.security.User user)}.
     * </p>
     */
    public java.util.List findByUser(int transform, String queryString, org.openuss.security.User user);

}
