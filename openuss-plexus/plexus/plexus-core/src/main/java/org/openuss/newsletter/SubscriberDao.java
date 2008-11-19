package org.openuss.newsletter;

/**
 * @see org.openuss.newsletter.Subscriber
 */
public interface SubscriberDao
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
     * {@link org.openuss.newsletter.SubscriberInfo}.
     */
    public final static int TRANSFORM_SUBSCRIBERINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toSubscriberInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toSubscriberInfo(
        org.openuss.newsletter.Subscriber sourceEntity,
        org.openuss.newsletter.SubscriberInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.newsletter.SubscriberInfo}.
     */
    public org.openuss.newsletter.SubscriberInfo toSubscriberInfo(org.openuss.newsletter.Subscriber entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.newsletter.SubscriberInfo}.
     */
    public void toSubscriberInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.newsletter.SubscriberInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void subscriberInfoToEntity(
        org.openuss.newsletter.SubscriberInfo sourceVO,
        org.openuss.newsletter.Subscriber targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.newsletter.SubscriberInfo} to this DAO's entity.
     */
    public org.openuss.newsletter.Subscriber subscriberInfoToEntity(org.openuss.newsletter.SubscriberInfo subscriberInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.newsletter.SubscriberInfo} to this
     * DAO's entity.
     */
    public void subscriberInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.newsletter.Subscriber from the persistent store.
     */
    public org.openuss.newsletter.Subscriber load(org.openuss.newsletter.SubscriberPK subscriberPk);

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
    public Object load(int transform, org.openuss.newsletter.SubscriberPK subscriberPk);

    /**
     * Loads all entities of type {@link org.openuss.newsletter.Subscriber}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.newsletter.Subscriber> loadAll();

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
     * Creates an instance of org.openuss.newsletter.Subscriber and adds it to the persistent store.
     */
    public org.openuss.newsletter.Subscriber create(org.openuss.newsletter.Subscriber subscriber);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.newsletter.Subscriber)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.newsletter.Subscriber subscriber);

    /**
     * Creates a new instance of org.openuss.newsletter.Subscriber and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.newsletter.Subscriber
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.newsletter.Subscriber> create(java.util.Collection<org.openuss.newsletter.Subscriber> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.newsletter.Subscriber)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.newsletter.Subscriber> entities);

    /**
     * Updates the <code>subscriber</code> instance in the persistent store.
     */
    public void update(org.openuss.newsletter.Subscriber subscriber);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.newsletter.Subscriber> entities);

    /**
     * Removes the instance of org.openuss.newsletter.Subscriber from the persistent store.
     */
    public void remove(org.openuss.newsletter.Subscriber subscriber);

    /**
     * Removes the instance of org.openuss.newsletter.Subscriber having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(org.openuss.newsletter.SubscriberPK subscriberPk);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.newsletter.Subscriber> entities);

    /**
 * 
     */
    public java.util.List findByNewsletter(org.openuss.newsletter.Newsletter newsletter, boolean blocked);

    /**
     * <p>
     * Does the same thing as {@link #findByNewsletter(org.openuss.newsletter.Newsletter, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByNewsletter(org.openuss.newsletter.Newsletter, boolean)}.
     * </p>
     */
    public java.util.List findByNewsletter(String queryString, org.openuss.newsletter.Newsletter newsletter, boolean blocked);

    /**
     * <p>
     * Does the same thing as {@link #findByNewsletter(org.openuss.newsletter.Newsletter, boolean)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByNewsletter(int transform, org.openuss.newsletter.Newsletter newsletter, boolean blocked);

    /**
     * <p>
     * Does the same thing as {@link #findByNewsletter(boolean, org.openuss.newsletter.Newsletter, boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByNewsletter(int, org.openuss.newsletter.Newsletter newsletter, boolean blocked)}.
     * </p>
     */
    public java.util.List findByNewsletter(int transform, String queryString, org.openuss.newsletter.Newsletter newsletter, boolean blocked);

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

    /**
 * 
     */
    public org.openuss.newsletter.Subscriber findByUserAndNewsletter(org.openuss.security.User user, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findByUserAndNewsletter(org.openuss.security.User, org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUserAndNewsletter(org.openuss.security.User, org.openuss.newsletter.Newsletter)}.
     * </p>
     */
    public org.openuss.newsletter.Subscriber findByUserAndNewsletter(String queryString, org.openuss.security.User user, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findByUserAndNewsletter(org.openuss.security.User, org.openuss.newsletter.Newsletter)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByUserAndNewsletter(int transform, org.openuss.security.User user, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findByUserAndNewsletter(boolean, org.openuss.security.User, org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByUserAndNewsletter(int, org.openuss.security.User user, org.openuss.newsletter.Newsletter newsletter)}.
     * </p>
     */
    public Object findByUserAndNewsletter(int transform, String queryString, org.openuss.security.User user, org.openuss.newsletter.Newsletter newsletter);

    /**
 * 
     */
    public java.util.List findByNewsletter(org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findByNewsletter(org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByNewsletter(org.openuss.newsletter.Newsletter)}.
     * </p>
     */
    public java.util.List findByNewsletter(String queryString, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findByNewsletter(org.openuss.newsletter.Newsletter)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByNewsletter(int transform, org.openuss.newsletter.Newsletter newsletter);

    /**
     * <p>
     * Does the same thing as {@link #findByNewsletter(boolean, org.openuss.newsletter.Newsletter)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByNewsletter(int, org.openuss.newsletter.Newsletter newsletter)}.
     * </p>
     */
    public java.util.List findByNewsletter(int transform, String queryString, org.openuss.newsletter.Newsletter newsletter);

}
