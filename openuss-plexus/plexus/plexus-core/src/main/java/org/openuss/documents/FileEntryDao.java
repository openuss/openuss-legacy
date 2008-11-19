package org.openuss.documents;

/**
 * @see org.openuss.documents.FileEntry
 */
public interface FileEntryDao
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
     * {@link org.openuss.documents.FileInfo}.
     */
    public final static int TRANSFORM_FILEINFO = 2;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toFileInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toFileInfo(
        org.openuss.documents.FileEntry sourceEntity,
        org.openuss.documents.FileInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.documents.FileInfo}.
     */
    public org.openuss.documents.FileInfo toFileInfo(org.openuss.documents.FileEntry entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.documents.FileInfo}.
     */
    public void toFileInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.documents.FileInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void fileInfoToEntity(
        org.openuss.documents.FileInfo sourceVO,
        org.openuss.documents.FileEntry targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.documents.FileInfo} to this DAO's entity.
     */
    public org.openuss.documents.FileEntry fileInfoToEntity(org.openuss.documents.FileInfo fileInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.documents.FileInfo} to this
     * DAO's entity.
     */
    public void fileInfoToEntityCollection(java.util.Collection instances);

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
        org.openuss.documents.FileEntry sourceEntity,
        org.openuss.documents.FolderEntryInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.documents.FolderEntryInfo}.
     */
    public org.openuss.documents.FolderEntryInfo toFolderEntryInfo(org.openuss.documents.FileEntry entity);

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
        org.openuss.documents.FileEntry targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.documents.FolderEntryInfo} to this DAO's entity.
     */
    public org.openuss.documents.FileEntry folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo folderEntryInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.documents.FolderEntryInfo} to this
     * DAO's entity.
     */
    public void folderEntryInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.documents.FileEntry from the persistent store.
     */
    public org.openuss.documents.FileEntry load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.documents.FileEntry}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.documents.FileEntry> loadAll();

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
     * Creates an instance of org.openuss.documents.FileEntry and adds it to the persistent store.
     */
    public org.openuss.documents.FileEntry create(org.openuss.documents.FileEntry fileEntry);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.documents.FileEntry)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.documents.FileEntry fileEntry);

    /**
     * Creates a new instance of org.openuss.documents.FileEntry and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.documents.FileEntry
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.documents.FileEntry> create(java.util.Collection<org.openuss.documents.FileEntry> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.documents.FileEntry)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.documents.FileEntry> entities);

    /**
     * Updates the <code>fileEntry</code> instance in the persistent store.
     */
    public void update(org.openuss.documents.FileEntry fileEntry);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.documents.FileEntry> entities);

    /**
     * Removes the instance of org.openuss.documents.FileEntry from the persistent store.
     */
    public void remove(org.openuss.documents.FileEntry fileEntry);

    /**
     * Removes the instance of org.openuss.documents.FileEntry having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.documents.FileEntry> entities);

}
