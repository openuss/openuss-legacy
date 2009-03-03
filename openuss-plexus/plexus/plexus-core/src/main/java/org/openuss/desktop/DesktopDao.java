package org.openuss.desktop;

import java.util.Collection;

/**
 * @see org.openuss.desktop.Desktop
 */
public interface DesktopDao
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
     * {@link org.openuss.desktop.DesktopInfo}.
     */
    public final static int TRANSFORM_DESKTOPINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toDesktopInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toDesktopInfo(
        org.openuss.desktop.Desktop sourceEntity,
        org.openuss.desktop.DesktopInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.desktop.DesktopInfo}.
     */
    public org.openuss.desktop.DesktopInfo toDesktopInfo(org.openuss.desktop.Desktop entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.desktop.DesktopInfo}.
     */
    public void toDesktopInfoCollection(Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.desktop.DesktopInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void desktopInfoToEntity(
        org.openuss.desktop.DesktopInfo sourceVO,
        org.openuss.desktop.Desktop targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.desktop.DesktopInfo} to this DAO's entity.
     */
    public org.openuss.desktop.Desktop desktopInfoToEntity(org.openuss.desktop.DesktopInfo desktopInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.desktop.DesktopInfo} to this
     * DAO's entity.
     */
    public void desktopInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.desktop.Desktop from the persistent store.
     */
    public org.openuss.desktop.Desktop load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.desktop.Desktop}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.desktop.Desktop> loadAll();

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
     * Creates an instance of org.openuss.desktop.Desktop and adds it to the persistent store.
     */
    public org.openuss.desktop.Desktop create(org.openuss.desktop.Desktop desktop);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.desktop.Desktop)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.desktop.Desktop desktop);

    /**
     * Creates a new instance of org.openuss.desktop.Desktop and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.desktop.Desktop
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.desktop.Desktop> create(java.util.Collection<org.openuss.desktop.Desktop> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.desktop.Desktop)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.desktop.Desktop> entities);

    /**
     * Updates the <code>desktop</code> instance in the persistent store.
     */
    public void update(org.openuss.desktop.Desktop desktop);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.desktop.Desktop> entities);

    /**
     * Removes the instance of org.openuss.desktop.Desktop from the persistent store.
     */
    public void remove(org.openuss.desktop.Desktop desktop);

    /**
     * Removes the instance of org.openuss.desktop.Desktop having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.desktop.Desktop> entities);

    /**
 * 
     */
    public org.openuss.desktop.Desktop findByUser(org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUser(org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUser(org.openuss.security.User)}.
     * </p>
     */
    public org.openuss.desktop.Desktop findByUser(String queryString, org.openuss.security.User user);

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
    public Object findByUser(int transform, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUser(boolean, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUser(int, org.openuss.security.User user)}.
     * </p>
     */
    public Object findByUser(int transform, String queryString, org.openuss.security.User user);

    /**
 * 
     */
    public java.util.Collection findByUniversity(org.openuss.lecture.University university);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversity(org.openuss.lecture.University)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversity(org.openuss.lecture.University)}.
     * </p>
     */
    public java.util.Collection findByUniversity(String queryString, org.openuss.lecture.University university);

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
    public java.util.Collection findByUniversity(int transform, org.openuss.lecture.University university);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversity(boolean, org.openuss.lecture.University)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversity(int, org.openuss.lecture.University university)}.
     * </p>
     */
    public java.util.Collection findByUniversity(int transform, String queryString, org.openuss.lecture.University university);

    /**
 * 
     */
    public java.util.Collection findByDepartment(org.openuss.lecture.Department department);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartment(org.openuss.lecture.Department)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDepartment(org.openuss.lecture.Department)}.
     * </p>
     */
    public java.util.Collection findByDepartment(String queryString, org.openuss.lecture.Department department);

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
    public java.util.Collection findByDepartment(int transform, org.openuss.lecture.Department department);

    /**
     * <p>
     * Does the same thing as {@link #findByDepartment(boolean, org.openuss.lecture.Department)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDepartment(int, org.openuss.lecture.Department department)}.
     * </p>
     */
    public java.util.Collection findByDepartment(int transform, String queryString, org.openuss.lecture.Department department);

    /**
 * 
     */
    public java.util.Collection findByInstitute(org.openuss.lecture.Institute institute);

    /**
     * <p>
     * Does the same thing as {@link #findByInstitute(org.openuss.lecture.Institute)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByInstitute(org.openuss.lecture.Institute)}.
     * </p>
     */
    public java.util.Collection findByInstitute(String queryString, org.openuss.lecture.Institute institute);

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
    public java.util.Collection findByInstitute(int transform, org.openuss.lecture.Institute institute);

    /**
     * <p>
     * Does the same thing as {@link #findByInstitute(boolean, org.openuss.lecture.Institute)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByInstitute(int, org.openuss.lecture.Institute institute)}.
     * </p>
     */
    public java.util.Collection findByInstitute(int transform, String queryString, org.openuss.lecture.Institute institute);

    /**
 * 
     */
    public java.util.Collection findByCourseType(org.openuss.lecture.CourseType courseType);

    /**
     * <p>
     * Does the same thing as {@link #findByCourseType(org.openuss.lecture.CourseType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByCourseType(org.openuss.lecture.CourseType)}.
     * </p>
     */
    public java.util.Collection findByCourseType(String queryString, org.openuss.lecture.CourseType courseType);

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
    public java.util.Collection findByCourseType(int transform, org.openuss.lecture.CourseType courseType);

    /**
     * <p>
     * Does the same thing as {@link #findByCourseType(boolean, org.openuss.lecture.CourseType)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByCourseType(int, org.openuss.lecture.CourseType courseType)}.
     * </p>
     */
    public java.util.Collection findByCourseType(int transform, String queryString, org.openuss.lecture.CourseType courseType);

    /**
 * 
     */
    public java.util.Collection findByCourse(org.openuss.lecture.Course course);

    /**
     * <p>
     * Does the same thing as {@link #findByCourse(org.openuss.lecture.Course)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByCourse(org.openuss.lecture.Course)}.
     * </p>
     */
    public java.util.Collection findByCourse(String queryString, org.openuss.lecture.Course course);

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
    public java.util.Collection findByCourse(int transform, org.openuss.lecture.Course course);

    /**
     * <p>
     * Does the same thing as {@link #findByCourse(boolean, org.openuss.lecture.Course)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByCourse(int, org.openuss.lecture.Course course)}.
     * </p>
     */
    public java.util.Collection findByCourse(int transform, String queryString, org.openuss.lecture.Course course);

    /**
 * 
     */
    public org.openuss.desktop.Desktop findByUniversityAndUser(org.openuss.lecture.University university, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndUser(org.openuss.lecture.University, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversityAndUser(org.openuss.lecture.University, org.openuss.security.User)}.
     * </p>
     */
    public org.openuss.desktop.Desktop findByUniversityAndUser(String queryString, org.openuss.lecture.University university, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndUser(org.openuss.lecture.University, org.openuss.security.User)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByUniversityAndUser(int transform, org.openuss.lecture.University university, org.openuss.security.User user);

    /**
     * <p>
     * Does the same thing as {@link #findByUniversityAndUser(boolean, org.openuss.lecture.University, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUniversityAndUser(int, org.openuss.lecture.University university, org.openuss.security.User user)}.
     * </p>
     */
    public Object findByUniversityAndUser(int transform, String queryString, org.openuss.lecture.University university, org.openuss.security.User user);

    /**
 * 
     */
    public boolean isInstituteBookmarked(java.lang.Long instituteId, java.lang.Long userId);

    /**
 * 
     */
    public boolean isDepartmentBookmarked(java.lang.Long departmentId, java.lang.Long userId);

    /**
 * 
     */
    public boolean isCourseBookmarked(java.lang.Long courseId, java.lang.Long userId);

}
