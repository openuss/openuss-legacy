package org.openuss.lecture;

/**
 * @see org.openuss.lecture.Department
 */
public interface DepartmentDao
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
     * {@link org.openuss.lecture.DepartmentInfo}.
     */
    public final static int TRANSFORM_DEPARTMENTINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toDepartmentInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toDepartmentInfo(
        org.openuss.lecture.Department sourceEntity,
        org.openuss.lecture.DepartmentInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.lecture.DepartmentInfo}.
     */
    public org.openuss.lecture.DepartmentInfo toDepartmentInfo(org.openuss.lecture.Department entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.lecture.DepartmentInfo}.
     */
    public void toDepartmentInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.lecture.DepartmentInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void departmentInfoToEntity(
        org.openuss.lecture.DepartmentInfo sourceVO,
        org.openuss.lecture.Department targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.lecture.DepartmentInfo} to this DAO's entity.
     */
    public org.openuss.lecture.Department departmentInfoToEntity(org.openuss.lecture.DepartmentInfo departmentInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.lecture.DepartmentInfo} to this
     * DAO's entity.
     */
    public void departmentInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.lecture.Department from the persistent store.
     */
    public org.openuss.lecture.Department load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.lecture.Department}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.lecture.Department> loadAll();

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
     * Creates an instance of org.openuss.lecture.Department and adds it to the persistent store.
     */
    public org.openuss.lecture.Department create(org.openuss.lecture.Department department);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.Department)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.lecture.Department department);

    /**
     * Creates a new instance of org.openuss.lecture.Department and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.lecture.Department
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.lecture.Department> create(java.util.Collection<org.openuss.lecture.Department> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.Department)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.lecture.Department> entities);

    /**
     * Updates the <code>department</code> instance in the persistent store.
     */
    public void update(org.openuss.lecture.Department department);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.lecture.Department> entities);

    /**
     * Removes the instance of org.openuss.lecture.Department from the persistent store.
     */
    public void remove(org.openuss.lecture.Department department);

    /**
     * Removes the instance of org.openuss.lecture.Department having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.lecture.Department> entities);

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

    /**
 * 
     */
    public java.util.List findByUniversityAndEnabled(org.openuss.lecture.University university, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndEnabled(org.openuss.lecture.University, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversityAndEnabled(org.openuss.lecture.University, boolean)}.
     * </p>
     */
    public java.util.List findByUniversityAndEnabled(String queryString, org.openuss.lecture.University university, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndEnabled(org.openuss.lecture.University, boolean)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByUniversityAndEnabled(int transform, org.openuss.lecture.University university, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndEnabled(boolean, org.openuss.lecture.University, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversityAndEnabled(int, org.openuss.lecture.University university, boolean enabled)}.
     * </p>
     */
    public java.util.List findByUniversityAndEnabled(int transform, String queryString, org.openuss.lecture.University university, boolean enabled);

    /**
 * 
     */
    public java.util.List findByType(org.openuss.lecture.DepartmentType type);

    /**
     * <p>
     * Does the same thing as {@link #findByType(org.openuss.lecture.DepartmentType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByType(org.openuss.lecture.DepartmentType)}.
     * </p>
     */
    public java.util.List findByType(String queryString, org.openuss.lecture.DepartmentType type);

    /**
     * <p>
     * Does the same thing as {@link #findByType(org.openuss.lecture.DepartmentType)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByType(int transform, org.openuss.lecture.DepartmentType type);

    /**
     * <p>
     * Does the same thing as {@link #findByType(boolean, org.openuss.lecture.DepartmentType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByType(int, org.openuss.lecture.DepartmentType type)}.
     * </p>
     */
    public java.util.List findByType(int transform, String queryString, org.openuss.lecture.DepartmentType type);

    /**
 * 
     */
    public java.util.List findByUniversityAndType(org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndType(org.openuss.lecture.University, org.openuss.lecture.DepartmentType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversityAndType(org.openuss.lecture.University, org.openuss.lecture.DepartmentType)}.
     * </p>
     */
    public java.util.List findByUniversityAndType(String queryString, org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndType(org.openuss.lecture.University, org.openuss.lecture.DepartmentType)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByUniversityAndType(int transform, org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndType(boolean, org.openuss.lecture.University, org.openuss.lecture.DepartmentType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversityAndType(int, org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType)}.
     * </p>
     */
    public java.util.List findByUniversityAndType(int transform, String queryString, org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType);

    /**
 * 
     */
    public org.openuss.lecture.Department findByShortcut(java.lang.String shortcut);

    /**
     * <p>
     * Does the same thing as {@link #findByShortcut(java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByShortcut(java.lang.String)}.
     * </p>
     */
    public org.openuss.lecture.Department findByShortcut(String queryString, java.lang.String shortcut);

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
    public java.util.List findByUniversityAndTypeAndEnabled(org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndTypeAndEnabled(org.openuss.lecture.University, org.openuss.lecture.DepartmentType, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversityAndTypeAndEnabled(org.openuss.lecture.University, org.openuss.lecture.DepartmentType, boolean)}.
     * </p>
     */
    public java.util.List findByUniversityAndTypeAndEnabled(String queryString, org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndTypeAndEnabled(org.openuss.lecture.University, org.openuss.lecture.DepartmentType, boolean)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByUniversityAndTypeAndEnabled(int transform, org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType, boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndTypeAndEnabled(boolean, org.openuss.lecture.University, org.openuss.lecture.DepartmentType, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversityAndTypeAndEnabled(int, org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType, boolean enabled)}.
     * </p>
     */
    public java.util.List findByUniversityAndTypeAndEnabled(int transform, String queryString, org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType, boolean enabled);

    /**
 * 
     */
    public org.openuss.lecture.Department findByUniversityAndDefault(org.openuss.lecture.University university, boolean defaultDepartment);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndDefault(org.openuss.lecture.University, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversityAndDefault(org.openuss.lecture.University, boolean)}.
     * </p>
     */
    public org.openuss.lecture.Department findByUniversityAndDefault(String queryString, org.openuss.lecture.University university, boolean defaultDepartment);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndDefault(org.openuss.lecture.University, boolean)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByUniversityAndDefault(int transform, org.openuss.lecture.University university, boolean defaultDepartment);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndDefault(boolean, org.openuss.lecture.University, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversityAndDefault(int, org.openuss.lecture.University university, boolean defaultDepartment)}.
     * </p>
     */
    public Object findByUniversityAndDefault(int transform, String queryString, org.openuss.lecture.University university, boolean defaultDepartment);

}
