package org.openuss.newsletter;

/**
 * @see org.openuss.newsletter.Mail
 */
public interface MailDao
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
     * {@link org.openuss.newsletter.MailInfo}.
     */
    public final static int TRANSFORM_MAILINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toMailInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toMailInfo(
        org.openuss.newsletter.Mail sourceEntity,
        org.openuss.newsletter.MailInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.newsletter.MailInfo}.
     */
    public org.openuss.newsletter.MailInfo toMailInfo(org.openuss.newsletter.Mail entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.newsletter.MailInfo}.
     */
    public void toMailInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.newsletter.MailInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void mailInfoToEntity(
        org.openuss.newsletter.MailInfo sourceVO,
        org.openuss.newsletter.Mail targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.newsletter.MailInfo} to this DAO's entity.
     */
    public org.openuss.newsletter.Mail mailInfoToEntity(org.openuss.newsletter.MailInfo mailInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.newsletter.MailInfo} to this
     * DAO's entity.
     */
    public void mailInfoToEntityCollection(java.util.Collection instances);

    /**
     * This constant is used as a transformation flag; entities can be converted automatically into value objects
     * or other types, different methods in a class implementing this interface support this feature: look for
     * an <code>int</code> parameter called <code>transform</code>.
     * <p/>
     * This specific flag denotes entities must be transformed into objects of type
     * {@link org.openuss.newsletter.MailDetail}.
     */
    public final static int TRANSFORM_MAILDETAIL = 2;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toMailDetail(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toMailDetail(
        org.openuss.newsletter.Mail sourceEntity,
        org.openuss.newsletter.MailDetail targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.newsletter.MailDetail}.
     */
    public org.openuss.newsletter.MailDetail toMailDetail(org.openuss.newsletter.Mail entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.newsletter.MailDetail}.
     */
    public void toMailDetailCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.newsletter.MailDetail} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void mailDetailToEntity(
        org.openuss.newsletter.MailDetail sourceVO,
        org.openuss.newsletter.Mail targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.newsletter.MailDetail} to this DAO's entity.
     */
    public org.openuss.newsletter.Mail mailDetailToEntity(org.openuss.newsletter.MailDetail mailDetail);

    /**
     * Converts a Collection of instances of type {@link org.openuss.newsletter.MailDetail} to this
     * DAO's entity.
     */
    public void mailDetailToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.newsletter.Mail from the persistent store.
     */
    public org.openuss.newsletter.Mail load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.newsletter.Mail}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.newsletter.Mail> loadAll();

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
     * Creates an instance of org.openuss.newsletter.Mail and adds it to the persistent store.
     */
    public org.openuss.newsletter.Mail create(org.openuss.newsletter.Mail mail);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.newsletter.Mail)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.newsletter.Mail mail);

    /**
     * Creates a new instance of org.openuss.newsletter.Mail and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.newsletter.Mail
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.newsletter.Mail> create(java.util.Collection<org.openuss.newsletter.Mail> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.newsletter.Mail)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.newsletter.Mail> entities);

    /**
     * Updates the <code>mail</code> instance in the persistent store.
     */
    public void update(org.openuss.newsletter.Mail mail);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.newsletter.Mail> entities);

    /**
     * Removes the instance of org.openuss.newsletter.Mail from the persistent store.
     */
    public void remove(org.openuss.newsletter.Mail mail);

    /**
     * Removes the instance of org.openuss.newsletter.Mail having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.newsletter.Mail> entities);

    /**
 * 
     */
    public java.util.List findMailByState(org.openuss.newsletter.MailingStatus status);

    /**
     * <p>
     * Does the same thing as {@link #findMailByState(org.openuss.newsletter.MailingStatus)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findMailByState(org.openuss.newsletter.MailingStatus)}.
     * </p>
     */
    public java.util.List findMailByState(String queryString, org.openuss.newsletter.MailingStatus status);

    /**
     * <p>
     * Does the same thing as {@link #findMailByState(org.openuss.newsletter.MailingStatus)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findMailByState(int transform, org.openuss.newsletter.MailingStatus status);

    /**
     * <p>
     * Does the same thing as {@link #findMailByState(boolean, org.openuss.newsletter.MailingStatus)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findMailByState(int, org.openuss.newsletter.MailingStatus status)}.
     * </p>
     */
    public java.util.List findMailByState(int transform, String queryString, org.openuss.newsletter.MailingStatus status);

    /**
 * 
     */
    public java.util.List findMailByNewsletter(org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findMailByNewsletter(org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findMailByNewsletter(org.openuss.newsletter.Newsletter)}.
     * </p>
     */
    public java.util.List findMailByNewsletter(String queryString, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findMailByNewsletter(org.openuss.newsletter.Newsletter)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findMailByNewsletter(int transform, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findMailByNewsletter(boolean, org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findMailByNewsletter(int, org.openuss.newsletter.Newsletter newsletter)}.
     * </p>
     */
    public java.util.List findMailByNewsletter(int transform, String queryString, org.openuss.newsletter.Newsletter newsletter);

    /**
 * 
     */
    public java.util.List findMailByNewsletterAndStatus(org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findMailByNewsletterAndStatus(org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findMailByNewsletterAndStatus(org.openuss.newsletter.Newsletter)}.
     * </p>
     */
    public java.util.List findMailByNewsletterAndStatus(String queryString, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findMailByNewsletterAndStatus(org.openuss.newsletter.Newsletter)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findMailByNewsletterAndStatus(int transform, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findMailByNewsletterAndStatus(boolean, org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findMailByNewsletterAndStatus(int, org.openuss.newsletter.Newsletter newsletter)}.
     * </p>
     */
    public java.util.List findMailByNewsletterAndStatus(int transform, String queryString, org.openuss.newsletter.Newsletter newsletter);

    /**
 * 
     */
    public java.util.List findByNewsletterWithoutDeleted(org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findByNewsletterWithoutDeleted(org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByNewsletterWithoutDeleted(org.openuss.newsletter.Newsletter)}.
     * </p>
     */
    public java.util.List findByNewsletterWithoutDeleted(String queryString, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findByNewsletterWithoutDeleted(org.openuss.newsletter.Newsletter)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByNewsletterWithoutDeleted(int transform, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findByNewsletterWithoutDeleted(boolean, org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByNewsletterWithoutDeleted(int, org.openuss.newsletter.Newsletter newsletter)}.
     * </p>
     */
    public java.util.List findByNewsletterWithoutDeleted(int transform, String queryString, org.openuss.newsletter.Newsletter newsletter);

    /**
 * 
     */
    public java.util.List findNotDeletedByStatus(org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findNotDeletedByStatus(org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findNotDeletedByStatus(org.openuss.newsletter.Newsletter)}.
     * </p>
     */
    public java.util.List findNotDeletedByStatus(String queryString, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findNotDeletedByStatus(org.openuss.newsletter.Newsletter)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findNotDeletedByStatus(int transform, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findNotDeletedByStatus(boolean, org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findNotDeletedByStatus(int, org.openuss.newsletter.Newsletter newsletter)}.
     * </p>
     */
    public java.util.List findNotDeletedByStatus(int transform, String queryString, org.openuss.newsletter.Newsletter newsletter);

    /**
 * 
     */
    public org.openuss.newsletter.Mail findMailByMessageId(java.lang.Long messageId);

    /**
     * <p>
     * Does the same thing as {@link #findMailByMessageId(java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findMailByMessageId(java.lang.Long)}.
     * </p>
     */
    public org.openuss.newsletter.Mail findMailByMessageId(String queryString, java.lang.Long messageId);

    /**
     * <p>
     * Does the same thing as {@link #findMailByMessageId(java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findMailByMessageId(int transform, java.lang.Long messageId);

    /**
     * <p>
     * Does the same thing as {@link #findMailByMessageId(boolean, java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findMailByMessageId(int, java.lang.Long messageId)}.
     * </p>
     */
    public Object findMailByMessageId(int transform, String queryString, java.lang.Long messageId);

    /**
 * 
     */
    public java.util.List loadAllMailInfos(org.openuss.newsletter.Newsletter newsletter, org.openuss.security.User user);

    /**
 * 
     */
    public java.util.List loadSendMailInfos(org.openuss.newsletter.Newsletter newsletter, org.openuss.security.User user);

}
