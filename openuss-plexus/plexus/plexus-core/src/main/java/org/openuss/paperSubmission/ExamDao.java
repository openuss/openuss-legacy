package org.openuss.paperSubmission;

/**
 * @see org.openuss.paperSubmission.Exam
 */
public interface ExamDao
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
     * {@link org.openuss.paperSubmission.ExamInfo}.
     */
    public final static int TRANSFORM_EXAMINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toExamInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toExamInfo(
        org.openuss.paperSubmission.Exam sourceEntity,
        org.openuss.paperSubmission.ExamInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.paperSubmission.ExamInfo}.
     */
    public org.openuss.paperSubmission.ExamInfo toExamInfo(org.openuss.paperSubmission.Exam entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.paperSubmission.ExamInfo}.
     */
    public void toExamInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.paperSubmission.ExamInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void examInfoToEntity(
        org.openuss.paperSubmission.ExamInfo sourceVO,
        org.openuss.paperSubmission.Exam targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.paperSubmission.ExamInfo} to this DAO's entity.
     */
    public org.openuss.paperSubmission.Exam examInfoToEntity(org.openuss.paperSubmission.ExamInfo examInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.paperSubmission.ExamInfo} to this
     * DAO's entity.
     */
    public void examInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.paperSubmission.Exam from the persistent store.
     */
    public org.openuss.paperSubmission.Exam load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.paperSubmission.Exam}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.paperSubmission.Exam> loadAll();

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
     * Creates an instance of org.openuss.paperSubmission.Exam and adds it to the persistent store.
     */
    public org.openuss.paperSubmission.Exam create(org.openuss.paperSubmission.Exam exam);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.paperSubmission.Exam)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.paperSubmission.Exam exam);

    /**
     * Creates a new instance of org.openuss.paperSubmission.Exam and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.paperSubmission.Exam
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.paperSubmission.Exam> create(java.util.Collection<org.openuss.paperSubmission.Exam> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.paperSubmission.Exam)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.paperSubmission.Exam> entities);

    /**
     * Updates the <code>exam</code> instance in the persistent store.
     */
    public void update(org.openuss.paperSubmission.Exam exam);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.paperSubmission.Exam> entities);

    /**
     * Removes the instance of org.openuss.paperSubmission.Exam from the persistent store.
     */
    public void remove(org.openuss.paperSubmission.Exam exam);

    /**
     * Removes the instance of org.openuss.paperSubmission.Exam having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.paperSubmission.Exam> entities);

    /**
 * <p>
 * finds all Exams in a Domain-Object
 * </p>
 * <p>
 * @param domainId the domainId the exams are searched for
 * </p>
 * <p>
 * @return a List of Exam-Objects
 * </p>
     */
    public java.util.List findByDomainId(java.lang.Long domainId);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainId(java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainId(java.lang.Long)}.
     * </p>
     */
    public java.util.List findByDomainId(String queryString, java.lang.Long domainId);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainId(java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByDomainId(int transform, java.lang.Long domainId);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainId(boolean, java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainId(int, java.lang.Long domainId)}.
     * </p>
     */
    public java.util.List findByDomainId(int transform, String queryString, java.lang.Long domainId);

}
