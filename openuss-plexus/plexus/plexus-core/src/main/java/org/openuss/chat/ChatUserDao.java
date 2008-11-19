package org.openuss.chat;

/**
 * @see org.openuss.chat.ChatUser
 */
public interface ChatUserDao
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
     * {@link org.openuss.chat.ChatUserInfo}.
     */
    public final static int TRANSFORM_CHATUSERINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toChatUserInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toChatUserInfo(
        org.openuss.chat.ChatUser sourceEntity,
        org.openuss.chat.ChatUserInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.chat.ChatUserInfo}.
     */
    public org.openuss.chat.ChatUserInfo toChatUserInfo(org.openuss.chat.ChatUser entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.chat.ChatUserInfo}.
     */
    public void toChatUserInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.chat.ChatUserInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void chatUserInfoToEntity(
        org.openuss.chat.ChatUserInfo sourceVO,
        org.openuss.chat.ChatUser targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.chat.ChatUserInfo} to this DAO's entity.
     */
    public org.openuss.chat.ChatUser chatUserInfoToEntity(org.openuss.chat.ChatUserInfo chatUserInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.chat.ChatUserInfo} to this
     * DAO's entity.
     */
    public void chatUserInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.chat.ChatUser from the persistent store.
     */
    public org.openuss.chat.ChatUser load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.chat.ChatUser}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.chat.ChatUser> loadAll();

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
     * Creates an instance of org.openuss.chat.ChatUser and adds it to the persistent store.
     */
    public org.openuss.chat.ChatUser create(org.openuss.chat.ChatUser chatUser);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.chat.ChatUser)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.chat.ChatUser chatUser);

    /**
     * Creates a new instance of org.openuss.chat.ChatUser and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.chat.ChatUser
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.chat.ChatUser> create(java.util.Collection<org.openuss.chat.ChatUser> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.chat.ChatUser)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.chat.ChatUser> entities);

    /**
     * Updates the <code>chatUser</code> instance in the persistent store.
     */
    public void update(org.openuss.chat.ChatUser chatUser);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.chat.ChatUser> entities);

    /**
     * Removes the instance of org.openuss.chat.ChatUser from the persistent store.
     */
    public void remove(org.openuss.chat.ChatUser chatUser);

    /**
     * Removes the instance of org.openuss.chat.ChatUser having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.chat.ChatUser> entities);

}
