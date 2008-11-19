
package org.openuss.lecture;

/**
 * @see org.openuss.lecture.Institute
 */
public interface InstituteDao
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
     * {@link org.openuss.lecture.InstituteSecurity}.
     */
    public final static int TRANSFORM_INSTITUTESECURITY = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toInstituteSecurity(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toInstituteSecurity(
        org.openuss.lecture.Institute sourceEntity,
        org.openuss.lecture.InstituteSecurity targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.lecture.InstituteSecurity}.
     */
    public org.openuss.lecture.InstituteSecurity toInstituteSecurity(org.openuss.lecture.Institute entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.lecture.InstituteSecurity}.
     */
    public void toInstituteSecurityCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.lecture.InstituteSecurity} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void instituteSecurityToEntity(
        org.openuss.lecture.InstituteSecurity sourceVO,
        org.openuss.lecture.Institute targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.lecture.InstituteSecurity} to this DAO's entity.
     */
    public org.openuss.lecture.Institute instituteSecurityToEntity(org.openuss.lecture.InstituteSecurity instituteSecurity);

    /**
     * Converts a Collection of instances of type {@link org.openuss.lecture.InstituteSecurity} to this
     * DAO's entity.
     */
    public void instituteSecurityToEntityCollection(java.util.Collection instances);

    /**
     * This constant is used as a transformation flag; entities can be converted automatically into value objects
     * or other types, different methods in a class implementing this interface support this feature: look for
     * an <code>int</code> parameter called <code>transform</code>.
     * <p/>
     * This specific flag denotes entities must be transformed into objects of type
     * {@link org.openuss.lecture.InstituteInfo}.
     */
    public final static int TRANSFORM_INSTITUTEINFO = 2;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toInstituteInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toInstituteInfo(
        org.openuss.lecture.Institute sourceEntity,
        org.openuss.lecture.InstituteInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.lecture.InstituteInfo}.
     */
    public org.openuss.lecture.InstituteInfo toInstituteInfo(org.openuss.lecture.Institute entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.lecture.InstituteInfo}.
     */
    public void toInstituteInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.lecture.InstituteInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void instituteInfoToEntity(
        org.openuss.lecture.InstituteInfo sourceVO,
        org.openuss.lecture.Institute targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.lecture.InstituteInfo} to this DAO's entity.
     */
    public org.openuss.lecture.Institute instituteInfoToEntity(org.openuss.lecture.InstituteInfo instituteInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.lecture.InstituteInfo} to this
     * DAO's entity.
     */
    public void instituteInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.lecture.Institute from the persistent store.
     */
    public org.openuss.lecture.Institute load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.lecture.Institute}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.lecture.Institute> loadAll();

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
     * Creates an instance of org.openuss.lecture.Institute and adds it to the persistent store.
     */
    public org.openuss.lecture.Institute create(org.openuss.lecture.Institute institute);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.Institute)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.lecture.Institute institute);

    /**
     * Creates a new instance of org.openuss.lecture.Institute and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.lecture.Institute
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.lecture.Institute> create(java.util.Collection<org.openuss.lecture.Institute> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.Institute)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.lecture.Institute> entities);

    /**
     * Updates the <code>institute</code> instance in the persistent store.
     */
    public void update(org.openuss.lecture.Institute institute);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.lecture.Institute> entities);

    /**
     * Removes the instance of org.openuss.lecture.Institute from the persistent store.
     */
    public void remove(org.openuss.lecture.Institute institute);

    /**
     * Removes the instance of org.openuss.lecture.Institute having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.lecture.Institute> entities);

    /**
 * 
     */
    public java.util.List findByShortcut(java.lang.String shortcut);

    /**
     * <p>
     * Does the same thing as {@link #findByShortcut(java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByShortcut(java.lang.String)}.
     * </p>
     */
    public java.util.List findByShortcut(String queryString, java.lang.String shortcut);

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
    public java.util.List findByShortcut(int transform, java.lang.String shortcut);

    /**
     * <p>
     * Does the same thing as {@link #findByShortcut(boolean, java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByShortcut(int, java.lang.String shortcut)}.
     * </p>
     */
    public java.util.List findByShortcut(int transform, String queryString, java.lang.String shortcut);

    /**
 * <p>
 * Loads all enabled institutes
 * </p>
     */
    public java.util.List findByEnabled(java.lang.Boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByEnabled(java.lang.Boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByEnabled(java.lang.Boolean)}.
     * </p>
     */
    public java.util.List findByEnabled(String queryString, java.lang.Boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByEnabled(java.lang.Boolean)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByEnabled(int transform, java.lang.Boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByEnabled(boolean, java.lang.Boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByEnabled(int, java.lang.Boolean enabled)}.
     * </p>
     */
    public java.util.List findByEnabled(int transform, String queryString, java.lang.Boolean enabled);

    /**
 * 
     */
    public java.util.List findByDepartmentAndEnabled(org.openuss.lecture.Department department, java.lang.Boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartmentAndEnabled(org.openuss.lecture.Department, java.lang.Boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDepartmentAndEnabled(org.openuss.lecture.Department, java.lang.Boolean)}.
     * </p>
     */
    public java.util.List findByDepartmentAndEnabled(String queryString, org.openuss.lecture.Department department, java.lang.Boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartmentAndEnabled(org.openuss.lecture.Department, java.lang.Boolean)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByDepartmentAndEnabled(int transform, org.openuss.lecture.Department department, java.lang.Boolean enabled);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartmentAndEnabled(boolean, org.openuss.lecture.Department, java.lang.Boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDepartmentAndEnabled(int, org.openuss.lecture.Department department, java.lang.Boolean enabled)}.
     * </p>
     */
    public java.util.List findByDepartmentAndEnabled(int transform, String queryString, org.openuss.lecture.Department department, java.lang.Boolean enabled);

}
