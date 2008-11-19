package org.openuss.wiki;

/**
 * @see org.openuss.wiki.WikiSite
 */
public interface WikiSiteDao
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
     * {@link org.openuss.wiki.WikiSiteInfo}.
     */
    public final static int TRANSFORM_WIKISITEINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toWikiSiteInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toWikiSiteInfo(
        org.openuss.wiki.WikiSite sourceEntity,
        org.openuss.wiki.WikiSiteInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.wiki.WikiSiteInfo}.
     */
    public org.openuss.wiki.WikiSiteInfo toWikiSiteInfo(org.openuss.wiki.WikiSite entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.wiki.WikiSiteInfo}.
     */
    public void toWikiSiteInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.wiki.WikiSiteInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void wikiSiteInfoToEntity(
        org.openuss.wiki.WikiSiteInfo sourceVO,
        org.openuss.wiki.WikiSite targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.wiki.WikiSiteInfo} to this DAO's entity.
     */
    public org.openuss.wiki.WikiSite wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo wikiSiteInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.wiki.WikiSiteInfo} to this
     * DAO's entity.
     */
    public void wikiSiteInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.wiki.WikiSite from the persistent store.
     */
    public org.openuss.wiki.WikiSite load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.wiki.WikiSite}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.wiki.WikiSite> loadAll();

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
     * Creates an instance of org.openuss.wiki.WikiSite and adds it to the persistent store.
     */
    public org.openuss.wiki.WikiSite create(org.openuss.wiki.WikiSite wikiSite);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.wiki.WikiSite)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.wiki.WikiSite wikiSite);

    /**
     * Creates a new instance of org.openuss.wiki.WikiSite and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.wiki.WikiSite
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.wiki.WikiSite> create(java.util.Collection<org.openuss.wiki.WikiSite> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.wiki.WikiSite)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.wiki.WikiSite> entities);

    /**
     * Updates the <code>wikiSite</code> instance in the persistent store.
     */
    public void update(org.openuss.wiki.WikiSite wikiSite);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.wiki.WikiSite> entities);

    /**
     * Removes the instance of org.openuss.wiki.WikiSite from the persistent store.
     */
    public void remove(org.openuss.wiki.WikiSite wikiSite);

    /**
     * Removes the instance of org.openuss.wiki.WikiSite having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.wiki.WikiSite> entities);

    /**
 * <p>
 * finds WikiSites for a domainObject
 * </p>
 * <p>
 * @param course the course sites a searched for
 * </p>
 * <p>
 * @return a List of WikiSites for the course
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

    /**
 * 
     */
    public org.openuss.wiki.WikiSite findByDomainIdAndName(java.lang.Long domainId, java.lang.String name);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdAndName(java.lang.Long, java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainIdAndName(java.lang.Long, java.lang.String)}.
     * </p>
     */
    public org.openuss.wiki.WikiSite findByDomainIdAndName(String queryString, java.lang.Long domainId, java.lang.String name);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdAndName(java.lang.Long, java.lang.String)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByDomainIdAndName(int transform, java.lang.Long domainId, java.lang.String name);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdAndName(boolean, java.lang.Long, java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainIdAndName(int, java.lang.Long domainId, java.lang.String name)}.
     * </p>
     */
    public Object findByDomainIdAndName(int transform, String queryString, java.lang.Long domainId, java.lang.String name);

}
