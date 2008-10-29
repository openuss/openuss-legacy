package org.openuss.lecture;

/**
 * @see org.openuss.lecture.Application
 */
public interface ApplicationDao
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
     * {@link org.openuss.lecture.ApplicationInfo}.
     */
    public final static int TRANSFORM_APPLICATIONINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toApplicationInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toApplicationInfo(
        org.openuss.lecture.Application sourceEntity,
        org.openuss.lecture.ApplicationInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.lecture.ApplicationInfo}.
     */
    public org.openuss.lecture.ApplicationInfo toApplicationInfo(org.openuss.lecture.Application entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.lecture.ApplicationInfo}.
     */
    public void toApplicationInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.lecture.ApplicationInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void applicationInfoToEntity(
        org.openuss.lecture.ApplicationInfo sourceVO,
        org.openuss.lecture.Application targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.lecture.ApplicationInfo} to this DAO's entity.
     */
    public org.openuss.lecture.Application applicationInfoToEntity(org.openuss.lecture.ApplicationInfo applicationInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.lecture.ApplicationInfo} to this
     * DAO's entity.
     */
    public void applicationInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.lecture.Application from the persistent store.
     */
    public org.openuss.lecture.Application load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.lecture.Application}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.lecture.Application> loadAll();

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
     * Creates an instance of org.openuss.lecture.Application and adds it to the persistent store.
     */
    public org.openuss.lecture.Application create(org.openuss.lecture.Application application);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.Application)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.lecture.Application application);

    /**
     * Creates a new instance of org.openuss.lecture.Application and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.lecture.Application
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.lecture.Application> create(java.util.Collection<org.openuss.lecture.Application> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.lecture.Application)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.lecture.Application> entities);

    /**
     * Updates the <code>application</code> instance in the persistent store.
     */
    public void update(org.openuss.lecture.Application application);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.lecture.Application> entities);

    /**
     * Removes the instance of org.openuss.lecture.Application from the persistent store.
     */
    public void remove(org.openuss.lecture.Application application);

    /**
     * Removes the instance of org.openuss.lecture.Application having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.lecture.Application> entities);

    /**
 * 
     */
    public org.openuss.lecture.Application findByInstituteAndDepartment(org.openuss.lecture.Institute institute, org.openuss.lecture.Department department);

    /**
     * <p>
     * Does the same thing as {@link #findByInstituteAndDepartment(org.openuss.lecture.Institute, org.openuss.lecture.Department)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByInstituteAndDepartment(org.openuss.lecture.Institute, org.openuss.lecture.Department)}.
     * </p>
     */
    public org.openuss.lecture.Application findByInstituteAndDepartment(String queryString, org.openuss.lecture.Institute institute, org.openuss.lecture.Department department);

    /**
     * <p>
     * Does the same thing as {@link #findByInstituteAndDepartment(org.openuss.lecture.Institute, org.openuss.lecture.Department)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByInstituteAndDepartment(int transform, org.openuss.lecture.Institute institute, org.openuss.lecture.Department department);

    /**
     * <p>
     * Does the same thing as {@link #findByInstituteAndDepartment(boolean, org.openuss.lecture.Institute, org.openuss.lecture.Department)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByInstituteAndDepartment(int, org.openuss.lecture.Institute institute, org.openuss.lecture.Department department)}.
     * </p>
     */
    public Object findByInstituteAndDepartment(int transform, String queryString, org.openuss.lecture.Institute institute, org.openuss.lecture.Department department);

    /**
 * 
     */
    public java.util.List findByDepartment(org.openuss.lecture.Department department);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartment(org.openuss.lecture.Department)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDepartment(org.openuss.lecture.Department)}.
     * </p>
     */
    public java.util.List findByDepartment(String queryString, org.openuss.lecture.Department department);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartment(org.openuss.lecture.Department)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByDepartment(int transform, org.openuss.lecture.Department department);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartment(boolean, org.openuss.lecture.Department)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDepartment(int, org.openuss.lecture.Department department)}.
     * </p>
     */
    public java.util.List findByDepartment(int transform, String queryString, org.openuss.lecture.Department department);

    /**
 * 
     */
    public java.util.List findByDepartmentAndConfirmed(org.openuss.lecture.Department department, boolean confirmed);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartmentAndConfirmed(org.openuss.lecture.Department, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDepartmentAndConfirmed(org.openuss.lecture.Department, boolean)}.
     * </p>
     */
    public java.util.List findByDepartmentAndConfirmed(String queryString, org.openuss.lecture.Department department, boolean confirmed);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartmentAndConfirmed(org.openuss.lecture.Department, boolean)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByDepartmentAndConfirmed(int transform, org.openuss.lecture.Department department, boolean confirmed);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartmentAndConfirmed(boolean, org.openuss.lecture.Department, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDepartmentAndConfirmed(int, org.openuss.lecture.Department department, boolean confirmed)}.
     * </p>
     */
    public java.util.List findByDepartmentAndConfirmed(int transform, String queryString, org.openuss.lecture.Department department, boolean confirmed);

    /**
 * 
     */
    public java.util.List findByInstitute(org.openuss.lecture.Institute institute);

    /**
     * <p>
     * Does the same thing as {@link #findByInstitute(org.openuss.lecture.Institute)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByInstitute(org.openuss.lecture.Institute)}.
     * </p>
     */
    public java.util.List findByInstitute(String queryString, org.openuss.lecture.Institute institute);

    /**
     * <p>
     * Does the same thing as {@link #findByInstitute(org.openuss.lecture.Institute)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByInstitute(int transform, org.openuss.lecture.Institute institute);

    /**
     * <p>
     * Does the same thing as {@link #findByInstitute(boolean, org.openuss.lecture.Institute)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByInstitute(int, org.openuss.lecture.Institute institute)}.
     * </p>
     */
    public java.util.List findByInstitute(int transform, String queryString, org.openuss.lecture.Institute institute);

    /**
 * 
     */
    public java.util.List findByApplyingUser(org.openuss.security.User applyingUser);

    /**
     * <p>
     * Does the same thing as {@link #findByApplyingUser(org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByApplyingUser(org.openuss.security.User)}.
     * </p>
     */
    public java.util.List findByApplyingUser(String queryString, org.openuss.security.User applyingUser);

    /**
     * <p>
     * Does the same thing as {@link #findByApplyingUser(org.openuss.security.User)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByApplyingUser(int transform, org.openuss.security.User applyingUser);

    /**
     * <p>
     * Does the same thing as {@link #findByApplyingUser(boolean, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByApplyingUser(int, org.openuss.security.User applyingUser)}.
     * </p>
     */
    public java.util.List findByApplyingUser(int transform, String queryString, org.openuss.security.User applyingUser);

    /**
 * 
     */
    public java.util.List findByConfirmingUser(org.openuss.security.User confirmingUser);

    /**
     * <p>
     * Does the same thing as {@link #findByConfirmingUser(org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByConfirmingUser(org.openuss.security.User)}.
     * </p>
     */
    public java.util.List findByConfirmingUser(String queryString, org.openuss.security.User confirmingUser);

    /**
     * <p>
     * Does the same thing as {@link #findByConfirmingUser(org.openuss.security.User)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByConfirmingUser(int transform, org.openuss.security.User confirmingUser);

    /**
     * <p>
     * Does the same thing as {@link #findByConfirmingUser(boolean, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByConfirmingUser(int, org.openuss.security.User confirmingUser)}.
     * </p>
     */
    public java.util.List findByConfirmingUser(int transform, String queryString, org.openuss.security.User confirmingUser);

}
