package org.openuss.documents;

/**
 * @see org.openuss.documents.FolderEntry
 */
public interface FolderEntryDao
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
     * {@link org.openuss.documents.FolderEntryInfo}.
     */
    public final static int TRANSFORM_FOLDERENTRYINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toFolderEntryInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toFolderEntryInfo(
        org.openuss.documents.FolderEntry sourceEntity,
        org.openuss.documents.FolderEntryInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.documents.FolderEntryInfo}.
     */
    public org.openuss.documents.FolderEntryInfo toFolderEntryInfo(org.openuss.documents.FolderEntry entity);

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
        org.openuss.documents.FolderEntry targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.documents.FolderEntryInfo} to this DAO's entity.
     */
    public org.openuss.documents.FolderEntry folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo folderEntryInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.documents.FolderEntryInfo} to this
     * DAO's entity.
     */
    public void folderEntryInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.documents.FolderEntry from the persistent store.
     */
    public org.openuss.documents.FolderEntry load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.documents.FolderEntry}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.documents.FolderEntry> loadAll();

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
     * Creates an instance of org.openuss.documents.FolderEntry and adds it to the persistent store.
     */
    public org.openuss.documents.FolderEntry create(org.openuss.documents.FolderEntry folderEntry);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.documents.FolderEntry)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.documents.FolderEntry folderEntry);

    /**
     * Creates a new instance of org.openuss.documents.FolderEntry and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.documents.FolderEntry
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.documents.FolderEntry> create(java.util.Collection<org.openuss.documents.FolderEntry> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.documents.FolderEntry)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.documents.FolderEntry> entities);

    /**
     * Updates the <code>folderEntry</code> instance in the persistent store.
     */
    public void update(org.openuss.documents.FolderEntry folderEntry);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.documents.FolderEntry> entities);

    /**
     * Removes the instance of org.openuss.documents.FolderEntry from the persistent store.
     */
    public void remove(org.openuss.documents.FolderEntry folderEntry);

    /**
     * Removes the instance of org.openuss.documents.FolderEntry having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.documents.FolderEntry> entities);

    /**
 * 
     */
    public java.util.List findByParent(org.openuss.documents.Folder parent);

    /**
     * <p>
     * Does the same thing as {@link #findByParent(org.openuss.documents.Folder)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByParent(org.openuss.documents.Folder)}.
     * </p>
     */
    public java.util.List findByParent(String queryString, org.openuss.documents.Folder parent);

    /**
     * <p>
     * Does the same thing as {@link #findByParent(org.openuss.documents.Folder)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByParent(int transform, org.openuss.documents.Folder parent);

    /**
     * <p>
     * Does the same thing as {@link #findByParent(boolean, org.openuss.documents.Folder)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByParent(int, org.openuss.documents.Folder parent)}.
     * </p>
     */
    public java.util.List findByParent(int transform, String queryString, org.openuss.documents.Folder parent);

}
