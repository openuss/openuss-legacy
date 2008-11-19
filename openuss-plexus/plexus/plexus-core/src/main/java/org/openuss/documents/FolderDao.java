package org.openuss.documents;

/**
 * @see org.openuss.documents.Folder
 */
public interface FolderDao
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
     * {@link org.openuss.documents.FolderInfo}.
     */
    public final static int TRANSFORM_FOLDERINFO = 2;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toFolderInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toFolderInfo(
        org.openuss.documents.Folder sourceEntity,
        org.openuss.documents.FolderInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.documents.FolderInfo}.
     */
    public org.openuss.documents.FolderInfo toFolderInfo(org.openuss.documents.Folder entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.documents.FolderInfo}.
     */
    public void toFolderInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.documents.FolderInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void folderInfoToEntity(
        org.openuss.documents.FolderInfo sourceVO,
        org.openuss.documents.Folder targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.documents.FolderInfo} to this DAO's entity.
     */
    public org.openuss.documents.Folder folderInfoToEntity(org.openuss.documents.FolderInfo folderInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.documents.FolderInfo} to this
     * DAO's entity.
     */
    public void folderInfoToEntityCollection(java.util.Collection instances);

    /**
     * This constant is used as a transformation flag; entities can be converted automatically into value objects
     * or other types, different methods in a class implementing this interface support this feature: look for
     * an <code>int</code> parameter called <code>transform</code>.
     * <p/>
     * This specific flag denotes entities must be transformed into objects of type
     * {@link org.openuss.documents.FolderEntryInfo}.
     */
    public final static int TRANSFORM_FOLDERENTRYINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toFolderEntryInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toFolderEntryInfo(
        org.openuss.documents.Folder sourceEntity,
        org.openuss.documents.FolderEntryInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.documents.FolderEntryInfo}.
     */
    public org.openuss.documents.FolderEntryInfo toFolderEntryInfo(org.openuss.documents.Folder entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.documents.FolderEntryInfo}.
     */
    public void toFolderEntryInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.documents.FolderEntryInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void folderEntryInfoToEntity(
        org.openuss.documents.FolderEntryInfo sourceVO,
        org.openuss.documents.Folder targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.documents.FolderEntryInfo} to this DAO's entity.
     */
    public org.openuss.documents.Folder folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo folderEntryInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.documents.FolderEntryInfo} to this
     * DAO's entity.
     */
    public void folderEntryInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.documents.Folder from the persistent store.
     */
    public org.openuss.documents.Folder load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.documents.Folder}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.documents.Folder> loadAll();

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
     * Creates an instance of org.openuss.documents.Folder and adds it to the persistent store.
     */
    public org.openuss.documents.Folder create(org.openuss.documents.Folder folder);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.documents.Folder)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.documents.Folder folder);

    /**
     * Creates a new instance of org.openuss.documents.Folder and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.documents.Folder
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.documents.Folder> create(java.util.Collection<org.openuss.documents.Folder> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.documents.Folder)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.documents.Folder> entities);

    /**
     * Updates the <code>folder</code> instance in the persistent store.
     */
    public void update(org.openuss.documents.Folder folder);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.documents.Folder> entities);

    /**
     * Removes the instance of org.openuss.documents.Folder from the persistent store.
     */
    public void remove(org.openuss.documents.Folder folder);

    /**
     * Removes the instance of org.openuss.documents.Folder having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.documents.Folder> entities);

    /**
 * 
     */
    public org.openuss.documents.Folder findByDomainIdentifier(java.lang.Long domainIdentifier);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdentifier(java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainIdentifier(java.lang.Long)}.
     * </p>
     */
    public org.openuss.documents.Folder findByDomainIdentifier(String queryString, java.lang.Long domainIdentifier);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdentifier(java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByDomainIdentifier(int transform, java.lang.Long domainIdentifier);

    /**
     * <p>
     * Does the same thing as {@link #findByDomainIdentifier(boolean, java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByDomainIdentifier(int, java.lang.Long domainIdentifier)}.
     * </p>
     */
    public Object findByDomainIdentifier(int transform, String queryString, java.lang.Long domainIdentifier);

}
