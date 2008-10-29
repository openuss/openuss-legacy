package org.openuss.paperSubmission;

/**
 * @see org.openuss.paperSubmission.PaperSubmission
 */
public interface PaperSubmissionDao
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
     * {@link org.openuss.paperSubmission.PaperSubmissionInfo}.
     */
    public final static int TRANSFORM_PAPERSUBMISSIONINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toPaperSubmissionInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toPaperSubmissionInfo(
        org.openuss.paperSubmission.PaperSubmission sourceEntity,
        org.openuss.paperSubmission.PaperSubmissionInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.paperSubmission.PaperSubmissionInfo}.
     */
    public org.openuss.paperSubmission.PaperSubmissionInfo toPaperSubmissionInfo(org.openuss.paperSubmission.PaperSubmission entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.paperSubmission.PaperSubmissionInfo}.
     */
    public void toPaperSubmissionInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.paperSubmission.PaperSubmissionInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void paperSubmissionInfoToEntity(
        org.openuss.paperSubmission.PaperSubmissionInfo sourceVO,
        org.openuss.paperSubmission.PaperSubmission targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.paperSubmission.PaperSubmissionInfo} to this DAO's entity.
     */
    public org.openuss.paperSubmission.PaperSubmission paperSubmissionInfoToEntity(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.paperSubmission.PaperSubmissionInfo} to this
     * DAO's entity.
     */
    public void paperSubmissionInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.paperSubmission.PaperSubmission from the persistent store.
     */
    public org.openuss.paperSubmission.PaperSubmission load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.paperSubmission.PaperSubmission}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.paperSubmission.PaperSubmission> loadAll();

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
     * Creates an instance of org.openuss.paperSubmission.PaperSubmission and adds it to the persistent store.
     */
    public org.openuss.paperSubmission.PaperSubmission create(org.openuss.paperSubmission.PaperSubmission paperSubmission);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.paperSubmission.PaperSubmission)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.paperSubmission.PaperSubmission paperSubmission);

    /**
     * Creates a new instance of org.openuss.paperSubmission.PaperSubmission and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.paperSubmission.PaperSubmission
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.paperSubmission.PaperSubmission> create(java.util.Collection<org.openuss.paperSubmission.PaperSubmission> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.paperSubmission.PaperSubmission)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.paperSubmission.PaperSubmission> entities);

    /**
     * Updates the <code>paperSubmission</code> instance in the persistent store.
     */
    public void update(org.openuss.paperSubmission.PaperSubmission paperSubmission);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.paperSubmission.PaperSubmission> entities);

    /**
     * Removes the instance of org.openuss.paperSubmission.PaperSubmission from the persistent store.
     */
    public void remove(org.openuss.paperSubmission.PaperSubmission paperSubmission);

    /**
     * Removes the instance of org.openuss.paperSubmission.PaperSubmission having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.paperSubmission.PaperSubmission> entities);

    /**
 * <p>
 * findes all PaperSubmissions in a Exam
 * </p>
 * <p>
 * @param exam the Exam-Object
 * </p>
 * <p>
 * @return a List of PaperSubmissions
 * </p>
     */
    public java.util.List findByExam(org.openuss.paperSubmission.Exam exam);

    /**
     * <p>
     * Does the same thing as {@link #findByExam(org.openuss.paperSubmission.Exam)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByExam(org.openuss.paperSubmission.Exam)}.
     * </p>
     */
    public java.util.List findByExam(String queryString, org.openuss.paperSubmission.Exam exam);

    /**
     * <p>
     * Does the same thing as {@link #findByExam(org.openuss.paperSubmission.Exam)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByExam(int transform, org.openuss.paperSubmission.Exam exam);

    /**
     * <p>
     * Does the same thing as {@link #findByExam(boolean, org.openuss.paperSubmission.Exam)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByExam(int, org.openuss.paperSubmission.Exam exam)}.
     * </p>
     */
    public java.util.List findByExam(int transform, String queryString, org.openuss.paperSubmission.Exam exam);

    /**
 * <p>
 * finds PaperSubmissions by a Exam and a CourseMember
 * </p>
 * <p>
 * @param exam a Exam
 * </p>
 * <p>
 * @param user a User
 * </p>
 * <p>
 * @return a List of PaperSubmissions that match the combination of
 * Exam and User
 * </p>
     */
    public java.util.List findByExamAndUser(org.openuss.paperSubmission.Exam exam, org.openuss.security.User sender);

    /**
     * <p>
     * Does the same thing as {@link #findByExamAndUser(org.openuss.paperSubmission.Exam, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByExamAndUser(org.openuss.paperSubmission.Exam, org.openuss.security.User)}.
     * </p>
     */
    public java.util.List findByExamAndUser(String queryString, org.openuss.paperSubmission.Exam exam, org.openuss.security.User sender);

    /**
     * <p>
     * Does the same thing as {@link #findByExamAndUser(org.openuss.paperSubmission.Exam, org.openuss.security.User)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByExamAndUser(int transform, org.openuss.paperSubmission.Exam exam, org.openuss.security.User sender);

    /**
     * <p>
     * Does the same thing as {@link #findByExamAndUser(boolean, org.openuss.paperSubmission.Exam, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByExamAndUser(int, org.openuss.paperSubmission.Exam exam, org.openuss.security.User sender)}.
     * </p>
     */
    public java.util.List findByExamAndUser(int transform, String queryString, org.openuss.paperSubmission.Exam exam, org.openuss.security.User sender);

    /**
 * 
     */
    public java.util.List findByUser(org.openuss.security.User sender);

    /**
     * <p>
     * Does the same thing as {@link #findByUser(org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUser(org.openuss.security.User)}.
     * </p>
     */
    public java.util.List findByUser(String queryString, org.openuss.security.User sender);

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
    public java.util.List findByUser(int transform, org.openuss.security.User sender);

    /**
     * <p>
     * Does the same thing as {@link #findByUser(boolean, org.openuss.security.User)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUser(int, org.openuss.security.User sender)}.
     * </p>
     */
    public java.util.List findByUser(int transform, String queryString, org.openuss.security.User sender);

}
